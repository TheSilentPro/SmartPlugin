package tsp.smartplugin.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {

    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(Utils.colorize(message));
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', ChatColor.GRAY + string);
    }

}
