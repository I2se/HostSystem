package fr.realcraft.host.menus;

import com.mattmalec.pterodactyl4j.UtilizationState;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import fr.realcraft.commons.Server;
import fr.realcraft.host.Host;
import fr.realcraft.host.databases.Request;
import fr.realcraft.host.inventories.ItemBuilder;
import fr.realcraft.host.inventories.SothisMenu;
import fr.realcraft.host.inventories.TransferData;
import fr.realcraft.host.servers.ServerProvider;
import fr.realcraft.host.servers.ServerType;
import fr.realcraft.host.utils.Bungee;
import fr.realcraft.host.utils.TempServer;
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
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainMenu extends SothisMenu {

    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    PteroClient client = Host.getInstance().getClient();
    Player player = playerMenuUtility.getOwner();
    List<Server> listservers = Request.getAllServersOfPlayer(player);
    int nbrservofplayer = Utils.getSizeNumberServer(listservers);
    List<Server> serverspubliq = Request.getAllServersPubliq();
    int nbrservpubliq = Utils.getSizeNumberServer(serverspubliq);

    @Override
    public String getMenuName() {
        return "§6§lMenu Principal";
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
    public void handleMenu(InventoryClickEvent event) throws MenuManagerNotSetupException, MenuManagerException {
        if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Créer votre serveur")) {
            HashMap<Player, TempServer> serverInCreation = TempServer.getServeurInCreation();
            if(serverInCreation.containsKey(player)) {
                TempServer tempServer = serverInCreation.get(player);
                ServerType serverType = tempServer.getType();
                String version = tempServer.getVersion();
                if(serverType.equals(ServerType.DEFAULT)) {
                    MenuManager.openMenu(CreationServerMenu.class, player);
                } else {
                    if(serverType.equals(ServerType.SURVIVAL)) {
                        if(version.equalsIgnoreCase("Default")) {
                            MenuManager.openMenu(VersionSurvieMenu.class, player);
                        } else {
                            MenuManager.openMenu(FinalCreationMenu.class, player);
                        }
                    }
                }
            } else {
                ArrayList<String> motdDefault = new ArrayList<>();
                List<Player> whitelistDefault = new ArrayList<>();
                TempServer tempServer = new TempServer("Default", "Serveur ", motdDefault, false, ServerType.DEFAULT, 20, whitelistDefault, 4, Material.SKELETON_SKULL.toString());
                TempServer.getServeurInCreation().put(player, tempServer);
                MenuManager.openMenu(CreationServerMenu.class, player);
            }
            ///////////////////////////////

            //////////////////////////////
        } else if(event.getSlot() == 22 || event.getSlot() == 31 || event.getSlot() == 40 || event.getSlot() == 13) {
            if(nbrservofplayer > 0 && nbrservofplayer <= 4) {
                if(event.getCurrentItem().getType() != Material.LIGHT_BLUE_STAINED_GLASS_PANE) {
                    String serverId = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Host.getInstance(), "getServer"), PersistentDataType.STRING);
                    ServerProvider serverProvider = new ServerProvider(serverId);
                    Server server = serverProvider.getServer();
                    if(!client.retrieveServerByIdentifier(serverId).execute().isInstalling()) {
                        if(event.isLeftClick()) {
                            if(Utils.getState(server).equals(UtilizationState.RUNNING)) {
                                Bungee.connect(player, server);
                            } else {
                                player.sendMessage(ChatColor.RED + "Le serveur n'est pas online");
                            }
                        } else if(event.isRightClick()){
                            playerMenuUtility.setData(TransferData.TRANSITMENU, server);
                            MenuManager.openMenu(SettingsMainMenu.class, player);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Le serveur est en cours d'installation");
                    }
                }
            } else if(nbrservofplayer > 4) {
                if(event.getCurrentItem().getType().equals(Material.CHEST)) {
                    MenuManager.openMenu(ServerOfAdminMenu.class, player);
                }
            }
            ///////////////////////////////

            //////////////////////////////
        } else if(event.getSlot() == 4) {
            if(player.isOp()) {
                if(event.isShiftClick()) {
                    if(event.isLeftClick()) {
                        MenuManager.openMenu(ServerAdminMenu.class, player);
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        ItemBuilder info = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getPlayer()).setName(player.getName());
        ItemBuilder servfavo = new ItemBuilder(Material.PLAYER_HEAD).setName("Serveur de vos amis").setLore("");
        ItemBuilder publicmenu = new ItemBuilder(Material.PLAYER_HEAD).setName("Voir tout les serveurs public").setLore("");
        ItemBuilder favomenu = new ItemBuilder(Material.PLAYER_HEAD).setName("Tout les serveurs ou vous etes whitelist").setLore("");
        ItemBuilder close = new ItemBuilder(Material.BARRIER).setName("§6Close");
        ItemBuilder shop = new ItemBuilder(Material.EMERALD).setName("§6Boutique");
        ItemBuilder create = new ItemBuilder(ItemBuilder.getHead("addserver")).setName(ChatColor.GOLD + "Créer votre serveur");

        inventory.setItem(4, info.toItemStack());
        inventory.setItem(37, publicmenu.toItemStack());
        inventory.setItem(15, servfavo.toItemStack());
        inventory.setItem(16, servfavo.toItemStack());
        inventory.setItem(24, servfavo.toItemStack());
        inventory.setItem(25, servfavo.toItemStack());
        inventory.setItem(33, servfavo.toItemStack());
        inventory.setItem(34, servfavo.toItemStack());
        inventory.setItem(43, favomenu.toItemStack());
        inventory.setItem(48, create.toItemStack());
        inventory.setItem(49, close.toItemStack());
        inventory.setItem(50, shop.toItemStack());

        if(nbrservpubliq <= 6) {
            for(int i = 1; i <= nbrservpubliq; i++) {
                int slot = Utils.getSlotServerPubliq(i);
                Server server = serverspubliq.get(i-1);
                inventory.setItem(slot, Utils.affichageServerPlayer(server));
            }
        } else {
            List<Integer> selectNumber = new ArrayList<>();
            while(selectNumber.size() < 6) {
                Random random = new Random();
                int randomInt = random.nextInt(nbrservpubliq);
                if(!selectNumber.contains(randomInt)) {
                    selectNumber.add(randomInt);
                }
            }
            for(int i = 0; i == 6; i++) {
                int slot = Utils.getSlotServerPubliq(i);
                Server server = serverspubliq.get(selectNumber.get(i));
                inventory.setItem(slot, Utils.affichageServerPlayer(server));
            }
        }

        if(nbrservofplayer > 0 && nbrservofplayer <= 4) {
            for(int i = 1; i <= nbrservofplayer; i++) {
                int slot = Utils.getSlotServerPerso(i);
                Server server = listservers.get(i-1);
                inventory.setItem(slot, Utils.affichageServerPlayer(server));
            }
        } else if(nbrservofplayer > 4) {
            inventory.setItem(22, new ItemBuilder(Material.CHEST).setName("" + ChatColor.AQUA + ChatColor.BOLD + "Vos serveurs").toItemStack());
        }

        setFillerGlass();
    }
}
