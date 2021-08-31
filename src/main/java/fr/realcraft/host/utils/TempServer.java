package fr.realcraft.host.utils;

import fr.realcraft.host.servers.ServerType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TempServer implements Cloneable{

    public static HashMap<Player, TempServer> serveurInCreation = new HashMap<>();

    public static HashMap<Player, TempServer> getServeurInCreation() {
        return serveurInCreation;
    }

    private String version; //FORMAT: 1.0.0
    private String name;
    private ArrayList<String> motd;
    private boolean status;
    private ServerType type;
    private int nbrslot;
    private List<Player> whitelist = new ArrayList<>();
    private long memory;
    private String material;

    public TempServer(String version, String name, ArrayList<String> motd, boolean status, ServerType type, int nbrslot, List<Player> whitelist, long memory, String material) {
        this.version = version;
        this.name = name;
        this.motd = motd;
        this.status = status;
        this.type = type;
        this.nbrslot = nbrslot;
        this.whitelist = whitelist;
        this.memory = memory;
        this.material = material;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMotd() {
        return motd;
    }

    public void setMotd(ArrayList<String> motd) {
        this.motd = motd;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ServerType getType() {
        return type;
    }

    public void setType(ServerType type) {
        this.type = type;
    }

    public List<Player> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<Player> whitelist) {
        this.whitelist = whitelist;
    }

    public int getNbrslot() {
        return nbrslot;
    }

    public void setNbrslot(int nbrslot) {
        this.nbrslot = nbrslot;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }
}
