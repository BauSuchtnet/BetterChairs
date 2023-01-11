package de.sprax2013.betterchairs;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.material.Directional;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class provides utility methods intended to be used by BetterChairs's nms-classes
 */
public class ChairUtils {


    private ChairUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void applyChairProtections(Plugin plugin, ArmorStand armorStand) {
        armorStand.setGravity(false);
        armorStand.setVisible(false);

        // Chairs should always be removed... Just making sure.
        armorStand.setRemoveWhenFarAway(true);

        NBT.modify(armorStand, readWriteNBT -> {
            readWriteNBT.setBoolean("Invulnerable", true);
            readWriteNBT.setInteger("DisabledSlots", 0b11111);
        });
        armorStand.setMetadata("Chair", new FixedMetadataValue(plugin, true));
    }

    public static boolean isChair(ArmorStand armorStand) {
        return armorStand.hasMetadata("Chair");
    }

    /**
     * This method checks {@link Directional#getFacing()} (legacy API in the current Bukkit api) and
     * inverts it to show the Block's rotation.
     *
     * <b>If the block is not facing to {@code NORTH}, {@code SOUTH}, {@code EAST} or {@code WEST},
     * the value is returned without inverting it</b>
     *
     * @param b The block to check
     * @return The inverted BlockFace as described above, or {@link BlockFace#SELF} if the
     * Bukkit-api is too old
     */
    public static BlockFace getBlockRotationLegacy(Block b) {
        try {
            BlockFace blockFace = ((Directional) b.getState().getData()).getFacing();

            if (blockFace == BlockFace.NORTH) {
                return BlockFace.SOUTH;
            }
            if (blockFace == BlockFace.SOUTH) {
                return BlockFace.NORTH;
            }
            if (blockFace == BlockFace.WEST) {
                return BlockFace.EAST;
            }
            if (blockFace == BlockFace.EAST) {
                return BlockFace.WEST;
            }

            return blockFace;
        } catch (Exception ignore) {
            // Feature not supported on this version of the Bukkit-api
        }

        return BlockFace.SELF;
    }

    public static void applyRegeneration(HumanEntity p, int regenerationAmplifier) {
        if (regenerationAmplifier >= 0 && !p.hasPotionEffect(PotionEffectType.REGENERATION)) {
            p.addPotionEffect(new PotionEffect(
                PotionEffectType.REGENERATION, ChairNMS.REGENERATION_EFFECT_DURATION,
                regenerationAmplifier,
                false, false), true);
        }
    }
}