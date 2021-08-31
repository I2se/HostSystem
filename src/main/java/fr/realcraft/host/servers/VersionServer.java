package fr.realcraft.host.servers;

import java.util.Arrays;

public enum VersionServer {

    DEFAULT("Default", "Default", 0,9,0),
    V18("Version 1.8.8", "1.8.8", 8, 10,16),
    V19("Version 1.9.4", "1.9.4", 8, 11,17),
    V110("Version 1.10.2", "1.10.2", 8, 12,18),
    V111("Version 1.11.2", "1.11.2", 8, 13,19),
    V112("Version 1.12.2", "1.12.2", 8, 14,20),
    V113("Version 1.13.2", "1.13.2", 16, 15,22),
    V114("Version 1.14.4", "1.14.4", 16, 16,23),
    V115("Version 1.15.2", "1.15.2", 16, 17,24),
    V116("Version 1.16.5", "1.16.5", 16, 18,25),
    V117("Version 1.17.1", "1.17.1", 16, 19,26);

    String name;
    String version;
    int docker;
    int filter;
    int eggnumber;

    VersionServer(String name, String version, int docker, int filter, int eggnumber) {
        this.name = name;
        this.version = version;
        this.docker = docker;
        this.filter = filter;
        this.eggnumber = eggnumber;
    }

    public static VersionServer getByName(String name) {
        return Arrays.stream(values()).filter(r -> r.getName().equalsIgnoreCase(name)).findAny().orElse(VersionServer.DEFAULT);
    }

    public static VersionServer getByVersion(String version) {
        return Arrays.stream(values()).filter(r -> r.getVersion().equalsIgnoreCase(version)).findAny().orElse(VersionServer.DEFAULT);
    }

    public static VersionServer getByEggNumber(int eggnumber) {
        return Arrays.stream(values()).filter(r -> r.getEggnumber() == eggnumber).findAny().orElse(VersionServer.DEFAULT);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getDocker() {
        return docker;
    }

    public void setDocker(int docker) {
        this.docker = docker;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public int getEggnumber() {
        return eggnumber;
    }

    public void setEggnumber(int eggnumber) {
        this.eggnumber = eggnumber;
    }
}
