package tsp.smartplugin.player;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class MessageUtils {

    private static final Map<String, String> MESSAGES = new HashMap<>();
    private static String section = "messages";

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendMessage(CommandSender sender, String key, UnaryOperator<String> function) {
        sender.sendMessage(colorize(function.apply(getMessage(key))));
    }

    public static void sendMessage(CommandSender sender, String key) {
        sender.sendMessage(colorize(MESSAGES.get(key)));
    }

    public static String getMessage(String key) {
        return MESSAGES.get(key);
    }

    public static void setSection(String configSection) {
        section = configSection;
    }

    public static String getSection() {
        return section;
    }

    public static void clear() {
        MESSAGES.clear();
    }

    public static void load(FileConfiguration messagesFile) {
        for (String key : messagesFile.getConfigurationSection(section).getKeys(false)) {
            MESSAGES.put(key, messagesFile.getString(section + key));
        }
    }

    public static void reload(FileConfiguration messagesFile) {
        clear();
        load(messagesFile);
    }

}
