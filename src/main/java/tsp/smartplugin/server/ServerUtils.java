package tsp.smartplugin.server;

import org.bukkit.Bukkit;
import tsp.smartplugin.player.MessageUtils;

/**
 * Utility class for a Minecraft Server
 *
 * @author TheSilentPro
 */
public final class ServerUtils {

    private ServerUtils() {}

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(MessageUtils.colorize(message));
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
