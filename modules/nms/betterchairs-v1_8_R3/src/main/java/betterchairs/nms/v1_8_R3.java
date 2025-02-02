package betterchairs.nms;

import de.sprax2013.betterchairs.ChairManager;
import de.sprax2013.betterchairs.ChairNMS;
import de.sprax2013.betterchairs.ChairUtils;
import de.sprax2013.betterchairs.Messages;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.material.Stairs;
import org.bukkit.material.Step;
import org.bukkit.material.WoodenStep;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public class v1_8_R3 extends ChairNMS {

    @Override
    public @NotNull ArmorStand spawnChairArmorStand(@NotNull Location loc, int regenerationAmplifier) {
        WorldServer nmsWorld = ((CraftWorld) Objects.requireNonNull(loc.getWorld())).getHandle();
        CustomArmorStand nmsArmorStand = new CustomArmorStand(
                nmsWorld, loc.getX(), loc.getY(), loc.getZ(), regenerationAmplifier);
        ArmorStand armorStand = (ArmorStand) nmsArmorStand.getBukkitEntity();

        ChairUtils.applyChairProtections(armorStand);

        if (!nmsWorld.addEntity(nmsArmorStand, CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            ChairManager.getLogger().warning(Messages.ERR_ANOTHER_PLUGIN_PREVENTING_SPAWN);
        }

        return armorStand;
    }

    @Override
    public void killChairArmorStand(@NotNull ArmorStand armorStand) {
        EntityArmorStand nmsArmorStand = ((CraftArmorStand) armorStand).getHandle();

        if (!(nmsArmorStand instanceof CustomArmorStand))
            throw new IllegalArgumentException(String.format(Messages.ERR_NOT_CUSTOM_ARMOR_STAND,
                    CustomArmorStand.class.getName()));

        ((CustomArmorStand) nmsArmorStand).remove = true;
        armorStand.remove();
    }

    @Override
    public boolean isStair(@NotNull Block block) {
        return block.getState().getData() instanceof Stairs;
    }

    @Override
    public boolean isStairUpsideDown(@NotNull Block block) {
        return ((Stairs) block.getState().getData()).isInverted();
    }

    @Override
    public @NotNull BlockFace getBlockRotation(@NotNull Block block) {
        return ChairUtils.getBlockRotationLegacy(block);
    }

    @Override
    public boolean isSlab(@NotNull Block block) {
        return (block.getState().getData() instanceof Step ||
                block.getState().getData() instanceof WoodenStep) &&
                block.getType() != Material.DOUBLE_STEP &&
                block.getType() != Material.WOOD_DOUBLE_STEP;
    }

    @Override
    public boolean isSlabTop(@NotNull Block block) {
        if (block.getState().getData() instanceof Step) {
            return ((Step) block.getState().getData()).isInverted();
        }

        return ((WoodenStep) block.getState().getData()).isInverted();
    }

    @Override
    public boolean hasEmptyMainHand(@NotNull Player player) {
        return player.getInventory().getItemInHand().getType() == Material.AIR;
    }

    @Override
    public boolean isChair(@NotNull ArmorStand armorStand) {
        return ((CraftArmorStand) armorStand).getHandle() instanceof CustomArmorStand;
    }

    private static class CustomArmorStand extends EntityArmorStand {
        private boolean remove = false;
        private final int regenerationAmplifier;

        /**
         * @param regenerationAmplifier provide a negative value to disable regeneration
         */
        public CustomArmorStand(World world, double d0, double d1, double d2, int regenerationAmplifier) {
            super(world, d0, d1, d2);

            this.regenerationAmplifier = regenerationAmplifier;
        }

        @Override
        public void g(float f, float f1) {
            if (remove) return; // If the ArmorStand is being removed, no need to bother
            if (this.ticksLived % 10 == 0) return;  // Only run every 10 ticks

            if (!(this.passenger instanceof EntityHuman)) {
                remove = true;
                this.bukkitEntity.remove();
                return;
            }

            // Rotate the ArmorStand together with its passenger
            this.setYawPitch(this.passenger.yaw, this.passenger.pitch * .5F);
            this.aK = this.yaw;

            if (this.regenerationAmplifier >= 0) {
                CraftHumanEntity p = ((EntityHuman) this.passenger).getBukkitEntity();

                if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
                    p.addPotionEffect(new PotionEffect(
                            PotionEffectType.REGENERATION, ChairNMS.REGENERATION_EFFECT_DURATION, this.regenerationAmplifier,
                            false, false), true);
                }
            }
        }

        @Override
        public void die() {
            // Prevents the ArmorStand from getting killed unexpectedly
            if (shouldDie()) super.die();
        }

        private boolean shouldDie() {
            return remove || this.passenger == null || !(this.passenger instanceof EntityHuman);
        }
    }
}