package me.gallowsdove.foxymachines.utils;

import me.gallowsdove.foxymachines.FoxyMachines;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class SimpleLocation {

    private int x;
    private int y;
    private int z;
    private final String worldUUID;
    private final String prefix;

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getZ() { return z; }
    public void setZ(int z) { this.z = z; }
    public String getWorldUUID() { return worldUUID; }
    public String getPrefix() { return prefix; }

    public SimpleLocation(int x, int y, int z, String worldUUID, String prefix) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldUUID = worldUUID;
        this.prefix = prefix;
    }

    @Nonnull
    public Block toBlock() {
        return Bukkit.getServer().getWorld(UUID.fromString(this.worldUUID)).getBlockAt(this.x, this.y, this.z);
    }

    public SimpleLocation(@Nonnull Block b, @Nonnull String prefix) {
        this(b.getLocation(), prefix);
    }

    public SimpleLocation(@Nonnull Location loc, @Nonnull String prefix) {
        this.worldUUID = loc.getWorld().getUID().toString();
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
        this.prefix = prefix;
    }

    public void storePersistently(@Nonnull PersistentDataContainer container) {
        container.set(getXKey(this.prefix), PersistentDataType.INTEGER, this.x);
        container.set(getYKey(this.prefix), PersistentDataType.INTEGER, this.y);
        container.set(getZKey(this.prefix), PersistentDataType.INTEGER, this.z);
        container.set(getWorldKey(this.prefix), PersistentDataType.STRING, this.worldUUID);
    }

    @Nullable
    public static SimpleLocation fromPersistentStorage(@Nonnull PersistentDataContainer container, @Nonnull String prefix) {
        if (container.has(getWorldKey(prefix), PersistentDataType.STRING)) {
            return new SimpleLocation(container.get(getXKey(prefix), PersistentDataType.INTEGER), container.get(getYKey(prefix), PersistentDataType.INTEGER),
                    container.get(getZKey(prefix), PersistentDataType.INTEGER), container.get(getWorldKey(prefix), PersistentDataType.STRING), prefix);
        } else {
            return null;
        }
    }

    private static NamespacedKey getWorldKey(@Nonnull String prefix) {
        return new NamespacedKey(FoxyMachines.getInstance(), prefix + "_world");
    }

    private static NamespacedKey getXKey(@Nonnull String prefix) {
        return new NamespacedKey(FoxyMachines.getInstance(), prefix + "_x");
    }

    private static NamespacedKey getYKey(@Nonnull String prefix) {
        return new NamespacedKey(FoxyMachines.getInstance(), prefix + "_y");
    }

    private static NamespacedKey getZKey(@Nonnull String prefix) {
        return new NamespacedKey(FoxyMachines.getInstance(), prefix + "_z");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleLocation that)) return false;
        return x == that.x && y == that.y && z == that.z && worldUUID.equals(that.worldUUID) && prefix.equals(that.prefix);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y, z, worldUUID, prefix);
    }

    @Override
    public String toString() {
        return "X: " + this.x + " Y: " + this.y + " Z: " + this.z;
    }
}
