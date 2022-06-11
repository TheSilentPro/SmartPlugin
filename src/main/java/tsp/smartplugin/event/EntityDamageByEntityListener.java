package tsp.smartplugin.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.SmartPlugin;

public class EntityDamageByEntityListener implements Listener {

    public EntityDamageByEntityListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public EntityDamageByEntityListener() {
        this(SmartPlugin.getInstance());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
            LivingEntityDamageByLivingEntityEvent effectiveEvent = LivingEntityDamageByLivingEntityEvent.of(event);
            Bukkit.getPluginManager().callEvent(effectiveEvent);
            event.setCancelled(effectiveEvent.isCancelled());
        }
    }

}
