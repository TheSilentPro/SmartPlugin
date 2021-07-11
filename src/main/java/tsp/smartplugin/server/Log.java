package tsp.smartplugin.server;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import tsp.smartplugin.player.MessageUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Logging Handler
 *
 * @author TheSilentPro
 */
public class Log {

    private static String name = "&bSmartPlugin";
    private static boolean debug = false;

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

    public static void log(LogLevel level, String message) {
        if (level == LogLevel.DEBUG && !debug) {
            return;
        }
        Bukkit.getConsoleSender().sendMessage(MessageUtils.colorize("&7[&9&l" + name + "&7] " + level.getColor() + "[" + level.name() + "]: " + message));
    }

    public static void log(Throwable ex) {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.colorize("&7[" + name + "&7] " + "&4&l[EXCEPTION]: " + ex.getMessage()));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.colorize("&4&l[StackTrace]: " + getStackTrace(ex)));
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

        private ChatColor getColor() {
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