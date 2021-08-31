package fr.realcraft.host.menus;

import fr.realcraft.host.inventories.ItemBuilder;
import fr.realcraft.host.inventories.SothisMenu;
import fr.realcraft.host.managers.DataServer;
import fr.realcraft.host.menus.custom.CustomCreationMenu;
import fr.realcraft.host.packet.Method;
import fr.realcraft.host.utils.TempServer;
import fr.realcraft.host.utils.Utils;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FinalCreationMenu extends SothisMenu {

    public FinalCreationMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    Player player = playerMenuUtility.getOwner();

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
        switch (event.getCurrentItem().getType()) {
            case GREEN_CONCRETE:
                DataServer.createServer(player);
                TempServer.getServeurInCreation().remove(player);
                player.sendTitle(ChatColor.GOLD + "Création de votre serveur en cours", ChatColor.AQUA + "Veuillez patientez", 30, 120, 30);
                MenuManager.openMenu(MainMenu.class, player);
                break;

            case RED_CONCRETE:
                TempServer.getServeurInCreation().remove(player);
                MenuManager.openMenu(MainMenu.class, player);
                break;

            case SPRUCE_SIGN:
                player.closeInventory();
                Method.openSign(player);
                break;
        }

        if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Icon")) {
            MenuManager.openMenu(CustomCreationMenu.class, player);
        }
    }

    @Override
    public void setMenuItems() {
        ItemBuilder name = new ItemBuilder(Material.SPRUCE_SIGN, 1).setName("Modifier le nom");
        ItemBuilder motd = new ItemBuilder(Material.REPEATER, 1).setName("Modifier le motd");
        ItemBuilder whitelist = new ItemBuilder(Material.COMPARATOR, 1).setName("Whitelist");
        ItemBuilder publiq = new ItemBuilder(Material.GRASS_BLOCK, 1).setName("Publique");
        ItemBuilder nbrslot = new ItemBuilder(Material.CHEST, 1).setName("Nombre Slot");
        ItemBuilder persona = new ItemBuilder(Utils.getCustomItemBuilder(TempServer.getServeurInCreation().get(player).getMaterial())).setName(ChatColor.AQUA + "Icon");

        ItemBuilder valid = new ItemBuilder(Material.GREEN_CONCRETE, 1).setName("Valider");
        ItemBuilder cancel = new ItemBuilder(Material.RED_CONCRETE, 1).setName("Cancel");

        inventory.setItem(10, motd.toItemStack());
        inventory.setItem(12, name.toItemStack());
        inventory.setItem(14, persona.toItemStack());
        inventory.setItem(16, nbrslot.toItemStack());
        inventory.setItem(20, whitelist.toItemStack());
        inventory.setItem(24, publiq.toItemStack());

        inventory.setItem(30, valid.toItemStack());
        inventory.setItem(32, cancel.toItemStack());

        setFillerGlass();
    }
}
