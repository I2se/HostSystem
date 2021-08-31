package fr.realcraft.host.listeners;

import fr.realcraft.host.Host;
import fr.realcraft.host.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private Host host;
    public PlayerJoin(Host host) {
        this.host = host;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ItemBuilder compass = new ItemBuilder(Material.COMPASS).setName("Creator");

        e.getPlayer().getInventory().setItem(4, compass.toItemStack());

        e.setJoinMessage("Bienvenue sur RealCraft " + e.getPlayer().getDisplayName());
    }

}
