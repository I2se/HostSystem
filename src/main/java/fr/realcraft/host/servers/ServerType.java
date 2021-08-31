package fr.realcraft.host.servers;

public enum ServerType {

    DEFAULT("Default", 9),
    MINIGAMES("Mini-jeux", 10),
    SURVIVAL("Survie", 11),
    ADVENTURE("Map Aventure", 12),
    MODDED("Moddée", 13);

    String name;
    int filter;

    ServerType(String name, int filter) {
        this.name = name;
        this.filter = filter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }
}
