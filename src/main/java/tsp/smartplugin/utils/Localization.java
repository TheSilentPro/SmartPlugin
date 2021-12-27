package tsp.smartplugin.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Class for a messages file
 *
 * @author TheSilentPro
 */
public class Localization {

    private final Map<String, String> MESSAGES = new HashMap<>(); // key, message
    private String section = "messages";
    private String argumentPlaceholder = "{%n}"; // %n will be replaced with the argument number

    public void setSection(String configSection) {
        section = configSection;
    }

    public void setArgumentPlaceholder(String argumentPlaceholder) {
        this.argumentPlaceholder = argumentPlaceholder;
    }

    public String getMessage(String key) {
        return MESSAGES.get(key);
    }

    public String getSection() {
        return section;
    }

    public String getArgumentPlaceholder() {
        return argumentPlaceholder;
    }

    public Map<String, String> getMessagesCache() {
        return MESSAGES;
    }

    public void clear() {
        MESSAGES.clear();
    }

    /**
     * Loads the messages in memory cache
     *
     * @param messagesFile The file containing the messages
     * @param deep If all sub-paths should be included
     */
    public void load(FileConfiguration messagesFile, boolean deep) {
        section = section.isEmpty() ? section : section + ".";
        for (String key : messagesFile.getConfigurationSection(section).getKeys(deep)) {
            MESSAGES.put(key, messagesFile.getString(section + key));
        }
    }

    public void load(FileConfiguration messageFile) {
        load(messageFile, true);
    }

    public void reload(FileConfiguration messagesFile) {
        clear();
        load(messagesFile);
    }

    /**
     * Send a message
     *
     * @param receiver The receiver of the message
     * @param key The message key
     * @param function Optional function to apply to the message
     * @param args Optional arguments to replace the {@link Localization#argumentPlaceholder} in the message with
     */
    public void sendMessage(CommandSender receiver, String key, @Nullable UnaryOperator<String> function, @Nullable String... args) {
        String message = getMessage(key);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace(argumentPlaceholder.replace("%n", String.valueOf(i)), args[i]);
            }
        }

        receiver.sendMessage(StringUtils.colorize(function != null ? function.apply(message) : message));
    }

    public void sendMessage(CommandSender receiver, String key, @Nullable UnaryOperator<String> function) {
        sendMessage(receiver, key, function, (String[]) null);
    }

    public void sendMessage(CommandSender receiver, String key, @Nullable String... args) {
        sendMessage(receiver, key, null, args);
    }

    public void sendMessage(CommandSender receiver, String key) {
        sendMessage(receiver, key, null, (String[]) null);
    }

}
