package fr.realcraft.host.utils;

import com.mattmalec.pterodactyl4j.ServerStatus;
import com.mattmalec.pterodactyl4j.UtilizationState;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import fr.realcraft.commons.Server;
import fr.realcraft.host.Host;
import fr.realcraft.host.inventories.ItemBuilder;
import fr.realcraft.host.servers.ServerType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static PteroClient client = Host.getInstance().getClient();
    private static PteroApplication app = Host.getInstance().getApp();

    public static String statusWrite(UtilizationState utilizationState) {
        String write = "";
        switch (utilizationState) {
            case OFFLINE:
                write = ChatColor.RED + "• " + ChatColor.WHITE + "Offline";
                return write;

            case RUNNING:
                write = ChatColor.GREEN + "• " + ChatColor.WHITE + "Online";
                return write;

            case STARTING:
                write = ChatColor.GOLD + "• " + ChatColor.WHITE + "Starting";
                return write;

            case STOPPING:
                write = ChatColor.DARK_RED + "• " + ChatColor.WHITE + "Stopping";
                return write;

            default:
                return "Action en cours";
        }
    }

    public static String statusWrite(ServerStatus serverStatus) {
        String write = "";
        switch (serverStatus) {
            case INSTALLING:
                write = ChatColor.YELLOW + "• " + ChatColor.WHITE + "Installation";
                return write;

            default:
                return "Error";
        }
    }

    public static ItemStack affichageServerPlayer(Server server) {
        String serverId = server.getServerId();
        ClientServer clientServer = client.retrieveServerByIdentifier(serverId).execute();
        String id = clientServer.getInternalId();
        ApplicationServer applicationServer = app.retrieveServerById(id).execute();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");

        if(clientServer.isInstalling()) {
            lore.add(statusWrite(applicationServer.getStatus()));
        } else {
            lore.add(statusWrite(clientServer.retrieveUtilization().execute().getState()));
        }

        lore.addAll(server.getMotd());

        ItemStack itemServer = new ItemStack(Utils.getCustomItemBuilder(server.getMaterial()));
        ItemMeta itemMeta = itemServer.getItemMeta();
        itemMeta.setDisplayName(server.getName());
        itemMeta.setLore(lore);
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(Host.getInstance(), "getServer"), PersistentDataType.STRING, server.getServerId());
        itemServer.setItemMeta(itemMeta);

        return itemServer;
    }

    public static void startServer(Server server) {
        client.retrieveServerByIdentifier(server.getServerId()).flatMap(ClientServer::start).executeAsync();
    }

    public static void stopServer(Server server) {
        client.retrieveServerByIdentifier(server.getServerId()).flatMap(ClientServer::stop).executeAsync();
    }

    public static String affichageServerType(ServerType type) {
        switch (type) {
            case SURVIVAL:
                return "Survival";

            case ADVENTURE:
                return "Adventure";

            case MINIGAMES:
                return "Mini-Games";

            case MODDED:
                return "Modded";

            default:
                return "ErrorNoTypeSpecified";
        }
    }

    public static ItemBuilder showStatus(Server server) {
        ClientServer clientServer = client.retrieveServerByIdentifier(server.getServerId()).execute();
        UtilizationState status = clientServer.retrieveUtilization().execute().getState();

        switch (status) {
            case RUNNING:
                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.DARK_GRAY + "Click to shutdown the server");
                return new ItemBuilder(Material.GREEN_CONCRETE, 1).setName(ChatColor.GREEN + "Online").setLore(lore);

            case OFFLINE:
                ArrayList<String> lore2 = new ArrayList<>();
                lore2.add("");
                lore2.add(ChatColor.DARK_GRAY + "Click to run the server");
                return new ItemBuilder(Material.RED_CONCRETE, 1).setName(ChatColor.RED + "Offline").setLore(lore2);

            case STARTING:
                ArrayList<String> lore3 = new ArrayList<>();
                lore3.add("");
                lore3.add(ChatColor.GREEN + "The server is still starting");
                lore3.add(ChatColor.DARK_GRAY + "Click to refresh status");
                return new ItemBuilder(Material.ORANGE_CONCRETE, 1).setLore(ChatColor.YELLOW + "Starting").setLore(lore3);

            case STOPPING:
                ArrayList<String> lore4 = new ArrayList<>();
                lore4.add("");
                lore4.add(ChatColor.RED + "The server is stopping");
                lore4.add(ChatColor.DARK_GRAY + "Click to refresh status");
                return new ItemBuilder(Material.LIGHT_GRAY_CONCRETE, 1).setLore(ChatColor.DARK_RED + "Stopping").setLore(lore4);

            default:break;
        }
        return new ItemBuilder(Material.DEAD_BUSH, 1);
    }

    public static UtilizationState getState(Server server) {
        ClientServer clientServer = client.retrieveServerByIdentifier(server.getServerId()).execute();
        UtilizationState state = clientServer.retrieveUtilization().execute().getState();
        return state;
    }

    public static ItemStack getCustomItemBuilder(String material) {
        ItemStack itemBuilder = null;
        if(material.contains("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv")) {
            itemBuilder = new ItemStack(ItemBuilder.createSkull(material));
        } else {
            itemBuilder = new ItemStack(Material.valueOf(material));
        }
        return itemBuilder;
    }

    public static int getSizeNumberServer(List<Server> list) {
        int nbrservofplayer = 0;
        if(list != null) {
            nbrservofplayer = list.size();
        } else {
            return nbrservofplayer;
        }
        return nbrservofplayer;
    }

    public static int getSlotServerPerso(int index) {
        int slot = 0;
        switch (index) {
            case 1:
                slot = 22;
                break;
            case 2:
                slot = 31;
                break;
            case 3:
                slot = 40;
                break;
            case 4:
                slot = 13;
                break;
        }
        return slot;
    }

    public static int getSlotServerPubliq(int index) {
        int slot = 0;
        switch (index) {
            case 1:
                slot = 10;
                break;
            case 2:
                slot = 11;
                break;
            case 3:
                slot = 19;
                break;
            case 4:
                slot = 20;
                break;
            case 5:
                slot = 28;
                break;
            case 6:
                slot = 29;
                break;
            default:break;
        }
        return slot;
    }

}
