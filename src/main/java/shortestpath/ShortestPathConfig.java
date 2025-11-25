package shortestpath;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup(ShortestPathPlugin.CONFIG_GROUP)
public interface ShortestPathConfig extends Config {
    @ConfigSection(
        name = "Settings",
        description = "Options for the pathfinding",
        position = 0
    )
    String sectionSettings = "sectionSettings";

    @ConfigItem(
        keyName = "avoidWilderness",
        name = "Avoid wilderness",
        description = "Whether the wilderness should be avoided if possible<br>" +
            "(otherwise, will e.g. use wilderness lever from Edgeville to Ardougne)",
        position = 1,
        section = sectionSettings
    )
    default boolean avoidWilderness() {
        return true;
    }

    @ConfigItem(
        keyName = "useAgilityShortcuts",
        name = "Use agility shortcuts",
        description = "Whether to include agility shortcuts in the path.<br>" +
            "You must also have the required agility level",
        position = 2,
        section = sectionSettings
    )
    default boolean useAgilityShortcuts() {
        return true;
    }

    @ConfigItem(
        keyName = "useGrappleShortcuts",
        name = "Use grapple shortcuts",
        description = "Whether to include crossbow grapple agility shortcuts in the path.<br>" +
            "You must also have the required agility, ranged and strength levels",
        position = 3,
        section = sectionSettings
    )
    default boolean useGrappleShortcuts() {
        return false;
    }

    @ConfigItem(
        keyName = "useBoats",
        name = "Use boats",
        description = "Whether to include small boats in the path<br>" +
            "(e.g. the boat to Fishing Platform)",
        position = 4,
        section = sectionSettings
    )
    default boolean useBoats() {
        return true;
    }

    @ConfigItem(
        keyName = "useCanoes",
        name = "Use canoes",
        description = "Whether to include canoes in the path",
        position = 5,
        section = sectionSettings
    )
    default boolean useCanoes() {
        return false;
    }

    @ConfigItem(
        keyName = "useCharterShips",
        name = "Use charter ships",
        description = "Whether to include charter ships in the path",
        position = 6,
        section = sectionSettings
    )
    default boolean useCharterShips() {
        return false;
    }

    @ConfigItem(
        keyName = "useShips",
        name = "Use ships",
        description = "Whether to include passenger ships in the path<br>" +
            "(e.g. the customs ships to Karamja)",
        position = 7,
        section = sectionSettings
    )
    default boolean useShips() {
        return true;
    }

    @ConfigItem(
        keyName = "useFairyRings",
        name = "Use fairy rings",
        description = "Whether to include fairy rings in the path.<br>" +
            "You must also have completed the required quests or miniquests",
        position = 8,
        section = sectionSettings
    )
    default boolean useFairyRings() {
        return true;
    }

    @ConfigItem(
        keyName = "useGnomeGliders",
        name = "Use gnome gliders",
        description = "Whether to include gnome gliders in the path",
        position = 9,
        section = sectionSettings
    )
    default boolean useGnomeGliders() {
        return true;
    }

    @ConfigItem(
        keyName = "useHotAirBalloons",
        name = "Use hot air balloons",
        description = "Whether to include hot air balloons in the path",
        position = 10,
        section = sectionSettings
    )
    default boolean useHotAirBalloons() {
        return false;
    }

    @ConfigItem(
        keyName = "useMagicCarpets",
        name = "Use magic carpets",
        description = "Whether to include magic carpets in the path",
        position = 11,
        section = sectionSettings
    )
    default boolean useMagicCarpets() {
        return true;
    }

    @ConfigItem(
        keyName = "useMagicMushtrees",
        name = "Use magic mushtrees",
        description = "Whether to include Fossil Island Magic Mushtrees in the path<br>" +
            "(e.g. the Mycelium transport network from Verdant Valley to Mushroom Meadow)",
        position = 12,
        section = sectionSettings
    )
    default boolean useMagicMushtrees() {
        return true;
    }

    @ConfigItem(
        keyName = "useMinecarts",
        name = "Use minecarts",
        description = "Whether to include minecarts in the path<br>" +
            "(e.g. the Keldagrim and Lovakengj minecart networks)",
        position = 13,
        section = sectionSettings
    )
    default boolean useMinecarts() {
        return true;
    }

    @ConfigItem(
        keyName = "useQuetzals",
        name = "Use quetzals",
        description = "Whether to include quetzals in the path",
        position = 14,
        section = sectionSettings
    )
    default boolean useQuetzals() {
        return true;
    }

    @ConfigItem(
        keyName = "useSpiritTrees",
        name = "Use spirit trees",
        description = "Whether to include spirit trees in the path",
        position = 15,
        section = sectionSettings
    )
    default boolean useSpiritTrees() {
        return true;
    }

    @ConfigItem(
        keyName = "useTeleportationItems",
        name = "Use teleportation items",
        description = "Whether to include teleportation items from the player's inventory and equipment.<br>" +
            "Options labelled (perm) only use permanent non-charge items.<br>" +
            "The All options do not check skill, quest or item requirements.",
        position = 16,
        section = sectionSettings
    )
    default TeleportationItem useTeleportationItems() {
        return TeleportationItem.INVENTORY_NON_CONSUMABLE;
    }

    @ConfigItem(
        keyName = "useTeleportationBoxes",
        name = "Use teleportation boxes",
        description = "Whether to include teleportation boxes or mounted items in the path<br>" +
            "(e.g. the PoH jewellery box or PoH mounted glory amulet)",
        position = 17,
        section = sectionSettings
    )
    default boolean useTeleportationBoxes() {
        return true;
    }

    @ConfigItem(
        keyName = "useTeleportationLevers",
        name = "Use teleportation levers",
        description = "Whether to include teleportation levers in the path<br>" +
            "(e.g. the lever from Edgeville to Wilderness)",
        position = 18,
        section = sectionSettings
    )
    default boolean useTeleportationLevers() {
        return true;
    }

    @ConfigItem(
        keyName = "useTeleportationPortals",
        name = "Use teleportation portals",
        description = "Whether to include teleportation portals in the path<br>" +
            "(e.g. the portal from Ferox Enclave to Castle Wars)",
        position = 19,
        section = sectionSettings
    )
    default boolean useTeleportationPortals() {
        return true;
    }

    @ConfigItem(
        keyName = "useTeleportationPortalsPoh",
        name = "Use teleportation portals (POH)",
        description = "Whether to include player-owned-house (POH) teleportation portals/nexus in the path",
        position = 20,
        section = sectionSettings
    )
    default boolean useTeleportationPortalsPoh() {
        return false;
    }

    @ConfigItem(
        keyName = "useTeleportationSpells",
        name = "Use teleportation spells",
        description = "Whether to include teleportation spells in the path",
        position = 21,
        section = sectionSettings
    )
    default boolean useTeleportationSpells() {
        return true;
    }

    @ConfigItem(
        keyName = "useTeleportationMinigames",
        name = "Use teleportation to minigames",
        description = "Whether to include teleportation to minigames/activities/grouping in the path<br>" +
            "(e.g. the Nightmare Zone minigame teleport). These teleports share a 20 minute cooldown.",
        position = 22,
        section = sectionSettings
    )
    default boolean useTeleportationMinigames() {
        return true;
    }

    @ConfigItem(
        keyName = "useWildernessObelisks",
        name = "Use wilderness obelisks",
        description = "Whether to include wilderness obelisks in the path",
        position = 23,
        section = sectionSettings
    )
    default boolean useWildernessObelisks() {
        return true;
    }


    @ConfigItem(
        keyName = "calculationCutoff",
        name = "Calculation cutoff",
        description = "The cutoff threshold in number of ticks (0.6 seconds) of no progress being<br>" +
            "made towards the path target before the calculation will be stopped",
        position = 32,
        section = sectionSettings
    )
    default int calculationCutoff()
    {
        return 5;
    }

    @ConfigItem(
        keyName = "drawTiles",
        name = "Draw path on tiles",
        description = "Whether the path should be drawn on the game tiles",
        position = 61,
        section = sectionSettings
    )
    default boolean drawTiles() {
        return true;
    }

    @ConfigSection(
        name = "Colours",
        description = "Colours for the path map, minimap and scene tiles",
        position = 63
    )
    String sectionColours = "sectionColours";

    @Alpha
    @ConfigItem(
        keyName = "colourPath",
        name = "Path",
        description = "Colour of the path tiles on the world map, minimap and in the game scene",
        position = 64,
        section = sectionColours
    )
    default Color colourPath() {
        return new Color(255, 0, 0);
    }

    @Alpha
    @ConfigItem(
        keyName = "colourPathCalculating",
        name = "Calculating",
        description = "Colour of the path tiles while the pathfinding calculation is in progress," +
            "<br>and the colour of unused targets if there are more than a single target",
        position = 65,
        section = sectionColours
    )
    default Color colourPathCalculating() {
        return new Color(0, 0, 255);
    }

    @Alpha
    @ConfigItem(
        keyName = "colourTransports",
        name = "Transports",
        description = "Colour of the transport tiles",
        position = 66,
        section = sectionColours
    )
    default Color colourTransports() {
        return new Color(0, 255, 0, 128);
    }

    @Alpha
    @ConfigItem(
        keyName = "colourCollisionMap",
        name = "Collision map",
        description = "Colour of the collision map tiles",
        position = 67,
        section = sectionColours
    )
    default Color colourCollisionMap() {
        return new Color(0, 128, 255, 128);
    }

    @ConfigItem(
        keyName = "drawCollisionMap",
        name = "Draw collision map",
        description = "Whether the collision map should be drawn",
        position = 71,
        section = sectionSettings
    )
    default boolean drawCollisionMap() {
        return false;
    }

    default boolean useSeasonalTransports() {
        return false;
    }

    default boolean includeBankPath() {
        return false;
    }

    default int currencyThreshold() {
        return 100000;
    }

    // description = "Whether the path should be cancelled rather than recalculated " +
    //     "when the recalculate distance limit is exceeded",
    default boolean cancelInstead() {
        return false;
    }

    default int recalculateDistance() {
        return 10;
    }

    default int reachedDistance() {
        return 5;
    }

    default boolean showTransportInfo() {
        return true;
    }

	// COST BEGIN
    default int costAgilityShortcuts()					{ return 0; }
    default int costGrappleShortcuts()					{ return 0; }
    default int costBoats()								{ return 0; }
    default int costCanoes()							{ return 0; }
    default int costCharterShips()						{ return 0; }
    default int costShips()								{ return 0; }
    default int costFairyRings()						{ return 0; }
    default int costGnomeGliders()						{ return 0; }
    default int costHotAirBalloons()					{ return 0; }
    default int costMagicCarpets()						{ return 0; }
    default int costMagicMushtrees()					{ return 0; }
    default int costMinecarts()							{ return 0; }
    default int costQuetzals()							{ return 0; }
    default int costSpiritTrees()						{ return 0; }
    default int costNonConsumableTeleportationItems()	{ return 0; }
    default int costConsumableTeleportationItems()		{ return 0; }
    default int costTeleportationBoxes()				{ return 0; }
    default int costTeleportationLevers()				{ return 0; }
    default int costTeleportationPortals()				{ return 0; }
    default int costTeleportationSpells()				{ return 0; }
    default int costTeleportationMinigames()			{ return 0; }
    default int costWildernessObelisks()				{ return 0; }
    default int costSeasonalTransports()				{ return 0; }
	// COST END

    // description = "Colour of the text of the tile counter and fairy ring codes",
    default Color colourText() {
        return Color.WHITE;
    }


	// DEBUG
    default boolean drawTransports() {
        return false;
    }

    default String builtTeleportationBoxes() {
        return "";
    }

    void setBuiltTeleportationBoxes(String content);

    default String builtTeleportationPortalsPoh() {
        return "";
    }

    void setBuiltTeleportationPortalsPoh(String content);
}
