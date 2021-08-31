package fr.realcraft.host.managers;

import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import fr.realcraft.commons.Server;
import fr.realcraft.host.servers.ServerProvider;
import fr.realcraft.host.servers.ServerType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerRedis {

    

    public static void reInsertServerInRedis(String serverId, int idDb, int port, UUID owner, String pseudo, String version, ArrayList<String> motd, boolean status, ServerType type, int nbrslot, List<Player> whitelist, String name, String material) {
        Server serverToRedis = new Server(idDb,
                serverId,
                version,
        public static void createServerInRedis(ApplicationServer server, int idDb, String version, Player player, ArrayList<String> motd, boolean status, ServerType type, int nbrslot, List<Player> whitelist, String name, String material) {
            Server serverToRedis = new Server(idDb,
                    server.getIdentifier(),
                    version,
                    Integer.parseInt(server.getAllocations().get().get(0).getPort()),
                    player.getUniqueId(),
                    player.getDisplayName(),
                    name,
                    motd,
                    status,
                    type,
                    nbrslot,
                    whitelist,
                    material);
            ServerProvider serverProvider = new ServerProvider(server.getIdentifier());
            serverProvider.sendServerToRedis(serverToRedis);
        }

        public static void deleteServerInRedis(String serverId) {
            ServerProvider serverProvider = new ServerProvider(serverId);
            serverProvider.deleteProfileRedis();
        }     port,
                owner,
                pseudo,
                name,
                motd,
                status,
                type,
                nbrslot,
                whitelist,
                material);
        ServerProvider serverProvider = new ServerProvider(serverId);
        serverProvider.sendServerToRedis(serverToRedis);
    }

    public static void updateMaterial(Server server, String material) {
        ServerProvider serverProvider = new ServerProvider(server.getServerId());
        Server serverRedis = serverProvider.getServer();
        serverRedis.setMaterial(material);
        serverProvider.sendServerToRedis(serverRedis);
    }
}
