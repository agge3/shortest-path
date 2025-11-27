package shortestpath;

import shortestpath.pathfinder.*;
import shortestpath.transport.*;

import com.aggeplugins.MessageBus.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Inject;
import com.google.inject.Provides;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.coords.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.*;
import net.runelite.api.widgets.*;
import net.runelite.api.worldmap.*;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.*;
import net.runelite.client.events.*;
import net.runelite.client.game.*;
import net.runelite.client.plugins.*;
import net.runelite.client.ui.*;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.worldmap.*;
import net.runelite.client.util.*;

import lombok.extern.slf4j.Slf4j;

@PluginDescriptor(
    name = "<html><font color=\"#73D216\">[A3]</font> Shortest Path</html>",
    description = "Draws the shortest path to a chosen destination on the map<br>" +
        "Right click on the world map or shift right click a tile to use",
    tags = {"pathfinder", "map", "waypoint", "navigation"}
)
@Slf4j
public class ShortestPathPlugin extends Plugin {
    protected static final String CONFIG_GROUP = "shortestpath";

    private static final Pattern TRANSPORT_OPTIONS_REGEX = Pattern.compile("^(avoidWilderness|includeBankPath|currencyThreshold|use\\w+|cost\\w+)$");
    private static final File PLUGIN_DIRECTORY = new File(RuneLite.RUNELITE_DIR, "shortest-path");

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private ShortestPathConfig config;

    @Inject
    private EventBus eventBus;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private PathTileOverlay pathOverlay;

    boolean drawCollisionMap = false;

	// keep as switch, in to deny rendering overhead at scale
    boolean drawTiles = true;

    private ExecutorService pathfindingExecutor = Executors.newSingleThreadExecutor();
    private Future<?> pathfinderFuture;
    private final Object pathfinderMutex = new Object();

    @Getter
    public static Pathfinder pathfinder;
    @Getter
    private PathfinderConfig pathfinderConfig;

    private MessageBus messageBus;
    private Message<MessageID, ?> msg;
    private WorldPoint target;

    public boolean started;

    @Provides
    public ShortestPathConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ShortestPathConfig.class);
    }

    @Override
    protected void startUp() {
        PLUGIN_DIRECTORY.mkdirs();

        pathfinderConfig = new PathfinderConfig(client, config);
        if (GameState.LOGGED_IN.equals(client.getGameState())) {
            clientThread.invokeLater(pathfinderConfig::refresh);
        }

        overlayManager.add(pathOverlay);

        this.init();
    }

    private void init()
    {
        messageBus = messageBus.instance();
        started = false; // by default, not (started) pathing
    }

    @Override
    protected void shutDown() {
        overlayManager.remove(pathOverlay);

        this.finalize();

        // Release MessageBus instance
        messageBus = null;
    }

    public void restartPathfinding(int start, Set<Integer> ends, boolean canReviveFiltered) {
        synchronized (pathfinderMutex) {
            if (pathfinder != null) {
                pathfinder.cancel();
                pathfinderFuture.cancel(true);
            }

            if (pathfindingExecutor == null) {
                ThreadFactory shortestPathNaming = new ThreadFactoryBuilder().setNameFormat("shortest-path-%d").build();
                pathfindingExecutor = Executors.newSingleThreadExecutor(shortestPathNaming);
            }
        }

        clientThread.invokeLater(() -> {
            pathfinderConfig.refresh();
            pathfinderConfig.filterLocations(ends, canReviveFiltered);
            synchronized (pathfinderMutex) {
                if (ends.isEmpty()) {
                } else {
                    pathfinder = new Pathfinder(this, pathfinderConfig, start, ends);
                    pathfinderFuture = pathfindingExecutor.submit(pathfinder);
                }
            }
        });
    }

    public void restartPathfinding(int start, Set<Integer> ends) {
        restartPathfinding(start, ends, true);
    }

	/**
	 * Wrapper method to restart pathfinding with WorldPoints (instead of
	 * Pathfinder API).
	 */
	public void restartPathfinding(WorldPoint start, WorldPoint end) {
		Set<Integer> s = new HashSet<>(1);
		s.add(WorldPointUtil.packWorldPoint(end));
		restartPathfinding(WorldPointUtil.packWorldPoint(start), s);
	}

    @Subscribe
    public void onGameTick(GameTick tick) {
      if (!messageBus.query(MessageID.REQUEST_PATH)) {
            finalize(); // always be null if there's no instructions to path
            return; // break-out
        }

        Player localPlayer = client.getLocalPlayer();
        if (localPlayer == null) {
			log.error("localPlayer is null, cannot pathfind!");
            finalize(); // clean-up if there's no player location
            return; // break-out
        }

        WorldPoint pos = client.isInInstancedRegion() ?
            WorldPoint.fromLocalInstance(client,
                                         localPlayer.getLocalLocation()) :
            localPlayer.getWorldLocation();

        // Get the Message if we don't have one.
        if (msg == null) {
            msg = (Message<MessageID, WorldPoint>)
                messageBus.get(MessageID.REQUEST_PATH);
            log.info("Received Message to path!");
            target = (WorldPoint) msg.getData();

            restartPathfinding(pos, target);
            started = true;	// xxx what's started doing?
        }

        if (pathfinder != null) {
            log.info("Target: " + target);
            log.info("Pathfinder start: " + pathfinder.getStart());
			Set<Integer> s = pathfinder.getTargets();
			log.info("pathfinder targets size: {}", s.size());
			if (s.size() > 1) {
				log.warn("pathfinder targets size expected to be <=1, but >1: {}", s.size());
			}
			int idx = 0;
			for (Integer e : s) {
				log.info("pathfinder targets[{}]: {}", idx, e);
				idx++;
			}
        }

        if (pathfinder != null && pathfinder.isDone()) {
			List<WorldPoint> path = pathfinder.getPathWp();
            log.info("Calculated path size " + path.size());
            // send path
            messageBus.send(new Message<MessageID, List<WorldPoint>>(
                MessageID.SEND_PATH, path));
            finalize(); // cleanup
            return; // and break-out
        } else if (pathfinder == null) {
            log.info("Pathfinder was null! Re-initializing...");
            restartPathfinding(pos, target);
            started = true;
            return;
        } else {
            // If all conditions aren't met then Pathfinder is calculating.
            log.info("Waiting for Pathfinder to calculate...");
        }
    }

    /**
     * Finalize procedure to always ensure a clean state for Pathfinder.
     */
    @Override
    public void finalize()
    {
        synchronized (pathfinderMutex) {
            if (pathfinder != null) {
                pathfinder.cancel();
                pathfinder = null;
            }
        }

        if (pathfinderFuture != null) {
            pathfinderFuture.cancel(true);
            pathfinderFuture = null;
        }

        if (pathfindingExecutor != null) {
            pathfindingExecutor.shutdownNow();
            pathfindingExecutor = null;
        }

        target = null;
        msg = null;

        started = false;
    }

    public Map<Integer, Set<Transport>> getTransports() {
        return pathfinderConfig.getTransports();
    }

    public CollisionMap getMap() {
        return pathfinderConfig.getMap();
    }

	// xxx what's going on here?
    private String simplify(String text) {
        return Text.removeTags(text).toLowerCase()
            .replaceAll("[^a-zA-Z ]", "")
            .replaceAll("[ ]", "_")
            .replace("__", "_");
    }
}
