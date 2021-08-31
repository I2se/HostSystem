package fr.realcraft.host.menus.custom;

import fr.realcraft.host.Host;
import fr.realcraft.host.inventories.Heads;
import fr.realcraft.host.inventories.ItemBuilder;
import fr.realcraft.host.inventories.PaginatedMenu;
import fr.realcraft.host.menus.FinalCreationMenu;
import fr.realcraft.host.utils.TempServer;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CustomCreationMenu extends PaginatedMenu {

    public CustomCreationMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    Player player = playerMenuUtility.getOwner();
    public static HashMap<Player, String> chatAsync = new HashMap<>();

    @Override
    public String getMenuName() {
        return "" + ChatColor.GOLD + ChatColor.BOLD + "Choissisez votre deco";
    }

    @Override
    public int getSlots() {
        return 6*9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        Player p = (Player) e.getWhoClicked();
        List<Material> materials = List.of(Material.values());

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {

            MenuManager.openMenu(FinalCreationMenu.class, player);

        }else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")){
                if (page == 0){
                    p.sendMessage(ChatColor.GRAY + "Tu es deja sur la première page.");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")){
                if (!((index + 1) >= materials.size())){
                    page = page + 1;
                    super.open();
                }else{
                    p.sendMessage(ChatColor.GRAY + "Tu es deja sur la dernière page.");
                }
            }
        } else if(e.getSlot() == 4) {
            chatAsync.put(player,"headValue");
            player.sendMessage(ChatColor.GOLD + "Vous pouvez vous même selectionnez une texture custom");
            player.sendMessage(ChatColor.GOLD + "Pour cela rendez vous sur Minecraft-Heads");
            player.sendMessage(ChatColor.GOLD + "Choissisez la texture de la tête que vous souhaitez");
            player.sendMessage(ChatColor.GOLD + "Copier la ligne Value puis coller la dans le chat");
            player.closeInventory();
        } else {
            Material material = e.getCurrentItem().getType();
            TempServer.getServeurInCreation().get(player).setMaterial(material.toString());
            MenuManager.openMenu(FinalCreationMenu.class, player);
        }
    }

    @Override
    public void setMenuItems() {

        if(chatAsync.containsKey(player)) {
            chatAsync.remove(player);
        }

        addMenuBorder();

        List<Material> materials = List.of(Material.values());

        if(materials != null && !materials.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= materials.size()) break;
                if (materials.get(index) != null){
                    ItemStack playerItem = new ItemStack(materials.get(index), 1);
                    if(playerItem.getItemMeta() != null) {
                        ItemMeta playerMeta = playerItem.getItemMeta();
                        playerMeta.setDisplayName(ChatColor.AQUA + materials.get(index).name());
                        playerMeta.setLore(Collections.singletonList(" "));
                        playerMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "Click to choose this block"));

                        playerMeta.getPersistentDataContainer().set(new NamespacedKey(Host.getInstance(), "servers"), PersistentDataType.STRING, materials.get(index).toString());
                        playerItem.setItemMeta(playerMeta);

                        inventory.addItem(playerItem);
                    }
                }
            }
        }
        inventory.setItem(4, new ItemBuilder(ItemBuilder.getHead(Heads.ADDCUSTOMVALUEHEAD.getName())).setName("Selectionnez une texture custom").toItemStack());
    }

    public static HashMap<Player, String> getChatAsync() {
        return chatAsync;
    }
}
