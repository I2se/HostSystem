package fr.realcraft.host.listeners;

import fr.realcraft.commons.Server;
import fr.realcraft.host.Host;
import fr.realcraft.host.inventories.TransferData;
import fr.realcraft.host.managers.DataServer;
import fr.realcraft.host.menus.FinalCreationMenu;
import fr.realcraft.host.menus.SettingsMainMenu;
import fr.realcraft.host.menus.custom.CustomCreationMenu;
import fr.realcraft.host.menus.custom.CustomMenu;
import fr.realcraft.host.utils.TempServer;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatAsync implements Listener {

    @EventHandler
    public void onPlayerSendChat(AsyncPlayerChatEvent e) {
        if(CustomCreationMenu.getChatAsync().get(e.getPlayer()) != null) {
            if(CustomCreationMenu.getChatAsync().get(e.getPlayer()).equalsIgnoreCase("headValue")) {
                String message = e.getMessage();
                Player player = e.getPlayer();
                if(message.contains("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv")) {
                    e.setCancelled(true);
                    TempServer.getServeurInCreation().get(player).setMaterial(message);
                    CustomCreationMenu.getChatAsync().remove(player);
                    Bukkit.getScheduler().runTask(Host.getInstance(), () -> {
                        try {
                            MenuManager.openMenu(FinalCreationMenu.class, player);
                        } catch (MenuManagerException ex) {
                            ex.printStackTrace();
                        } catch (MenuManagerNotSetupException ex) {
                            ex.printStackTrace();
                        }
                    });
                } else {
                    player.sendMessage(ChatColor.RED + "Annulée");
                    CustomCreationMenu.getChatAsync().remove(player);
                    try {
                        MenuManager.openMenu(CustomCreationMenu.class, player);
                    } catch (MenuManagerException | MenuManagerNotSetupException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if(CustomCreationMenu.getChatAsync().get(e.getPlayer()).equalsIgnoreCase("head")) {
                String message = e.getMessage();
                Player player = e.getPlayer();
                if(message.contains("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv")) {
                    e.setCancelled(true);
                    Server server = null;
                    try {
                        server = MenuManager.getPlayerMenuUtility(player).getData(TransferData.TRANSITMENU, Server.class);
                        server.setMaterial(message);
                        MenuManager.getPlayerMenuUtility(player).setData(TransferData.TRANSITMENU, server);
                    } catch (MenuManagerException ex) {
                        ex.printStackTrace();
                    } catch (MenuManagerNotSetupException ex) {
                        ex.printStackTrace();
                    }
                    DataServer.updateMaterial(server, message);
                    CustomCreationMenu.getChatAsync().remove(player);
                    Bukkit.getScheduler().runTask(Host.getInstance(), () -> {
                        try {
                            MenuManager.openMenu(SettingsMainMenu.class, player);
                        } catch (MenuManagerException ex) {
                            ex.printStackTrace();
                        } catch (MenuManagerNotSetupException ex) {
                            ex.printStackTrace();
                        }
                    });
                } else {
                    player.sendMessage(ChatColor.RED + "Annulée");
                    CustomCreationMenu.getChatAsync().remove(player);
                    try {
                        MenuManager.openMenu(CustomMenu.class, player);
                    } catch (MenuManagerException | MenuManagerNotSetupException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
