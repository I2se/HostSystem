package fr.realcraft.host.inventories;

import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;

public abstract class SothisMenu extends Menu {

    public SothisMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public void setFillerGlass() {
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack());
            }
        }
    }
}
