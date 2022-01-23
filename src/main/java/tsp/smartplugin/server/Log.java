package tsp.smartplugin.server;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import tsp.smartplugin.SmartPlugin;
import tsp.smartplugin.utils.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Logging Handler
 *
 * @author TheSilentPro
 */
public final class Log {

    private static String name = SmartPlugin.getInstance().getSettings().getPluginName();
    private static boolean debug = false;

    private Log() {}

    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public static void error(Throwable ex) {
        log(ex);
    }

    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    /**
     * Log a message to the console
     *
     * @param level The logging level of the message
     * @param message The message
     */
    public static void log(LogLevel level, String message) {
        if (level == LogLevel.DEBUG && !debug) {
            return;
        }
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorize("&7[&9&l" + name + "&7] " + level.getColor() + "[" + level.name() + "]: " + message));
    }

    /**
     * Log a {@link Throwable} to the console
     *
     * @param ex The throwable to log
     */
    public static void log(Throwable ex) {
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorize("&7[" + name + "&7] " + "&4&l[EXCEPTION]: " + ex.getMessage()));
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorize("&4&l[StackTrace]: " + getStackTrace(ex)));
    }

    public static void setName(String logName) {
        name = logName;
    }

    public static String getName() {
        return name;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Log.debug = debug;
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public enum LogLevel {

        INFO,
        WARNING,
        ERROR,
        DEBUG;

        /**
         * Get the text color of the level
         *
         * @return The color
         */
        public ChatColor getColor() {
            switch (this) {
                case INFO:
                    return ChatColor.GREEN;
                case WARNING:
                    return ChatColor.YELLOW;
                case ERROR:
                    return ChatColor.DARK_RED;
                case DEBUG:
                    return ChatColor.DARK_AQUA;
                default:
                    return ChatColor.WHITE;
            }
        }

    }

}