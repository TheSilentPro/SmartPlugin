package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class SmartPlugin extends JavaPlugin {

    private static SmartPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

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
