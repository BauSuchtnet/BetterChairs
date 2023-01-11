package betterchairs.nms;

import de.sprax2013.betterchairs.ChairNMS;
import de.sprax2013.betterchairs.ChairUtils;
import de.sprax2013.betterchairs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class v1_19_R1 extends ChairNMS {

    @Override
    public @NotNull
    ArmorStand spawnChairArmorStand(Plugin plugin, @NotNull Location loc,
        int regenerationAmplifier) {
        Bukkit.broadcast("trying to spawn armorstand", "debug");
        ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class);
        ChairUtils.applyChairProtections(plugin, armorStand);
        Bukkit.broadcast("spawned successfully", "debug");
        return armorStand;
    }

    @Override
    public void killChairArmorStand(@NotNull ArmorStand armorStand) {
        if (!ChairUtils.isChair(armorStand)) {
            throw new IllegalArgumentException(String.format(Messages.ERR_NOT_CUSTOM_ARMOR_STAND,
                armorStand.getName()));
        }
        armorStand.remove();
    }

    @Override
    public boolean isStair(@NotNull Block block) {
        return block.getBlockData() instanceof Stairs;
    }

    @Override
    public boolean isStairUpsideDown(@NotNull Block block) {
        return ((Stairs) block.getBlockData()).getHalf() == Bisected.Half.TOP;
    }

    @Override
    public @NotNull
    BlockFace getBlockRotation(@NotNull Block block) {
        return ((Directional) block.getBlockData()).getFacing();
    }

    @Override
    public boolean isSlab(@NotNull Block block) {
        return block.getBlockData() instanceof Slab
            && ((Slab) block.getBlockData()).getType() != Slab.Type.DOUBLE;
    }

    @Override
    public boolean isSlabTop(@NotNull Block block) {
        return ((Slab) block.getBlockData()).getType() == Slab.Type.TOP;
    }

    @Override
    public boolean hasEmptyMainHand(@NotNull Player player) {
        return player.getInventory().getItemInMainHand().getType() == Material.AIR;
    }

}