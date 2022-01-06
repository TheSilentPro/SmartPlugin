package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.inventory.PaneListener;
import tsp.smartplugin.server.Log;

public class SmartPlugin {

    private static SmartPlugin instance;
    private JavaPlugin plugin;

    /**
     * Initialize SmartPlugin features.
     *
     * @param plugin The {@link JavaPlugin}
     */
    public static void init(JavaPlugin plugin) {
        instance = new SmartPlugin();
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
