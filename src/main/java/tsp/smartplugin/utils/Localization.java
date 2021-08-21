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
    private String argumentPlaceholder = "$arg";

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
    public void sendConfigMessage(CommandSender receiver, String key, @Nullable UnaryOperator<String> function, @Nullable String... args) {
        String message = getMessage(key);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace(argumentPlaceholder + i, args[i]);
            }
        }

        receiver.sendMessage(StringUtils.colorize(function != null ? function.apply(message) : message));
    }

    public void sendConfigMessage(CommandSender receiver, String key, @Nullable UnaryOperator<String> function) {
        sendConfigMessage(receiver, key, function, (String[]) null);
    }

    public void sendConfigMessage(CommandSender receiver, String key, @Nullable String... args) {
        sendConfigMessage(receiver, key, null, args);
    }

    public void sendConfigMessage(CommandSender receiver, String key) {
        sendConfigMessage(receiver, key, null, (String[]) null);
    }

}
