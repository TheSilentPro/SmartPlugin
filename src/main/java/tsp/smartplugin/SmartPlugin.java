package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.inventory.shared.PaneListener;
import tsp.smartplugin.server.Log;

public class SmartPlugin {

    private static JavaPlugin instance;

    public static void init(JavaPlugin plugin) {
        instance = plugin;

        Log.setName(plugin.getName());
        new PaneListener(plugin);
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

}
