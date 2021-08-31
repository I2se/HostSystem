package fr.realcraft.host.menus;

import fr.realcraft.commons.Server;
import fr.realcraft.host.inventories.ItemBuilder;
import fr.realcraft.host.inventories.SothisMenu;
import fr.realcraft.host.inventories.TransferData;
import fr.realcraft.host.managers.DataServer;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DeleteConfirmMenu extends SothisMenu {

    public DeleteConfirmMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    Player player = playerMenuUtility.getOwner();
    Server server = playerMenuUtility.getData(TransferData.TRANSITMENU, Server.class);

    @Override
    public String getMenuName() {
        return "" + ChatColor.GOLD + ChatColor.BOLD + "Confirmation";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) throws MenuManagerNotSetupException, MenuManagerException {

        switch (event.getCurrentItem().getType()) {
            case RED_CONCRETE:
                MenuManager.openMenu(SettingsMainMenu.class, player);
                DataServer.reInsertDataInDatabase(server);
                break;

            case GREEN_CONCRETE:
                MenuManager.openMenu(MainMenu.class, player);
                DataServer.deleteServer(server);
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemBuilder deco = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ");
        ItemBuilder confirm = new ItemBuilder(Material.GREEN_CONCRETE, 1).setName("Confirmer");
        ItemBuilder cancel = new ItemBuilder(Material.RED_CONCRETE, 1).setName("Annuler");

        inventory.setItem(3, confirm.toItemStack());
        inventory.setItem(5, cancel.toItemStack());

        setFillerGlass();
    }
}
