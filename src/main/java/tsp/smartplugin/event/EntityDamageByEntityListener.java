package tsp.smartplugin.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
            LivingEntityDamageByLivingEntityEvent effectiveEvent = LivingEntityDamageByLivingEntityEvent.of(event);
            Bukkit.getPluginManager().callEvent(effectiveEvent);
            event.setCancelled(effectiveEvent.isCancelled());
        }
    }

}
