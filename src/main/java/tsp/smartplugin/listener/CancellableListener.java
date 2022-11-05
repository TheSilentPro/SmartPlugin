package tsp.smartplugin.listener;

import com.google.common.annotations.Beta;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

@Beta
public abstract class CancellableListener<T extends Event & Cancellable> extends SmartListener<T> {

    @Override
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(T event) {
        if (!event.isCancelled()) {
            handle(event);
        }
    }

}
