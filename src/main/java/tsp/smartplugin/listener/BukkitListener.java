package tsp.smartplugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.SmartPlugin;

public class BukkitListener implements Listener {

    public BukkitListener(JavaPlugin plugin) {
        if (this instanceof AutoRegister) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }
    }

    public BukkitListener() {
        this(SmartPlugin.getInstance());
    }

}
