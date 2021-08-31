package fr.realcraft.host.managers;

import com.google.gson.Gson;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import fr.realcraft.commons.Server;
import fr.realcraft.host.databases.management.sql.DatabaseManager;
import fr.realcraft.host.servers.ServerType;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerDB {

    private static Gson gson = new Gson();

    public static int createServerInDB(ApplicationServer server, Player player, boolean status, ServerType type, int nbrslot, String version, ArrayList<String> motd, List<Player> whitelist, String name, String material) {

        int idDb = 0;
        String inputStringMotd = gson.toJson(motd);
        String inputStringWhitelist = gson.toJson(whitelist);

        try {
            final Connection connection = DatabaseManager.SERVEUR.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO servers (serverId, version, port, owner, name, pseudo, motd, status, type, nbrslot, whitelist, material) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, server.getIdentifier());
            preparedStatement.setString(2, version);
            preparedStatement.setInt(3, Integer.parseInt(server.getAllocations().get().get(0).getPort()));
            preparedStatement.setString(4, player.getUniqueId().toString());
            preparedStatement.setString(5, name);
            preparedStatement.setString(6, player.getDisplayName());
            preparedStatement.setString(7, inputStringMotd);
            preparedStatement.setBoolean(8, status);
            preparedStatement.setString(9, type.toString());
            preparedStatement.setInt(10, nbrslot);
            preparedStatement.setString(11, inputStringWhitelist);
            preparedStatement.setString(12, material);

            final int row = preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(row > 0 && resultSet.next()) {
                idDb = resultSet.getInt(1);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idDb;
    }

    public static void deleteServerInDB(String serverId) {
        try {
            final Connection connection = DatabaseManager.SERVEUR.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM servers WHERE serverId = ?");

            preparedStatement.setString(1, serverId);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int reInsertServerInDB(String serverId, int port, UUID owner, String pseudo, boolean status, ServerType type, int nbrslot, String version, ArrayList<String> motd, List<Player> whitelist, String name, String material) {

        int idDb = 0;
        String inputStringMotd = gson.toJson(motd);
        String inputStringWhitelist = gson.toJson(whitelist);

        try {
            final Connection connection = DatabaseManager.SERVEUR.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO servers (serverId, version, port, owner, name, pseudo, motd, status, type, nbrslot, whitelist, material) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, serverId);
            preparedStatement.setString(2, version);
            preparedStatement.setInt(3, port);
            preparedStatement.setString(4, owner.toString());
            preparedStatement.setString(5, name);
            preparedStatement.setString(6, pseudo);
            preparedStatement.setString(7, inputStringMotd);
            preparedStatement.setBoolean(8, status);
            preparedStatement.setString(9, type.toString());
            preparedStatement.setInt(10, nbrslot);
            preparedStatement.setString(11, inputStringWhitelist);
            preparedStatement.setString(12, material);

            final int row = preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(row > 0 && resultSet.next()) {
                idDb = resultSet.getInt(1);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idDb;
    }

    public static void updateMaterial(Server server, String material) {
        try {
            final Connection connection = DatabaseManager.SERVEUR.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE servers SET material = ? WHERE serverId = ?");

            preparedStatement.setString(1, material);
            preparedStatement.setString(2, server.getServerId());
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
