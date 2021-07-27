package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.inventory.PaneListener;
import tsp.smartplugin.server.Log;

public class SmartPlugin {

    private static final SmartPlugin instance = new SmartPlugin();
    private JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        instance.plugin = plugin;

        Log.setName(plugin.getName());
        new PaneListener(plugin);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public static SmartPlugin getInstance() {
        return instance;
    }
}
