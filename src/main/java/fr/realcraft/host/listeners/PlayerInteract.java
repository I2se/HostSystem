package fr.realcraft.host.listeners;

import fr.realcraft.host.Host;
import fr.realcraft.host.menus.MainMenu;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {

    private Host host;
    public PlayerInteract(Host host) {
        this.host = host;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getItem() != null) {
            ItemStack item = e.getItem();
            if (item.getType() == Material.COMPASS || item.getItemMeta().getDisplayName().equals("Creator")) {
                try {
                    MenuManager.openMenu(MainMenu.class, player);
                } catch (MenuManagerException menuManagerException) {
                    menuManagerException.printStackTrace();
                } catch (MenuManagerNotSetupException menuManagerNotSetupException) {
                    menuManagerNotSetupException.printStackTrace();
                }
            }
        }

    }

}
