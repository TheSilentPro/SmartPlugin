package tsp.smartplugin.server;

import org.bukkit.Bukkit;
import tsp.smartplugin.player.PlayerUtils;

/**
 * Utility class for a Minecraft Server
 *
 * @author TheSilentPro
 */
public class ServerUtils {

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(PlayerUtils.colorize(message));
    }

    public static ServerVersion getVersion() {
        return ServerVersion.getVersion();
    }

    public static boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
