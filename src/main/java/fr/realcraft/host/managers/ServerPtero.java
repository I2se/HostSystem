package fr.realcraft.host.managers;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.ServerStatus;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import fr.realcraft.host.Host;
import fr.realcraft.host.servers.ServerType;
import fr.realcraft.host.utils.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ServerPtero {

    public static PteroApplication app = Host.getInstance().getApp();
    public static PteroClient client = Host.getInstance().getClient();

    public static ApplicationServer createServerInPtero(ServerType type, int eggnumber, long memory, Player player) {

        String name = RandomStringUtils.randomAlphanumeric(6) + " - " + Utils.affichageServerType(type);

        List<Integer> ports = new ArrayList<>();
        for (ApplicationServer applicationServer : app.retrieveServersByOwner(app.retrieveUserById(2).execute()).execute()) {
            int port = Integer.parseInt(applicationServer.getAllocations().get().get(0).getPort());
            if(port >= 25600 && port <= 25650) {
                ports.add(port);
            }
        }
        int port = 25600;
        int i = port;
        if(!ports.isEmpty()) {
            for(; i <= 25700; i++ ) {
                if(!ports.contains(i)) {
                    port = i;
                    break;
                }
            }
        }

        Nest nest = app.retrieveNestById("5").execute();
        Location location =app.retrieveLocationById("1").execute();
        ApplicationEgg egg = app.retrieveEggById(nest, eggnumber).execute();

        Map<String, EnvironmentValue<?>> map = new HashMap<>();
        map.put("SERVER_JARFILE", EnvironmentValue.ofString("server.jar"));
        map.put("VERSION", EnvironmentValue.ofString("latest"));

        PteroAction<ApplicationServer> action = app.createServer()
                .setName(name)
                .setDescription("Serveur de " + player.getDisplayName())
                .setOwner(app.retrieveUserById("2").execute())
                .setEgg(egg)
                .setLocation(location)
                .setCPU(0L)
                .setDisk(disk, DataType.GB)
                .setMemory(memory, DataType.GB)
                .setPort(port)
                .setEnvironment(map)
                .startOnCompletion(true);

        ApplicationServer server = action.execute();

        Bukkit.getScheduler().runTaskAsynchronously(Host.getInstance(), () -> {
            while (app.retrieveServerById(server.getId()).delay(1, TimeUnit.SECONDS).execute().getStatus().equals(ServerStatus.INSTALLING)) {

            }
            client.retrieveServerByIdentifier(server.getIdentifier()).flatMap(ClientServer::start).executeAsync();
        });

        return server;
    }

    public static void deleteServerInPtero(String serverId) {

        ClientServer clientServer = client.retrieveServerByIdentifier(serverId).execute();
        String id = clientServer.getInternalId();
        app.retrieveServerById(id).flatMap(applicationServer -> applicationServer.getController().delete(false)).executeAsync();

    }
}
