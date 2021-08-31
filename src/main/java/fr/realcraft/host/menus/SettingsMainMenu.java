package fr.realcraft.host.menus;

import fr.realcraft.commons.Server;
import fr.realcraft.host.inventories.ItemBuilder;
import fr.realcraft.host.inventories.SothisMenu;
import fr.realcraft.host.inventories.TransferData;
import fr.realcraft.host.managers.ServerDB;
import fr.realcraft.host.managers.ServerRedis;
import fr.realcraft.host.menus.custom.CustomMenu;
import fr.realcraft.host.utils.Utils;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class SettingsMainMenu extends SothisMenu {

    public SettingsMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    Player player = playerMenuUtility.getOwner();
    Server server = playerMenuUtility.getData(TransferData.TRANSITMENU, Server.class);

    @Override
    public String getMenuName() {
        return "" + ChatColor.GOLD + ChatColor.BOLD + "Paramètres";
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

        ArrayList<String> lore3 = new ArrayList<>();
        lore3.add("");
        lore3.add(ChatColor.GREEN + "The server is still starting");
        lore3.add(ChatColor.DARK_GRAY + "Click to refresh status");
        ItemBuilder starting = new ItemBuilder(Material.ORANGE_CONCRETE, 1).setName(ChatColor.YELLOW + "Starting").setLore(lore3);

        ArrayList<String> lore4 = new ArrayList<>();
        lore4.add("");
        lore4.add(ChatColor.RED + "The server is stopping");
        lore4.add(ChatColor.DARK_GRAY + "Click to refresh status");
        ItemBuilder stopping = new ItemBuilder(Material.LIGHT_GRAY_CONCRETE, 1).setName(ChatColor.DARK_RED + "Stopping").setLore(lore4);

        switch (event.getCurrentItem().getType()) {
            case RED_WOOL:
                MenuManager.openMenu(DeleteConfirmMenu.class, player);
                ServerDB.deleteServerInDB(server.getServerId());
                ServerRedis.deleteServerInRedis(server.getServerId());
                break;
            //Status
            case GREEN_CONCRETE:
                Utils.stopServer(server);
                inventory.setItem(34, stopping.toItemStack());
                break;
            case RED_CONCRETE:
                Utils.startServer(server);
                inventory.setItem(34, starting.toItemStack());
                break;
        }

        if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Icon")) {
            MenuManager.openMenu(CustomMenu.class, player);
        }
    }

    @Override
    public void setMenuItems() {
        ItemBuilder name = new ItemBuilder(Material.SPRUCE_SIGN, 1).setName("Modifier le nom");
        ItemBuilder motd = new ItemBuilder(Material.REPEATER, 1).setName("Modifier le motd");
        ItemBuilder nbrslot = new ItemBuilder(Material.CHEST, 1).setName("Modifier le nombre de slot");
        ItemBuilder publiq = new ItemBuilder(Material.GRASS_BLOCK, 1).setName("Publique");

        ItemBuilder delete = new ItemBuilder(Material.RED_WOOL, 1).setName("Souhaitez vous supprimer le serveur ?");
        ItemBuilder persona = new ItemBuilder(Utils.getCustomItemBuilder(server.getMaterial())).setName(ChatColor.AQUA + "Icon");
        ItemBuilder whitelist = new ItemBuilder(Material.COMPARATOR, 1).setName("Modifier la whitelist");

        ItemBuilder status = Utils.showStatus(server);

        inventory.setItem(10, name.toItemStack());
        inventory.setItem(12, motd.toItemStack());
        inventory.setItem(14, nbrslot.toItemStack());
        inventory.setItem(16, publiq.toItemStack());

        inventory.setItem(28, delete.toItemStack());
        inventory.setItem(30, persona.toItemStack());
        inventory.setItem(32, whitelist.toItemStack());
        inventory.setItem(34, status.toItemStack());

        setFillerGlass();
    }
}
