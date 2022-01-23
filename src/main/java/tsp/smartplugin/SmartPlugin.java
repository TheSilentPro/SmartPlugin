package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.inventory.PaneListener;
import tsp.smartplugin.mojang.MojangAPI;

public abstract class SmartPlugin extends JavaPlugin {

    private static SmartPlugin instance;
    private Settings settings;
    private MojangAPI mojangAPI;

    @Override
    public void onEnable() {
        instance = this;
        settings = new Settings(this);
        mojangAPI = new MojangAPI(this);
        new PaneListener(this);

        // Call superclass method
        onStart();

        // Settings should load after changes from superclass
        settings.load();
    }

    /**
     * Called after everything has been set internally
     * Plugins should use this instead of onEnable.
     */
    public abstract void onStart();

    public MojangAPI getMojangAPI() {
        return mojangAPI;
    }

    public Settings getSettings() {
        return settings;
    }

    public static SmartPlugin getInstance() {
        return instance;
    }

}
