package tsp.smartplugin;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.event.EntityDamageByEntityListener;
import tsp.smartplugin.server.Log;
import tsp.smartplugin.utils.Patterns;

public class Settings {

    private final JavaPlugin plugin;
    private String pluginName;
    private boolean compilePatterns = true;
    private boolean callLivingEntityEvent = false;

    public Settings(JavaPlugin plugin) {
        this.plugin = plugin;
        Log.setName(pluginName = plugin.getName());
    }

    public void load() {
        Log.setName(pluginName);
        if (compilePatterns) {
            Patterns.compile();
        }
        if (callLivingEntityEvent) {
            plugin.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), plugin);
        }
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return pluginName;
    }

    /**
     * Set if patterns should be compiled on startup by default.
     *
     * @param compilePatterns if the plugin should compile patterns by default
     */
    public void setCompilePatterns(boolean compilePatterns) {
        this.compilePatterns = compilePatterns;
    }

    public boolean shouldCompilePatterns() {
        return compilePatterns;
    }

    public boolean isCallLivingEntityEvent() {
        return callLivingEntityEvent;
    }

    public void setCallLivingEntityEvent(boolean callLivingEntityEvent) {
        this.callLivingEntityEvent = callLivingEntityEvent;
    }
}
