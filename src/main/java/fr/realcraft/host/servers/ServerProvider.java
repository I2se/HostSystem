package fr.realcraft.host.servers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import fr.realcraft.commons.Server;
import fr.realcraft.host.databases.management.redis.RedisAccess;
import fr.realcraft.host.databases.management.sql.DatabaseManager;
import org.bukkit.entity.Player;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerProvider {

    public static final String REDIS_KEY = "servers:";
    private static RedisAccess redisAccess;
    private String serverId;
    private Gson gson = new Gson();

    public ServerProvider(String serverId) {
        this.serverId = serverId;
        this.redisAccess = RedisAccess.Instance;
    }

    public Server getServer() {

        Server server = getServerFromRedis();

        if(server == null) {
            server = getServerFromDatabase();

            sendServerToRedis(server);
        }
        return server;
    }

    public void sendServerToRedis(Server server) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + serverId;
        final RBucket<Server> serverRBucket = redissonClient.getBucket(key);

        serverRBucket.set(server);
    }

    private Server getServerFromRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + serverId;
        final RBucket<Server> serverRBucket = redissonClient.getBucket(key);

        return serverRBucket.get();
    }

    public void deleteProfileRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + serverId;
        final RBucket<Server> serverRBucket = redissonClient.getBucket(key);

        serverRBucket.delete();
    }

    private Server getServerFromDatabase() {
        Server server = null;
        try {
            final Connection connection = DatabaseManager.SERVEUR.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM servers WHERE serverId = ?");

            preparedStatement.setString(1, serverId);
            preparedStatement.executeQuery();

            final ResultSet resultSet = preparedStatement.getResultSet();
    
            if(resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String version = resultSet.getString("version");
                final int port = resultSet.getInt("port");
                final UUID owner = UUID.fromString(resultSet.getString("owner"));
                final String name = resultSet.getString("name");
                final String pseudo = resultSet.getString("pseudo");
                final String motdstring = resultSet.getString("motd");
                final boolean status = resultSet.getBoolean("status");
                final ServerType type = ServerType.valueOf(resultSet.getString("type"));
                final int nbrslot = resultSet.getInt("nbrslot");
                final String whiteliststring = resultSet.getString("whitelist");
                final String material = resultSet.getString("material");

                Type typeArrayList = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> motd = gson.fromJson(motdstring, typeArrayList);

                Type typeList = new TypeToken<List<Player>>() {}.getType();
                List<Player> whitelist = gson.fromJson(whiteliststring, typeList);

                server = new Server(id, serverId, version, port, owner, name, pseudo, motd, status, type, nbrslot, whitelist, material);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return server;
    }
}
