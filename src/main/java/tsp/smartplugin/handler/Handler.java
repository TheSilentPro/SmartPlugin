package tsp.smartplugin.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.SmartPlugin;

/**
 * @see Listener
 */
public abstract class Handler<E extends Event> implements Listener {

    public Handler(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Handler() {
        Bukkit.getPluginManager().registerEvents(this, SmartPlugin.getInstance());
    }

    public abstract void handle(E event);

}
