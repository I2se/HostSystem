package fr.realcraft.host.inventories;

import org.bukkit.inventory.ItemStack;

public enum Heads {

    ICONMODDED("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJmNzUyN2YwZTkxMWFkZGU1MzBiNzBjMmRlYmU4ZjMxZTQ3MzUwMDczZDNkMDdmNzEyYjMyMTQyMzU3NDhlZSJ9fX0=","icmodded"),
    ADDSERVER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjlmZjk4ODQyY2I2NjQwNWQyMGE4ZTZiMmVmZmFjNDYwMTBiOGY1NjAyZWE3MzI2ZDRkMTg1YjliNWRjZTA2In19fQ==", "addserver"),
    ADDCUSTOMVALUEHEAD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQwZDRlMzE0NmMwNjE1N2M0ODM3MTA2MjEyMTU3Y2Q2NjAwYjZmZjkzNGFmOTMzYjFkY2FiODhhN2MxZWZhMiJ9fX0=", "addheadvalue");

    private ItemStack item;
    private String idTag;

    private Heads(String texture, String id) {
        item = ItemBuilder.createSkull(texture, id);
        idTag = id;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public String getName() {
        return idTag;
    }

}
