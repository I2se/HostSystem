package fr.realcraft.host.managers;

import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import fr.realcraft.commons.Server;
import fr.realcraft.host.servers.ServerProvider;
import fr.realcraft.host.servers.ServerType;
import fr.realcraft.host.servers.VersionServer;
import fr.realcraft.host.utils.TempServer;
import fr.realcraft.host.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataServer {

    public static void createServer(Player player) {
        TempServer tempServer = TempServer.getServeurInCreation().get(player);
        String version = tempServer.getVersion();
        int eggnumber = VersionServer.getByVersion(version).getEggnumber();
        ServerType type = tempServer.getType();
        long memory = tempServer.getMemory();
        boolean status = tempServer.isStatus();
        int nbrslot = tempServer.getNbrslot();
        String name = tempServer.getName() + Utils.affichageServerType(type);
        ArrayList<String> motd = tempServer.getMotd();
        List<Player> whitelist = tempServer.getWhitelist();
        String material = tempServer.getMaterial();

        ApplicationServer applicationServer = ServerPtero.createServerInPtero(type, eggnumber, memory, player);

        int ID = ServerDB.createServerInDB(applicationServer, player, status, type, nbrslot, version, motd, whitelist, name, material);

        ServerRedis.createServerInRedis(applicationServer, ID, version, player, motd, status, type, nbrslot, whitelist, name, material);
    }

    public static void deleteServer(Server server) {
        String serverID = server.getServerId();

        ServerPtero.deleteServerInPtero(serverID);

        ServerProvider serverProvider = new ServerProvider(serverID);
        Server serverDb = serverProvider.getServer();

        if(!(serverDb == null)) {
            ServerDB.deleteServerInDB(serverID);
            ServerRedis.deleteServerInRedis(serverID);
        }
    }

    public static void reInsertDataInDatabase(Server server) {
        String serverId = server.getServerId();
        int port = server.getPort();
        UUID owner = server.getOwner();
        String pseudo = server.getPseudo();
        boolean status = server.isStatus();
        ServerType type = server.getType();
        int nbrslot = server.getNbrslot();
        String version = server.getVersion();
        ArrayList<String> motd = server.getMotd();
        List<Player> whitelist = server.getWhitelist();
        String name = server.getName();
        String material = server.getMaterial();

        int ID = ServerDB.reInsertServerInDB(serverId, port, owner, pseudo, status, type, nbrslot, version, motd, whitelist, name, material);

        ServerRedis.reInsertServerInRedis(serverId, ID, port, owner, pseudo, version, motd, status, type, nbrslot, whitelist, name, material);
    }

    public static void updateMaterial(Server server, String material) {
        ServerDB.updateMaterial(server, material);
        ServerRedis.updateMaterial(server, material);
    }
}
