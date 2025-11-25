package shortestpath;

import java.awt.Color;

public class SpConfig {
	// TRANSPORT TOGGLES
	public boolean avoidWilderness = true;
	public boolean useAgilityShortcuts = true;
	public boolean useGrappleShortcuts = false;
	public boolean useBoats = true;
	public boolean useCanoes = false;
	public boolean useCharterShips = false;
	public boolean useShips = true;
	public boolean useFairyRings = true;
	public boolean useGnomeGliders = true;
	public boolean useHotAirBalloons = false;
	public boolean useMagicCarpets = true;
	public boolean useMagicMushtrees = true;
	public boolean useMinecarts = true;
	public boolean useQuetzals = true;
	public boolean useSpiritTrees = true;
	public TeleportationItem useTeleportationItems = TeleportationItem.INVENTORY_NON_CONSUMABLE;
	public boolean useTeleportationBoxes = true;
	public boolean useTeleportationLevers = true;
	public boolean useTeleportationPortals = true;
	public boolean useTeleportationPortalsPoh = false;
	public boolean useTeleportationSpells = true;
	public boolean useTeleportationMinigames = true;
	public boolean useWildernessObelisks = true;
	public boolean useSeasonalTransports = false;

	// COST THRESHOLDS
	public int costAgilityShortcuts = 0;
	public int costGrappleShortcuts = 0;
	public int costBoats = 0;
	public int costCanoes = 0;
	public int costCharterShips = 0;
	public int costShips = 0;
	public int costFairyRings = 0;
	public int costGnomeGliders = 0;
	public int costHotAirBalloons = 0;
	public int costMagicCarpets = 0;
	public int costMagicMushtrees = 0;
	public int costMinecarts = 0;
	public int costQuetzals = 0;
	public int costSpiritTrees = 0;
	public int costNonConsumableTeleportationItems = 0;
	public int costConsumableTeleportationItems = 0;
	public int costTeleportationBoxes = 0;
	public int costTeleportationLevers = 0;
	public int costTeleportationPortals = 0;
	public int costTeleportationSpells = 0;
	public int costTeleportationMinigames = 0;
	public int costWildernessObelisks = 0;
	public int costSeasonalTransports = 0;

	// OTHER SETTINGS
	public boolean includeBankPath = false;
	public int currencyThreshold = 100000;
	public int calculationCutoff = 5;
	public boolean cancelInstead = false;
	public int recalculateDistance = 10;
	public int reachedDistance = 5;
	public boolean showTransportInfo = true;

	// DISPLAY
	public boolean drawTiles = true;
	public boolean drawCollisionMap = false;
	public boolean drawTransports = false;

	// COLORS
	public Color colourPath = new Color(255, 0, 0);
	public Color colourPathCalculating = new Color(0, 0, 255);
	public Color colourTransports = new Color(0, 255, 0, 128);
	public Color colourCollisionMap = new Color(0, 128, 255, 128);
	public Color colourText = Color.WHITE;

	// DEBUG
	public String builtTeleportationBoxes = "";
	public String builtTeleportationPortalsPoh = "";
}
