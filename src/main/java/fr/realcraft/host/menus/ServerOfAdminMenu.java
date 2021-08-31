package fr.realcraft.host.menus;

import fr.realcraft.commons.Server;
import fr.realcraft.host.Host;
import fr.realcraft.host.databases.Request;
import fr.realcraft.host.inventories.PaginatedMenu;
import fr.realcraft.host.inventories.TransferData;
import fr.realcraft.host.servers.ServerProvider;
import fr.realcraft.host.utils.Utils;
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

import java.util.List;

public class ServerOfAdminMenu extends PaginatedMenu {

    public ServerOfAdminMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    Player player = playerMenuUtility.getOwner();
    List<Server> servers = Request.getAllServersOfPlayer(player);

    @Override
    public String getMenuName() {
        return "" + ChatColor.GOLD + ChatColor.BOLD + "Vos serveurs";
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

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {

            MenuManager.openMenu(MainMenu.class, player);

        }else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")){
                if (page == 0){
                    p.sendMessage(ChatColor.GRAY + "Tu es deja sur la première page.");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")){
                if (!((index + 1) >= servers.size())){
                    page = page + 1;
                    super.open();
                }else{
                    p.sendMessage(ChatColor.GRAY + "Tu es deja sur la dernière page.");
                }
            }
        } else {
            String serverId = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Host.getInstance(), "server"), PersistentDataType.STRING);
            ServerProvider serverProvider = new ServerProvider(serverId);
            Server server = serverProvider.getServer();
            playerMenuUtility.setData(TransferData.TRANSITMENU, server);
            MenuManager.openMenu(SettingsMainMenu.class, player);
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        if(servers != null && !servers.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= servers.size()) break;
                if (servers.get(index) != null){
                    ItemStack playerItem = new ItemStack(Utils.getCustomItemBuilder(servers.get(index).getMaterial()));
                    ItemMeta playerMeta = playerItem.getItemMeta();
                    playerMeta.setDisplayName(ChatColor.RED + servers.get(index).getName());

                    playerMeta.getPersistentDataContainer().set(new NamespacedKey(Host.getInstance(), "server"), PersistentDataType.STRING, servers.get(index).getServerId());
                    playerItem.setItemMeta(playerMeta);

                    inventory.addItem(playerItem);
                }
            }
        }
    }
}
