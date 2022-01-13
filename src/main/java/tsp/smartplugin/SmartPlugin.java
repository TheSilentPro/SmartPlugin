package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.inventory.PaneListener;
import tsp.smartplugin.server.Log;

public abstract class SmartPlugin extends JavaPlugin {

    private static SmartPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Log.setName(getName());

        new PaneListener(this);

        // Call superclass method
        onStart();
    }

    /**
     * Called after everything has been set internally
     * Plugins should use this instead of onEnable.
     */
    public abstract void onStart();

    public static SmartPlugin getInstance() {
        return instance;
    }
}
