package fr.realcraft.host.packet;

import fr.realcraft.host.utils.ReflectionUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Method {

    public static void openSign(Player player) {
        Location location = player.getLocation();
        BlockPosition blockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        ReflectionUtils.sendPacket(player, new PacketPlayOutOpenSignEditor(blockPosition));
    }
}
