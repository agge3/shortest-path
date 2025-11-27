package shortestpath;

import com.google.inject.Inject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.Tile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import shortestpath.pathfinder.CollisionMap;
import shortestpath.transport.Transport;
import shortestpath.transport.TransportType;

public class PathTileOverlay extends Overlay {
    private final Client client;
    private final ShortestPathPlugin plugin;
    private static final int TRANSPORT_LABEL_GAP = 3;

	@Inject
	ShortestPathConfig config;

    @Inject
    public PathTileOverlay(Client client, ShortestPathPlugin plugin) {
        this.client = client;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(Overlay.PRIORITY_LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    private void renderCollisionMap(Graphics2D graphics) {
        CollisionMap map = plugin.getMap();
        for (Tile[] row : client.getScene().getTiles()[client.getPlane()]) {
            for (Tile tile : row) {
                if (tile == null) {
                    continue;
                }

                Polygon tilePolygon = Perspective.getCanvasTilePoly(client, tile.getLocalLocation());

                if (tilePolygon == null) {
                    continue;
                }

                int location = WorldPointUtil.fromLocalInstance(client, tile.getLocalLocation());
                int x = WorldPointUtil.unpackWorldX(location);
                int y = WorldPointUtil.unpackWorldY(location);
                int z = WorldPointUtil.unpackWorldPlane(location);

                String s = (!map.n(x, y, z) ? "n" : "") +
                        (!map.s(x, y, z) ? "s" : "") +
                        (!map.e(x, y, z) ? "e" : "") +
                        (!map.w(x, y, z) ? "w" : "");

                if (map.isBlocked(x, y, z)) {
                    graphics.setColor(config.colourCollisionMap());
                    graphics.fill(tilePolygon);
                }
                if (!s.isEmpty() && !s.equals("nsew")) {
                    graphics.setColor(Color.WHITE);
                    int stringX = (int) (tilePolygon.getBounds().getCenterX() - graphics.getFontMetrics().getStringBounds(s, graphics).getWidth() / 2);
                    int stringY = (int) tilePolygon.getBounds().getCenterY();
                    graphics.drawString(s, stringX, stringY);
                }
            }
        }
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (config.drawCollisionMap()) {
            renderCollisionMap(graphics);
        }

        if (plugin.drawTiles && plugin.getPathfinder() != null && plugin.getPathfinder().getPath() != null) {
            Color colorCalculating = new Color(
                config.colourPathCalculating().getRed(),
                config.colourPathCalculating().getGreen(),
                config.colourPathCalculating().getBlue(),
                config.colourPathCalculating().getAlpha() / 2);
            Color color = plugin.getPathfinder().isDone()
                ? new Color(
                    config.colourPath().getRed(),
                    config.colourPath().getGreen(),
                    config.colourPath().getBlue(),
                    config.colourPath().getAlpha() / 2)
                : colorCalculating;

            PrimitiveIntList path = plugin.getPathfinder().getPath();
            int counter = 0;
            boolean showTiles = true;
            for (int i = 0; i < path.size(); i++) {
                drawTile(graphics, path.get(i), color, counter++, showTiles);
            }
            for (int target : plugin.getPathfinder().getTargets()) {
                if (path.size() > 0 && target != path.get(path.size() - 1)) {
                    drawTile(graphics, target, colorCalculating, -1, showTiles);
                }
            }
        }

        return null;
    }

    private Point tileCenter(int b) {
        if (b == WorldPointUtil.UNDEFINED || client == null) {
            return null;
        }

        if (WorldPointUtil.unpackWorldPlane(b) != client.getPlane()) {
            return null;
        }

        LocalPoint lp = WorldPointUtil.toLocalPoint(client, b);
        if (lp == null) {
            return null;
        }

        Polygon poly = Perspective.getCanvasTilePoly(client, lp);
        if (poly == null) {
            return null;
        }

        int cx = poly.getBounds().x + poly.getBounds().width / 2;
        int cy = poly.getBounds().y + poly.getBounds().height / 2;
        return new Point(cx, cy);
    }

    private void drawTile(Graphics2D graphics, int location, Color color, int counter, boolean draw) {
        if (client == null) {
            return;
        }

        PrimitiveIntList points = WorldPointUtil.toLocalInstance(client, location);
        for (int i = 0; i < points.size(); i++) {
            int point = points.get(i);
            if (point == WorldPointUtil.UNDEFINED) {
                continue;
            }

            LocalPoint lp = WorldPointUtil.toLocalPoint(client, point);
            if (lp == null) {
                continue;
            }

            Polygon poly = Perspective.getCanvasTilePoly(client, lp);
            if (poly == null) {
                continue;
            }

            if (draw) {
                graphics.setColor(color);
                graphics.fill(poly);
            }
        }
    }
}
