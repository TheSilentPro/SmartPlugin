package tsp.smartplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.UUID;

public class LocationUtils {

    public static void serializeLocation(Location location, FileConfiguration data, @Nullable String key) {
        if (key != null && !key.isEmpty()) {
            key = key + ".";
        }

        String uid = location.getWorld().getUID().toString();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        data.set(key + "world", uid);
        data.set(key + "x", x);
        data.set(key + "y", y);
        data.set(key + "z", z);
        data.set(key + "yaw", yaw);
        data.set(key + "pitch", pitch);
    }

    public static Location deserializeLocation(FileConfiguration data, @Nullable String key) {
        if (key != null && !key.isEmpty()) {
            key = key + ".";
        }

        String uid = data.getString(key + "world");
        double x = data.getDouble(key + "x");
        double y = data.getDouble(key + "y");
        double z = data.getDouble(key + "z");
        float yaw = Float.parseFloat(data.getString(key + "yaw"));
        float pitch = Float.parseFloat(data.getString(key + "pitch")); // For some reason FileConfiguration doesn't have getFloat :/

        return new Location(Bukkit.getWorld(UUID.fromString(uid)), x, y, z, yaw, pitch);
    }

    /**
     * Serializes a location to a single string
     *
     * @param location The location to serialize
     * @return Serialized Location. Format: world;x;y;z;yaw;pitch
     */
    public static String serializeLocation(Location location) {
        String uid = location.getWorld().getUID().toString();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        return uid +
                ";" +
                x +
                ";" +
                y +
                ";" +
                z +
                ";" +
                yaw +
                ";" +
                pitch;
    }

    /**
     * Deserializes a location
     * that has been serailized with {@link #serializeLocation(Location)}
     *
     * @param serialized Location as a serialized string
     * @return Location
     */
    public static Location deserializeLocation(String serialized) {
        String[] args = serialized.split(";");
        String uid = args[0];
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);
        float yaw = Float.parseFloat(args[4]);
        float pitch = Float.parseFloat(args[5]);

        return new Location(Bukkit.getWorld(UUID.fromString(uid)), x, y, z, yaw, pitch);
    }

}
