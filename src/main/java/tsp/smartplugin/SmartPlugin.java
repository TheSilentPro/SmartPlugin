package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.inventory.shared.PaneListener;

public class SmartPlugin {

    private static JavaPlugin instance;

    public void init(JavaPlugin plugin) {
        instance = plugin;

        new PaneListener(plugin);
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
