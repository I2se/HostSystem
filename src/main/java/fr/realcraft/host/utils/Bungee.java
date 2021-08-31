package fr.realcraft.host.utils;

import fr.realcraft.commons.Server;
import fr.realcraft.host.Host;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Bungee {

    private static Host host;

    public Bungee(Host host) {
        this.host = host;
    }

    public static void connect(Player player, Server server) {
        int port = server.getPort();
        String serverToConnect = "server" + port;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(serverToConnect);
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        player.sendPluginMessage(host, "BungeeCord", b.toByteArray());
        player.sendMessage("§fConnexion au serveur : §6§l" + server.getName());
    }
}
