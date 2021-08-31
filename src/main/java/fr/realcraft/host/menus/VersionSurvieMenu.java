package fr.realcraft.host.menus;

import fr.realcraft.host.inventories.ItemBuilder;
import fr.realcraft.host.inventories.SothisMenu;
import fr.realcraft.host.servers.VersionServer;
import fr.realcraft.host.utils.TempServer;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class VersionSurvieMenu extends SothisMenu {

    public VersionSurvieMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    Player player = playerMenuUtility.getOwner();

    @Override
    public String getMenuName() {
        return "" + ChatColor.GOLD + ChatColor.BOLD + "Choisir la version";
    }

    @Override
    public int getSlots() {
        return 5*9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) throws MenuManagerNotSetupException, MenuManagerException {
        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case "Version 1.8.8":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V18.getVersion());
                break;

            case "Version 1.9.4":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V19.getVersion());
                break;

            case "Version 1.10.2":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V110.getVersion());
                break;

            case "Version 1.11.2":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V111.getVersion());
                break;

            case "Version 1.12.2":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V112.getVersion());
                break;

            case "Version 1.13.2":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V113.getVersion());
                break;

            case "Version 1.14.4":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V114.getVersion());
                break;

            case "Version 1.15.2":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V115.getVersion());
                break;

            case "Version 1.16.5":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V116.getVersion());
                break;

            case "Version 1.17.1":
                TempServer.getServeurInCreation().get(player).setVersion(VersionServer.V117.getVersion());
                break;
        }

        if(event.getCurrentItem().getType() != Material.LIGHT_BLUE_STAINED_GLASS_PANE) {
            MenuManager.openMenu(FinalCreationMenu.class, player);
        }
    }

    @Override
    public void setMenuItems() {
        ItemBuilder v18 = new ItemBuilder(Material.GRASS_BLOCK, 1).setName(VersionServer.V18.getName());
        ItemBuilder v19 = new ItemBuilder(Material.END_STONE_BRICKS, 1).setName(VersionServer.V19.getName());
        ItemBuilder v110 = new ItemBuilder(Material.MAGMA_BLOCK, 1).setName(VersionServer.V110.getName());
        ItemBuilder v111 = new ItemBuilder(Material.TOTEM_OF_UNDYING, 1).setName(VersionServer.V111.getName());
        ItemBuilder v112 = new ItemBuilder(Material.YELLOW_CONCRETE, 1).setName(VersionServer.V112.getName());
        ItemBuilder v113 = new ItemBuilder(Material.TRIDENT, 1).setName(VersionServer.V113.getName());
        ItemBuilder v114 = new ItemBuilder(Material.CROSSBOW, 1).setName(VersionServer.V114.getName());
        ItemBuilder v115 = new ItemBuilder(Material.HONEYCOMB, 1).setName(VersionServer.V115.getName());
        ItemBuilder v116 = new ItemBuilder(Material.ANCIENT_DEBRIS, 1).setName(VersionServer.V116.getName());
        ItemBuilder v117 = new ItemBuilder(Material.AMETHYST_CLUSTER, 1).setName(VersionServer.V117.getName());

        inventory.setItem(10, v18.toItemStack());
        inventory.setItem(12, v19.toItemStack());
        inventory.setItem(14, v110.toItemStack());
        inventory.setItem(16, v111.toItemStack());
        inventory.setItem(20, v112.toItemStack());
        inventory.setItem(24, v113.toItemStack());
        inventory.setItem(28, v114.toItemStack());
        inventory.setItem(30, v115.toItemStack());
        inventory.setItem(32, v116.toItemStack());
        inventory.setItem(34, v117.toItemStack());

        setFillerGlass();
    }
}
