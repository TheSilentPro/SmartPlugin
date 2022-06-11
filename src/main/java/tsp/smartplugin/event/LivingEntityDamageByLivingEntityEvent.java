package tsp.smartplugin.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tsp.smartplugin.utils.Validate;

import javax.annotation.Nonnull;

/**
 * Same as {@link EntityDamageByEntityEvent}, but only for {@link LivingEntity}'s.
 * Requires to be registered via {@link EntityDamageByEntityListener}.
 *
 * @author TheSilentPro
 */
public class LivingEntityDamageByLivingEntityEvent extends EntityDamageByEntityEvent {

    public LivingEntityDamageByLivingEntityEvent(LivingEntity damager, LivingEntity damagee, DamageCause cause, double damage) {
        super(damager, damagee, cause, damage);
    }

    public static LivingEntityDamageByLivingEntityEvent of(EntityDamageByEntityEvent event) {
        Validate.isTrue(event.getDamager() instanceof LivingEntity, "Damager must be an instance of LivingEntity!");
        Validate.isTrue(event.getEntity() instanceof LivingEntity, "Damager must be an instance of LivingEntity!");

        return new LivingEntityDamageByLivingEntityEvent((LivingEntity) event.getDamager(), (LivingEntity) event.getEntity(), event.getCause(), event.getDamage());
    }

    @Override
    @Nonnull
    public LivingEntity getDamager() {
        return (LivingEntity) super.getDamager();
    }

    @Override
    @Nonnull
    public LivingEntity getEntity() {
        return (LivingEntity) super.getEntity();
    }

    public LivingEntity getVictim() {
        return getEntity();
    }

}
