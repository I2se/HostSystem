package fr.realcraft.host.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketListener extends ChannelInboundHandlerAdapter {

    Player player;

    PacketListener(Player player) {
        this.player = player;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        super.channelRead(channelHandlerContext, o);
    }

    public static void registerPlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().b.a.k;
        channel.pipeline().addBefore("packet_handler", "packet_listenerin", new PacketListener(player));
    }
}
