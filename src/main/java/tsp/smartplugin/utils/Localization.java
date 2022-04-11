package tsp.smartplugin.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.util.function.UnaryOperator;

/**
 * Class for a messages file
 *
 * @author TheSilentPro
 */
public class Localization {

    private final JavaPlugin plugin;
    private File messageFile;
    private FileConfiguration data;
    private String section;
    private String argumentPlaceholder; // %n will be replaced with the argument number

    /**
     * Constructs a new {@link Localization} instance
     *
     * @param section The section in which the messages will be kept under
     * @param placeholder The placeholder used for replacing arguments. %n = argument number
     */
    public Localization(JavaPlugin plugin, File file, String section, String placeholder) {
        this.plugin = plugin;
        this.messageFile = file;
        this.data = YamlConfiguration.loadConfiguration(file);
        this.section = section;
        this.argumentPlaceholder = placeholder;
    }

    /**
     * Constructs a new {@link Localization} instance with the default parameters
     */
    public Localization(JavaPlugin plugin) {
        this(
                plugin,
                new File(plugin.getDataFolder(), "messages.yml"),
                "",
                "{%n}"
        );
    }

    /**
     * Set the message file for the messages
     *
     * @param messageFile The message file
     */
    public void setMessageFile(File messageFile) {
        this.messageFile = messageFile;
        this.data = YamlConfiguration.loadConfiguration(messageFile);
    }

    /**
     * Set the section in which the messages will start to be looked at
     *
     * @param configSection The section
     */
    public void setSection(String configSection) {
        section = configSection;
    }

    /**
     * Set the argument placeholder
     *
     * @param argumentPlaceholder The placeholder used for replacing arguments. %n = argument number
     */
    public void setArgumentPlaceholder(String argumentPlaceholder) {
        this.argumentPlaceholder = argumentPlaceholder;
    }

    /**
     * Retrieve a message
     *
     * @param key The message key
     * @return The message. Returns null if not found
     */
    @Nullable
    public String getMessage(String key) {
        return data.getString(section + key);
    }

    /**
     * Retrieve a message
     *
     * @param key The message key
     * @param def The value returned if the original is not found
     * @return The message
     */
    @Nullable
    public String getMessage(String key, String def) {
        return data.getString(section + key, def);
    }

    /**
     * Retrieve the section that has been set for messages
     *
     * @return The section being used for messages
     */
    public String getSection() {
        return section;
    }

    /**
     * Retrieve the argument placeholder
     *
     * @return The argument placeholder
     */
    public String getArgumentPlaceholder() {
        return argumentPlaceholder;
    }

    /**
     * Retrieve the messages file
     *
     * @return The messages file
     */
    public File getMessageFile() {
        return messageFile;
    }

    /**
     * Loads the messages
     */
    public void load() {
        createFromResources();
        this.data = YamlConfiguration.loadConfiguration(messageFile);
    }

    /**
     * Creates the file from the resource directory
     */
    public void createFromResources() {
        if (!messageFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
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
        if (args != null && message != null) {
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
