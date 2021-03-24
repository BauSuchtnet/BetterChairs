# Better Chairs Default Config
````yaml

  # BetterChairs Remastered
  # 
  # Support: https://Sprax.me/Discord
  # Updates and Information:
  # Statistics: https://bstats.org/plugin/bukkit/BetterChairs%20Remastered/8214
  # Information for developers: https://github.com/SpraxDev/BetterChairs/wiki


  # You shouldn't make any changes to this
  version: 2
  Chairs:

    # Allowed distance a player is allowed to have when trying to sit? (-1 to ignore)
    AllowedDistanceToChair: -1

    # Should a player automatically look forward when starting to sit
    AutoRotatePlayer: true

    # Does a player need his hands empty when trying to sit?
    NeedEmptyHands: true

    # Does a chair need signs on both sides attached to be detected as an chair
    NeedsSignsOnBothSides: false

    # Enable this if you want players to be able to sit on chairs
    # while other plugins (like WorldGuard or PlotSquared) are not
    # allowing interactions/use with the chair blocks.
    IgnoreOtherPluginsPreventingInteract: false

    # Enable this if you want BetterChairs to remember a player who used /bc <toggle|on|off> after a plugin reload or him rejoining
    RememberIfPlayerDisabledChairsAfterRelogin: true
    Position:

      # Set to false, if you do not care about a player suffocating while sitting
      NeedAirAbove: true

      # Set to false, to force chairs to have a block below them
      AllowAirBelow: true

    # Can stairs be chairs?
    UseStairs: true

    # Can half slabs be chairs too?
    UseSlabs: false
    LeavingChair:

      # Should a player be teleported to its original position when leaving a chair
      TeleportPlayerToOldLocation: true

      # Should a player keep his head rotation when teleported to its original position
      KeepHeadRotation: true
    Messages:

      # Should the player receive a message when the chair is already occupied
      AlreadyOccupied: false

      # Should the player receive a message when a chair is missing signs on both sided
      NeedsSignsOnBothSides: false

      # Should the player receive a message when he starts sitting
      NowSitting: false
    Regeneration:

      # Should player receive regeneration effect when sitting? (Needs permission BetterChairs.regeneration)
      Enabled: false

      # What amplifier should be applied?
      Amplifier: 1
  Furniture:

    # Can some furniture be chairs too?
    Use: true

    # List of Furniture to Use
    List:
      - OAK_CHAIR
      - SPRUCE_CHAIR
      - BIRCH_CHAIR
      - JUNGLE_CHAIR
      - ACACIA_CHAIR
      - DARK_OAK_CHAIR
      - STONE_CHAIR
      - GRANITE_CHAIR
      - DIORITE_CHAIR
      - ANDESITE_CHAIR
      - STRIPPED_OAK_CHAIR
      - STRIPPED_SPRUCE_CHAIR
      - STRIPPED_BIRCH_CHAIR
      - STRIPPED_JUNGLE_CHAIR
      - STRIPPED_ACACIA_CHAIR
      - STRIPPED_DARK_OAK_CHAIR
      - WHITE_SOFA
      - ORANGE_SOFA
      - MAGENTA_SOFA
      - LIGHT_BLUE_SOFA
      - YELLOW_SOFA
      - LIME_SOFA
      - PINK_SOFA
      - GRAY_SOFA
      - LIGHT_GRAY_SOFA
      - CYAN_SOFA
      - PURPLE_SOFA
      - BLUE_SOFA
      - BROWN_SOFA
      - GREEN_SOFA
      - RED_SOFA
      - BLACK_SOFA
      - RAINBOW_SOFA
      - OAK_PARK_BENCH
      - SPRUCE_PARK_BENCH
      - BIRCH_PARK_BENCH
      - JUNGLE_PARK_BENCH
      - ACACIA_PARK_BENCH
      - DARK_OAK_PARK_BENCH
      - STRIPPED_OAK_PARK_BENCH
      - STRIPPED_SPRUCE_PARK_BENCH
      - STRIPPED_BIRCH_PARK_BENCH
      - STRIPPED_JUNGLE_PARK_BENCH
      - STRIPPED_ACACIA_PARK_BENCH
      - STRIPPED_DARK_OAK_PARK_BENCH
  Filter:
    Worlds:

      # Should we only enable chairs in specific worlds?
      Enabled: false

      # Should be the list below be used as blacklist or whitelist?
      UseAsBlacklist: false

      # List of all enabled/disabled worlds
      Names:
        - worldname
        - worldname2
    Blocks:

      # Should we only enable specific blocks as chairs?
      Enabled: false

      # Setting this to true, won't check if a chair
      # is a stair or slab but only look if it is in the list below
      # 
      # This is kinda experimental.
      # Enabling overwrites 'UseStairs' and 'UseSlabs' further above
      AllowAllTypes: false

      # Should be the list below be used as blacklist or whitelist?
      UseAsBlacklist: false

      # List of all enabled/disabled block types
      # 
      # The names from Minecraft do not always work
      # Full list: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
      Names:
        - blockname
        - blockname2
  Updater:

    # Should we check for new versions and report to the console? (Recommended)
    CheckForUpdates: true

    # Should be notify admins when they join the server? (Permission: BetterChairs.updater)
    NotifyOnJoin: true

````