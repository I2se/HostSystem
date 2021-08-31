package fr.realcraft.host.listeners;

import fr.realcraft.host.Host;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerClick implements Listener {

    private Host host;
    public PlayerClick(Host host) {
        this.host = host;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

    }

}
