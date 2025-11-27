package shortestpath;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.awt.Color;

/**
 * static Config class, to be easily modified and viewed all inline. If one
 * plugin changes this class, then all will keep changes (so update state to
 * expected state if last plugin's configuration is not ideal).
 *
 * lombok: getters have the same function signature has the variable name (to
 * match ShortestPathConfig)
 * 	ex.
 * 		boolean avoidWilderness; -> boolean avoidWilderness();
 *
 * @todo read in from groovy OR json
 * @note groovy can modify the object at runtime!
 */
@Getter
@Acessors(fluent = true)
public class PathingConfig {
	public static final DEFAULT_CALCULATION_CUTOFF = 5;

	// TRANSPORT TOGGLES
	public boolean avoidWilderness = true;
	public boolean useAgilityShortcuts = true;
	public boolean useGrappleShortcuts = false;
	public boolean useBoats = false;
	public boolean useCanoes = false;
	public boolean useCharterShips = false;
	public boolean useShips = false;
	public boolean useFairyRings = false;
	public boolean useGnomeGliders = false;
	public boolean useHotAirBalloons = false;
	public boolean useMagicCarpets = false;
	public boolean useMagicMushtrees = false;
	public boolean useMinecarts = false;;
	public boolean useQuetzals = false;
	public boolean useSpiritTrees = false;
	//public TeleportationItem useTeleportationItems = TeleportationItem.INVENTORY_NON_CONSUMABLE;
	// xxx would need to click!
	public TeleportationItem useTeleportationItems = TeleportationItem.NONE;
	public boolean useTeleportationBoxes = false;
	public boolean useTeleportationLevers = false;
	public boolean useTeleportationPortals = false;
	public boolean useTeleportationPortalsPoh = false;
	public boolean useTeleportationSpells = false;
	public boolean useTeleportationMinigames = false;
	public boolean useWildernessObelisks = false;
	public boolean useSeasonalTransports = false;

	// COST THRESHOLDS
	// XXX SEE ABOVE FOR WHAT'S ENABLED
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
	public int calculationCutoff = DEFAULT_CALCULATION_CUTOFF;
	public boolean cancelInstead = false;	// xxx ?
	public int recalculateDistance = 10;
	public int reachedDistance = 5;
	public boolean showTransportInfo = true;	// xxx disabled?

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
