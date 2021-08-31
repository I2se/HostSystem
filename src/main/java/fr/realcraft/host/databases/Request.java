package fr.realcraft.host.databases;

import fr.realcraft.commons.Server;
import fr.realcraft.host.databases.management.sql.DatabaseManager;
import fr.realcraft.host.servers.ServerProvider;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Request {

    public static List<String> getAllServerId() {
        List<String> listServerId = new ArrayList<>();
        try {
            final Connection connection = DatabaseManager.SERVEUR.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT serverId FROM servers");
            preparedStatement.executeQuery();
            final ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet != null) {
                while(resultSet.next()) {
                    listServerId.add(resultSet.getString("serverId"));
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listServerId;
    }

    public static List<String> getAllServerIdForPlayer(Player player) {
        List<String> listServerId = new ArrayList<>();
        try {
            final Connection connection = DatabaseManager.SERVEUR.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT serverId FROM servers WHERE owner = ?");

            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.executeQuery();

            final ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet != null) {
                while(resultSet.next()) {
                    listServerId.add(resultSet.getString("serverId"));
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listServerId;
    }

    public static List<Server> getAllServers() {
        List<Server> listServers = new ArrayList<>();
        List<String> listServerId = Request.getAllServerId();
        if(listServerId != null) {
            for (String s : listServerId) {
                final ServerProvider serverProvider = new ServerProvider(s);
                listServers.add(serverProvider.getServer());
            }
        }
        return listServers;
    }

    public static List<Server> getAllServersOfPlayer(Player player) {
        List<Server> serversplayer = new ArrayList<>();
        List<String> serverIds = getAllServerIdForPlayer(player);
        if(serverIds != null) {
            for (String serverID : serverIds) {
                ServerProvider serverProvider = new ServerProvider(serverID);
                serversplayer.add(serverProvider.getServer());
            }
        }
        return serversplayer;
    }

    public static List<Server> getAllServersPubliq() {
        List<Server> listServer = new ArrayList<>();
        try {
            final Connection connection = DatabaseManager.SERVEUR.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT serverId FROM servers WHERE status = 0");
            preparedStatement.executeQuery();

            final ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet != null) {
                while(resultSet.next()) {
                    ServerProvider serverProvider = new ServerProvider(resultSet.getString("serverId"));
                    Server server = serverProvider.getServer();
                    listServer.add(server);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listServer;
    }
}
