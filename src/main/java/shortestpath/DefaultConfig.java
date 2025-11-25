package shortestpath;

import java.awt.Color;

/**
 * static Config class, to be easily modified and viewed all inline. If one
 * plugin changes this class, then all will keep changes (so update state to
 * expected state if last plugin's configuration is not ideal).
 */
public class DefaultConfig {
	// TRANSPORT TOGGLES
	public static boolean avoidWilderness = true;
	public static boolean useAgilityShortcuts = true;
	public static boolean useGrappleShortcuts = false;
	public static boolean useBoats = true;
	public static boolean useCanoes = false;
	public static boolean useCharterShips = false;
	public static boolean useShips = true;
	public static boolean useFairyRings = true;
	public static boolean useGnomeGliders = true;
	public static boolean useHotAirBalloons = false;
	public static boolean useMagicCarpets = true;
	public static boolean useMagicMushtrees = true;
	public static boolean useMinecarts = true;
	public static boolean useQuetzals = true;
	public static boolean useSpiritTrees = true;
	public static TeleportationItem useTeleportationItems = TeleportationItem.INVENTORY_NON_CONSUMABLE;
	public static boolean useTeleportationBoxes = true;
	public static boolean useTeleportationLevers = true;
	public static boolean useTeleportationPortals = true;
	public static boolean useTeleportationPortalsPoh = false;
	public static boolean useTeleportationSpells = true;
	public static boolean useTeleportationMinigames = true;
	public static boolean useWildernessObelisks = true;
	public static boolean useSeasonalTransports = false;

	// COST THRESHOLDS
	public static int costAgilityShortcuts = 0;
	public static int costGrappleShortcuts = 0;
	public static int costBoats = 0;
	public static int costCanoes = 0;
	public static int costCharterShips = 0;
	public static int costShips = 0;
	public static int costFairyRings = 0;
	public static int costGnomeGliders = 0;
	public static int costHotAirBalloons = 0;
	public static int costMagicCarpets = 0;
	public static int costMagicMushtrees = 0;
	public static int costMinecarts = 0;
	public static int costQuetzals = 0;
	public static int costSpiritTrees = 0;
	public static int costNonConsumableTeleportationItems = 0;
	public static int costConsumableTeleportationItems = 0;
	public static int costTeleportationBoxes = 0;
	public static int costTeleportationLevers = 0;
	public static int costTeleportationPortals = 0;
	public static int costTeleportationSpells = 0;
	public static int costTeleportationMinigames = 0;
	public static int costWildernessObelisks = 0;
	public static int costSeasonalTransports = 0;

	// OTHER SETTINGS
	public static boolean includeBankPath = false;
	public static int currencyThreshold = 100000;
	public static int calculationCutoff = 5;
	public static boolean cancelInstead = false;
	public static int recalculateDistance = 10;
	public static int reachedDistance = 5;
	public static boolean showTransportInfo = true;

	// DISPLAY
	public static boolean drawTiles = true;
	public static boolean drawCollisionMap = false;
	public static boolean drawTransports = false;

	// COLORS
	public static Color colourPath = new Color(255, 0, 0);
	public static Color colourPathCalculating = new Color(0, 0, 255);
	public static Color colourTransports = new Color(0, 255, 0, 128);
	public static Color colourCollisionMap = new Color(0, 128, 255, 128);
	public static Color colourText = Color.WHITE;

	// DEBUG
	public static String builtTeleportationBoxes = "";
	public static String builtTeleportationPortalsPoh = "";
}
