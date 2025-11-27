package shortestpath.pathfinder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import lombok.Getter;
import shortestpath.PrimitiveIntList;
import lombok.Setter;
import net.runelite.api.coords.WorldPoint;
import shortestpath.WorldPointUtil;

public class Pathfinder implements Callable<Pathfinder.PathfinderResult> {
    private PathfinderStats stats;
    private volatile boolean done = false;
    private volatile boolean cancelled = false;

    @Getter
	@Setter
    private int start;

	@Setter
    @Getter
    private int target;

    public PathfinderConfig config;
    public CollisionMap map;
	// needs to be modified and derived from target for reset
    private boolean targetInWilderness;

    // Capacities should be enough to store all nodes without requiring the queue to grow
    // They were found by checking the max queue size
    private final Deque<Node> boundary = new ArrayDeque<>(4096);
    private final Queue<Node> pending = new PriorityQueue<>(256);
    private VisitedTiles visited;

    private PrimitiveIntList path = new PrimitiveIntList();
    private boolean pathNeedsUpdate = false;
    private Node bestLastNode;
    /**
     * Teleportation transports are updated when this changes.
     * Can be either:
     *  0 = all teleports can be used (e.g. Chronicle)
     * 20 = most teleports can be used (e.g. Varrock Teleport)
     * 30 = some teleports can be used (e.g. Amulet of Glory)
     * 31 = no teleports can be used
     */
    private int wildernessLevel;

	private final int DEFAULT_WILDERNESS_LEVEL = 31;

    public Pathfinder(PathfinderConfig config, int start, int target) {
		this.init(config, start, target);
    }

	public Pathfinder(PathfinderConfig config, WorldPoint start, WorldPoint target) {
		this(config, WorldPointUtil.packWorldPoint(start), WorldPointUtil.packWorldPoint(target));
	}

	public void init(PathfinderConfig config, int start, int end) {
        stats = new PathfinderStats();
        this.config = config;
        this.map = config.getMap();
        this.start = start;
        this.target = target;
        visited = new VisitedTiles(map);
        targetInWilderness = WildernessChecker.isInWilderness(target);
        wildernessLevel = DEFAULT_WILDERNESS_LEVEL;
	}

    public void reset()
    {
		// pathfinder state
		done = false;
		cancelled = false;

		// input calls
        start = WorldPointUtil.UNDEFINED;
        target = WorldPointUtil.UNDEFINED;

		// pathfinding algorithm state
		boundary.clear();
		pending.clear();
		visited = null;	// init creates

		// output state
		path = new PrimitiveIntList();
		pathNeedsUpdate = false;
		bestLastNode = null;	// run creates

		// wilderness tracking
		wildernessLevel = DEFAULT_WILDERNESS_LEVEL;
		// xxx what should the default value be?
		targetInWilderness = WildernessChecker.isInWilderness(target);

		stats = null;	// init creates
    }

	public void restart(PathfinderConfig config, WorldPoint start, WorldPoint target) {
		restart(config, WorldPointUtil.packWorldPoint(start), WorldPointUtil.packWorldPoint(target));
	}

	public void restart(WorldPoint start, WorldPoint target) {
		restart(config, start, target);
	}

	public void restart(PathfinderConfig config, int start, int target) {
		reset();
		init(config, start, target);
	}

	public void restart(int start, int target) {
		restart(config, start, target);
	}

    public boolean isDone() {
        return done;
    }

    public void cancel() {
        cancelled = true;
    }

    public PrimitiveIntList getPath() {
        Node lastNode = bestLastNode; // For thread safety, read bestLastNode once
        if (lastNode == null) {
            return path;
        }

        if (pathNeedsUpdate) {
            path = lastNode.getPath();
            pathNeedsUpdate = false;
        }

        return path;
    }

    private void addNeighbors(Node node) {
        List<Node> nodes = map.getNeighbors(node, visited, config, wildernessLevel);
        for (int i = 0; i < nodes.size(); ++i) {
            Node neighbor = nodes.get(i);

            if (config.avoidWilderness(node.packedPosition, neighbor.packedPosition, targetInWilderness)) {
                continue;
            }

            visited.set(neighbor.packedPosition);
            if (neighbor instanceof TransportNode) {
                pending.add(neighbor);
                ++stats.transportsChecked;
            } else {
                boundary.addLast(neighbor);
                ++stats.nodesChecked;
            }
        }
    }

    @Override
    public PathfinderResult call() {
        stats.start();
        boundary.addFirst(new Node(start, null));

        int bestDistance = Integer.MAX_VALUE;
        long bestHeuristic = Integer.MAX_VALUE;
        long cutoffDurationMillis = config.getCalculationCutoffMillis();
        long cutoffTimeMillis = System.currentTimeMillis() + cutoffDurationMillis;

        while (!cancelled && (!boundary.isEmpty() || !pending.isEmpty())) {
            Node node = boundary.peekFirst();
            Node p = pending.peek();

            if (p != null && (node == null || p.cost < node.cost)) {
                node = pending.poll();
            } else {
                node = boundary.removeFirst();
            }

            if (wildernessLevel > 0) {
                // We don't need to remove teleports when going from 20 to 21 or higher,
                // because the teleport is either used at the very start of the
                // path or when going from 31 or higher to 30, or from 21 or higher to 20.

                boolean update = false;

                // These are overlapping boundaries, so if the node isn't in level 30, it's in 0-29
                // likewise, if the node isn't in level 20, it's in 0-19
                if (wildernessLevel > 30 && !WildernessChecker.isInLevel30Wilderness(node.packedPosition)) {
                    wildernessLevel = 30;
                    update = true;
                }
                if (wildernessLevel > 20 && !WildernessChecker.isInLevel20Wilderness(node.packedPosition)) {
                    wildernessLevel = 20;
                    update = true;
                }
                if (wildernessLevel > 0 && !WildernessChecker.isInWilderness(node.packedPosition)) {
                    wildernessLevel = 0;
                    update = true;
                }
                if (update) {
                    config.refreshTeleports(node.packedPosition, wildernessLevel);
                }
            }

            if (target == node.packedPosition) {
                bestLastNode = node;
                pathNeedsUpdate = true;
                break;
            }

            int distance = WorldPointUtil.distanceBetween(node.packedPosition, target);
            long heuristic = distance + (long) WorldPointUtil.distanceBetween(node.packedPosition, target, 2);
            if (heuristic < bestHeuristic || (heuristic <= bestHeuristic && distance < bestDistance)) {
                bestLastNode = node;
                pathNeedsUpdate = true;
                bestDistance = distance;
                bestHeuristic = heuristic;
                cutoffTimeMillis = System.currentTimeMillis() + cutoffDurationMillis;
            }

            if (System.currentTimeMillis() > cutoffTimeMillis) {
                break;
            }

            addNeighbors(node);
        }

        done = !cancelled;

        boundary.clear();
        visited.clear();
        pending.clear();

        stats.end(); // Include cleanup in stats to get the total cost of pathfinding

		return new PathfinderResult(getPath(), stats);
    }

    public static class PathfinderStats {
        @Getter
        private int nodesChecked = 0, transportsChecked = 0;
        private long startNanos, endNanos;
        private volatile boolean started = false, ended = false;

        public int getTotalNodesChecked() {
            return nodesChecked + transportsChecked;
        }

        public long getElapsedTimeNanos() {
            return endNanos - startNanos;
        }

        private void start() {
            started = true;
            nodesChecked = 0;
            transportsChecked = 0;
            startNanos = System.nanoTime();
        }

        private void end() {
            endNanos = System.nanoTime();
            ended = true;
        }

		@Override
		public String toString() {
			return String.format("[ nodesChecked: %d, transportsChecked: %d, " +
				"startNanos: %d, endNanos: %d, started: %b, ended: %b, " +
				"totalNodesChecked: %d, elapsedTimeNanos: %d ]", nodesChecked,
				transportsChecked, startNanos, endNanos, started, ended,
				getTotalNodesChecked(), getElapsedTimeNanos());
		}
    }

	public class PathfinderResult {
		public PrimitiveIntList path;
		public PathfinderStats stats;
		PathfinderResult(PrimitiveIntList path, PathfinderStats stats) {
			this.path = path;
			this.stats = stats;
		}
	}
}
