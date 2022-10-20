package tsp.smartplugin.listener;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.SmartPlugin;
import tsp.smartplugin.utils.Validate;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public abstract class SmartListener<T extends Event> implements Listener {

    private final JavaPlugin plugin;

    public SmartListener(@Nonnull JavaPlugin plugin) {
        Validate.notNull(plugin, "Plugin can not be null!");

        this.plugin = plugin;
        if (this instanceof AutoRegister) {
            register();
        }
    }

    public SmartListener() {
        this(SmartPlugin.getInstance());
    }

    @EventHandler
    public void onEvent(T event) {
        if (event instanceof Cancellable && ((Cancellable) event).isCancelled()) {
            return;
        }
        handle(event);
    }

    public abstract void handle(T event);

    @Nonnull
    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}