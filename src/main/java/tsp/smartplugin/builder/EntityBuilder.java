package tsp.smartplugin.builder;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class for easy entity creation
 *
 * @author TheSilentPro
 */
public class EntityBuilder {

    private Class<? extends Entity> clazz;
    private boolean randomized = false;
    private final List<Entity> passengers = new ArrayList<>();
    private final List<String> tags = new ArrayList<>();

    private boolean persistent;
    private boolean customNameVisible;
    private String customName;

    public EntityBuilder() {
        this.clazz = Zombie.class;
    }

    public EntityBuilder(Class<? extends Entity> entityClass) {
        this.clazz = entityClass;
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

    public Entity spawn(Location location) {
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
        });
    }

}
