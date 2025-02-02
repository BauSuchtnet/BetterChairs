package de.sprax2013.betterchairs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Provides abstraction to be used in maven modules with the specified spigot version<br><br>
 * <b>Why not just use the Fallback-ChairNMS in {@code #onEnable()}?</b><br>
 * <ul>
 *     <li>Using the fallback in newer versions requires Spigot to enable old material support</li>
 *     <li>NMS allows us to easily rotate the ArmorStand together with the player
 *     (without using {@link org.bukkit.event.player.PlayerMoveEvent})</li>
 * </ul>
 */
public abstract class ChairNMS {
    public static final int REGENERATION_EFFECT_DURATION = 3 * 20;    // In Ticks

    /**
     * Spawns an ArmorStand that is/has:
     * <ul>
     *     <li>Invisible</li>
     *     <li>Invincible</li>
     *     <li>NoGravity</li>
     *     <li>DisabledSlots</li>
     * </ul>
     * The ArmorStand may fulfill the above with the help of {@link #getListener()}.
     *
     * @param loc                   The location for the Chair-ArmorStand
     * @param regenerationAmplifier The amplifier for the regeneration effect
     * @return The created ArmorStand to be used for an Chair
     * @see #getRegenerationAmplifier(Player)
     */
    @NotNull
    public abstract ArmorStand spawnChairArmorStand(@NotNull Location loc, int regenerationAmplifier);

    /**
     * @param armorStand The ArmorStand that should be deleted
     * @throws IllegalArgumentException if {@code armorStand} is not an instance of CustomArmorStand
     */
    public abstract void killChairArmorStand(@NotNull ArmorStand armorStand);

    public abstract boolean isStair(@NotNull Block block);

    public abstract boolean isStairUpsideDown(@NotNull Block block);

    @NotNull
    public abstract BlockFace getBlockRotation(@NotNull Block block);

    /**
     * @param block The {@link Block} to check
     * @return true if the block is a half slab (not double slab!), false otherwise
     */
    public abstract boolean isSlab(@NotNull Block block);

    public abstract boolean isSlabTop(@NotNull Block block);

    /**
     * **FURNITURE MODIFICATION**
     *
     * @param block The {@link Block} to check
     * @return true if the block is funtiure
     */
    public boolean isFurniture(@NotNull Block block) {
        return Objects.requireNonNull(Settings.FURNITURE.getValueAsStringList()).contains(block.getType().name());
    }


    public abstract boolean hasEmptyMainHand(@NotNull Player player);

    /**
     * Checks if an ArmorStand is or will be used as {@link Chair} with NMS.<br>
     * This method should be used to identify a {@link Chair} before it has been spawned into the world.<br>
     * This can for example be used to un-cancel an {@link org.bukkit.event.entity.EntitySpawnEvent}
     *
     * @param armorStand {@link ArmorStand} to check
     * @return true if ArmorStand is or will be used as Chair, false otherwise
     * @see ChairManager#isChair(ArmorStand)
     */
    public abstract boolean isChair(@NotNull ArmorStand armorStand);

    @Nullable
    public Listener getListener() {
        return null;
    }

    public static int getRegenerationAmplifier(Player p) {
        if (!Settings.REGENERATION_ENABLED.getValueAsBoolean() ||
                Settings.REGENERATION_AMPLIFIER.getValueAsInt() <= 0 ||
                !p.hasPermission(ChairManager.plugin.getName() + ".regeneration")) return -1;

        return Settings.REGENERATION_AMPLIFIER.getValueAsInt() - 1;
    }
}