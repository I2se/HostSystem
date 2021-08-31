package fr.realcraft.host.menus;

import fr.realcraft.host.inventories.ItemBuilder;
import fr.realcraft.host.inventories.SothisMenu;
import fr.realcraft.host.servers.ServerType;
import fr.realcraft.host.utils.TempServer;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CreationServerMenu extends SothisMenu {

    public CreationServerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    Player player = playerMenuUtility.getOwner();

    @Override
    public String getMenuName() {
        return "" + ChatColor.GOLD + ChatColor.BOLD + "Choisir le type de serveur";
    }

    @Override
    public int getSlots() {
        return 3*9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) throws MenuManagerNotSetupException, MenuManagerException {
        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case "Survie":
                MenuManager.openMenu(VersionSurvieMenu.class, player);
                TempServer.getServeurInCreation().get(player).setType(ServerType.SURVIVAL);
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemBuilder deco = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, (short) 3).setName(" ");

        ItemBuilder minigames = new ItemBuilder(Material.ELYTRA, 1).setName("Mini-Jeux");
        ItemBuilder survie = new ItemBuilder(Material.GRASS_BLOCK, 1).setName("Survie");
        ItemBuilder map = new ItemBuilder(Material.BOW, 1).setName("Map Aventure");
        ItemBuilder mod = new ItemBuilder(ItemBuilder.getHead("icmodded")).setName("Moddée");

        inventory.setItem(10, minigames.toItemStack());
        inventory.setItem(12, survie.toItemStack());
        inventory.setItem(14, map.toItemStack());
        inventory.setItem(16, mod.toItemStack());

        setFillerGlass();
    }
}
