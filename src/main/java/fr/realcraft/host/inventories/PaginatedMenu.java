package fr.realcraft.host.inventories;

import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatedMenu extends SothisMenu {

    protected int page = 0;

    protected int maxItemsPerPage = 28;

    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    //Set the border and menu buttons for the menu
    public void addMenuBorder(){
        ItemStack glass = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName("").toItemStack();

        inventory.setItem(48, makeItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Left"));

        inventory.setItem(49, makeItem(Material.BARRIER, ChatColor.DARK_RED + "Close"));

        inventory.setItem(50, makeItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Right"));

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }

        inventory.setItem(17, glass);
        inventory.setItem(18, glass);
        inventory.setItem(26, glass);
        inventory.setItem(27, glass);
        inventory.setItem(35, glass);
        inventory.setItem(36, glass);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

}
