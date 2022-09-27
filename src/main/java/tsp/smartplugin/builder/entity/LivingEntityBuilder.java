package tsp.smartplugin.builder.entity;

import com.google.common.annotations.Beta;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;

@Beta
public class LivingEntityBuilder extends EntityBuilder {

    @Override
    public LivingEntity spawn(@Nonnull Location location) {
        // TODO: Logic for livingentity
        return (LivingEntity) super.spawn(location);
    }

}
