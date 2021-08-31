package fr.realcraft.host.databases;

import fr.realcraft.host.databases.management.redis.RedisAccess;
import fr.realcraft.host.databases.management.sql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class DataManagement {

    public static void init() {
        PluginManager pm = Bukkit.getPluginManager();
        DatabaseManager.initAllDatabaseConnections();
        RedisAccess.init();
    }

    public static void close() {
        DatabaseManager.closeAllDatabaseConnections();
        RedisAccess.close();
    }
}
