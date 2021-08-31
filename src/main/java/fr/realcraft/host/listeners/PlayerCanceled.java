package fr.realcraft.host.listeners;

import fr.realcraft.host.Host;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerCanceled implements Listener {

    private Host host;
    public PlayerCanceled(Host host) {
        this.host = host;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        Player player = e.getPlayer();
        if (player.hasPermission("xeralobby.admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        if (player.hasPermission("xeralobby.admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if (player.hasPermission("xeralobby.admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        Player player = e.getPlayer();
        if (player.hasPermission("xeralobby.admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            e.setCancelled(!player.hasPermission("xeralobby.admin"));
        }
    }

    @EventHandler
    public void onLostHealth(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

}
