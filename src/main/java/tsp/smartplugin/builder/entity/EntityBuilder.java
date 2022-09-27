package tsp.smartplugin.builder.entity;

import com.google.common.annotations.Beta;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import tsp.smartplugin.utils.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class for easy entity creation
 *
 * @author TheSilentPro
 */
@Beta
public class EntityBuilder {

    private Class<? extends Entity> clazz;
    private final List<Entity> passengers = new ArrayList<>();
    private final List<String> tags = new ArrayList<>();

    private boolean randomized = false;
    @Nullable
    private Consumer<Entity> function = null;

    private boolean persistent;
    private boolean customNameVisible;
    @Nullable
    private String customName;

    public EntityBuilder() {
        this.clazz = Zombie.class;
    }

    public EntityBuilder(Class<? extends Entity> entityClass) {
        this.clazz = entityClass;
    }

    public EntityBuilder(EntityType type) {
        this.clazz = type.getEntityClass();
    }

    public EntityBuilder entityClass(Class<? extends Entity> clazz) {
        this.clazz = clazz;
        return this;
    }

    public EntityBuilder randomize(boolean randomizedData) {
        this.randomized = randomizedData;
        return this;
    }

    public EntityBuilder passengers(Entity... entities) {
        this.passengers.addAll(Arrays.asList(entities));
        return this;
    }

    public EntityBuilder scoreboardTags(String... tags) {
        Collections.addAll(this.tags, tags);
        return this;
    }

    public EntityBuilder customName(String customName) {
        this.customName = customName;
        return this;
    }

    public EntityBuilder customNameVisible(boolean customNameVisible) {
        this.customNameVisible = customNameVisible;
        return this;
    }

    public EntityBuilder persistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    /**
     * Function ran before the entity is spawned
     *
     * @param function Function to run before entity spawns. This runs after everything from the builder has been set.
     * @return Builder
     */
    public EntityBuilder preSpawn(Consumer<Entity> function) {
        this.function = function;
        return this;
    }

    /**
     * Spawn the entity
     *
     * @param location The location at which the entity will be spawned.
     * @return The entity
     */
    public Entity spawn(@Nonnull Location location) {
        Validate.notNull(location, "Location must not be null");
        Validate.notNull(location.getWorld(), "Location world must not be null");

        return location.getWorld().spawn(location, clazz, randomized, entity -> {
            for (Entity passenger : passengers) {
                entity.addPassenger(passenger);
            }
            for (String tag : tags) {
                entity.addScoreboardTag(tag);
            }

            entity.setPersistent(persistent);

            if (customName != null) {
                entity.setCustomName(customName);
            }
            entity.setCustomNameVisible(customNameVisible);

            if (function != null) {
                function.accept(entity);
            }
        });
    }

}
