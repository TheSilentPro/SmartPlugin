package tsp.smartplugin.builder;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for building an {@link Entity}
 *
 * @author TheSilentPro
 */
public class EntityBuilder {

    private final Class<? extends Entity> entityClass;
    private List<Entity> passengers = new ArrayList<>();
    private List<String> scoreboardTags = new ArrayList<>();
    private Map<String, MetadataValue> metadata = new HashMap<>();
    private EntityDamageEvent lastDamageCause;
    private Vector velocity;
    private CreatureSpawnEvent.SpawnReason spawnReason;

    private String name;
    private boolean nameVisibility, glowing, gravity, invulnerable, op, persistent, silent;
    private float fallDistance, rotationYaw, rotationPitch;
    private int fireTicks, portalCD, livedTicks;

    public EntityBuilder(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityBuilder(EntityType type) {
        entityClass = type.getEntityClass();
    }

    public EntityBuilder(Entity entity) {
        this.entityClass = entity.getClass();
        parseEntity(entity);
    }

    public EntityBuilder passengers(Entity... passengers) {
        this.passengers = Arrays.asList(passengers);
        return this;
    }

    public EntityBuilder addPassengers(Entity... passengers) {
        this.passengers.addAll(Arrays.asList(passengers));
        return this;
    }

    public EntityBuilder scoreboardTags(String... tags) {
        this.scoreboardTags = Arrays.asList(tags);
        return this;
    }

    public EntityBuilder addScoreboardTags(String... tags) {
        this.scoreboardTags.addAll(Arrays.asList(tags));
        return this;
    }

    public EntityBuilder metadata(Map<String, MetadataValue> metadata) {
        this.metadata = metadata;
        return this;
    }

    public EntityBuilder metadata(String s, MetadataValue metadata) {
        this.metadata.put(s, metadata);
        return this;
    }

    public EntityBuilder lastDamageCause(EntityDamageEvent lastDamageCause) {
        this.lastDamageCause = lastDamageCause;
        return this;
    }

    // Strings
    public EntityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EntityBuilder nameVisibility(boolean nameVisibility) {
        this.nameVisibility = nameVisibility;
        return this;
    }

    // Doubles
    public EntityBuilder velocity(Vector velocity) {
        this.velocity = velocity;
        return this;
    }

    public EntityBuilder velocity(double x, double y, double z) {
        this.velocity = new Vector(x, y, z);
        return this;
    }

    // Booleans
    public EntityBuilder glowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    public EntityBuilder gravity(boolean gravity) {
        this.gravity = gravity;
        return this;
    }

    public EntityBuilder invulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
        return this;
    }

    public EntityBuilder op(boolean op) {
        this.op = op;
        return this;
    }

    public EntityBuilder persistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    public EntityBuilder silent(boolean silent) {
        this.silent = silent;
        return this;
    }

    // Floats
    public EntityBuilder fallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
        return this;
    }

    public EntityBuilder rotationYaw(float rotationYaw) {
        this.rotationYaw = rotationYaw;
        return this;
    }

    public EntityBuilder rotationPitch(float rotationPitch) {
        this.rotationPitch = rotationPitch;
        return this;
    }

    public EntityBuilder rotation(float rotationYaw, float rotationPitch) {
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        return this;
    }

    // Integers
    public EntityBuilder fireTicks(int fireTicks) {
        this.fireTicks = fireTicks;
        return this;
    }

    public EntityBuilder portalCooldown(int portalCooldown) {
        this.portalCD = portalCooldown;
        return this;
    }

    public EntityBuilder livedTicks(int livedTicks) {
        this.livedTicks = livedTicks;
        return this;
    }

    public EntityBuilder spawnReason(CreatureSpawnEvent.SpawnReason spawnReason) {
        this.spawnReason = spawnReason;
        return this;
    }

    public Entity spawn(Location location, @Nullable Consumer<Entity> function) {
        return location.getWorld().spawn(location, entityClass, spawnReason, entity -> {
            passengers.forEach(entity::addPassenger);
            scoreboardTags.forEach(entity::addScoreboardTag);
            metadata.forEach(entity::setMetadata);

            entity.setCustomName(name);
            entity.setCustomNameVisible(nameVisibility);
            entity.setFallDistance(fallDistance);
            entity.setFireTicks(fireTicks);
            entity.setGlowing(glowing);
            entity.setGravity(gravity);
            entity.setInvulnerable(invulnerable);
            entity.setLastDamageCause(lastDamageCause);
            entity.setOp(op);
            entity.setPersistent(persistent);
            entity.setPortalCooldown(portalCD);
            entity.setRotation(rotationYaw, rotationPitch);
            entity.setSilent(silent);
            entity.setTicksLived(livedTicks);
            entity.setVelocity(velocity);

            if (function != null) function.accept(entity);
        });
    }

    public Entity spawn(Location location) {
        return spawn(location, null);
    }

    public void parseEntity(Entity entity) {
        passengers.addAll(entity.getPassengers());
        scoreboardTags.addAll(entity.getScoreboardTags());
        // Metadata does not get parsed

        name = entity.getCustomName();
        nameVisibility = entity.isCustomNameVisible();
        fallDistance = entity.getFallDistance();
        fireTicks = entity.getFireTicks();
        glowing = entity.isGlowing();
        gravity = entity.hasGravity();
        invulnerable = entity.isInvulnerable();
        lastDamageCause = entity.getLastDamageCause();
        op = entity.isOp();
        persistent = entity.isPersistent();
        portalCD = entity.getPortalCooldown();
        rotationYaw = entity.getLocation().getYaw();
        rotationPitch = entity.getLocation().getPitch();
        silent = entity.isSilent();
        livedTicks = entity.getTicksLived();
        velocity = entity.getVelocity();
    }

}
