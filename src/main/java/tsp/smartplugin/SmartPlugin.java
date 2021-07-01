package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.inventory.shared.PaneListener;
import tsp.smartplugin.server.Log;

public class SmartPlugin {

    private static JavaPlugin instance;
    private static boolean debug = false;

    public static void init(JavaPlugin plugin) {
        instance = plugin;

        Log.setName(plugin.getName());
        new PaneListener(plugin);
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        SmartPlugin.debug = debug;
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

}
