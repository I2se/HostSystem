package fr.realcraft.host;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import fr.realcraft.host.databases.DataManagement;
import fr.realcraft.host.listeners.PlayerCanceled;
import fr.realcraft.host.listeners.PlayerChatAsync;
import fr.realcraft.host.listeners.PlayerInteract;
import fr.realcraft.host.listeners.PlayerJoin;
import fr.realcraft.host.utils.Bungee;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Host extends JavaPlugin implements Listener {

    private static Host Instance;
    PteroApplication app = PteroBuilder.createApplication("https://panel.realcraft.fr", "token");
    PteroClient client = PteroBuilder.createClient("https://panel.realcraft.fr", "token");

    @Override
    public void onEnable() {
        DataManagement.init();
        Instance = this;
        getLogger().info("Active");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        new Bungee(this);
        MenuManager.setup(getServer(), this);
        registerListeners();
    }

    @Override
    public void onDisable() {
        getLogger().info("Desactive");
        DataManagement.close();
    }

    public void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerCanceled(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerChatAsync(), this);
    }

    public PteroApplication getApp() {
        return app;
    }

    public PteroClient getClient() {
        return client;
    }

    public static Host getInstance() {
        return Instance;
    }
}
