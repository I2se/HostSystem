package fr.realcraft.commons;

import fr.realcraft.host.servers.ServerType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Server implements Cloneable{
    private int id;
    private String serverId;
    private String version; //FORMAT: 1.0.0
    private int port;
    private UUID owner;
    private String name;
    private String pseudo;
    private ArrayList<String> motd;
    private boolean status;
    private ServerType type;
    private int nbrslot;
    private List<Player> whitelist = new ArrayList<>();
    private String material;


    public Server() {

    }

    public Server(int id, String serverId, String version, int port, UUID owner, String pseudo, String name, ArrayList<String> motd, boolean status, ServerType type, int nbrslot, List<Player> whitelist, String material) {
        this.id = id;
        this.serverId = serverId;
        this.version = version;
        this.port = port;
        this.owner = owner;
        this.pseudo = pseudo;
        this.name = name;
        this.motd = motd;
        this.status = status;
        this.type = type;
        this.nbrslot = nbrslot;
        this.whitelist = whitelist;
        this.material = material;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
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

    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getNbrslot() {
        return nbrslot;
    }

    public void setNbrslot(int nbrslot) {
        this.nbrslot = nbrslot;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(!(o instanceof Server)) {
            return false;
        } else {
            return ((Server) o).getId() == this.id;
        }
    }

    public Server clone() {
        try {
            return (Server) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
