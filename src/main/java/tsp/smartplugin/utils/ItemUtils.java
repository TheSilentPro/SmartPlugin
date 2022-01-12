package tsp.smartplugin.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tsp.smartplugin.SmartPlugin;
import tsp.smartplugin.data.PersistentDataAPI;

import java.util.function.Consumer;

/**
 * Utility class for an {@link ItemStack}
 *
 * @author TheSilentPro
 */
public final class ItemUtils {

    public static final NamespacedKey COOLDOWN_KEY = new NamespacedKey(SmartPlugin.getInstance().getPlugin(), "item_cooldown");
    public static final NamespacedKey LAST_USED_KEY = new NamespacedKey(SmartPlugin.getInstance().getPlugin(), "item_lastused");

    private ItemUtils() {}

    /**
     * Add cooldown to an item
     *
     * @param item The item to add a cooldown to
     * @param time The cooldown time
     */
    public static void setCooldown(ItemStack item, long time) {
        if (time > -1) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataAPI.setLong(meta, COOLDOWN_KEY, time);
            PersistentDataAPI.setLong(meta, LAST_USED_KEY, System.currentTimeMillis());
            item.setItemMeta(meta);
        }
    }

    /**
     * Get the cooldown time left on an item
     *
     * @param item The item to check
     * @return Time left. Returns -1 if there is no cooldown
     */
    public static long getTimeLeft(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        long l = PersistentDataAPI.getLong(meta, LAST_USED_KEY, -1);
        long time = PersistentDataAPI.getLong(meta, COOLDOWN_KEY, -1);
        if (l < 1 || time < 1) {
            return -1;
        }

        return ((l + time * 1000) - System.currentTimeMillis()) / 1000;
    }

    /**
     * Use an item and set it on cooldown
     *
     * @param item The item to use
     * @param cooldown The cooldown for the item when used
     * @param ready Ran when the item is used. Long is the cooldown the item was set to
     * @param timeleft Ran when the item is not ready. Long is time left
     */
    public static void use(ItemStack item, long cooldown, Consumer<Long> ready, Consumer<Long> timeleft) {
        long time = getTimeLeft(item);
        if (time < 1) {
            ready.accept(cooldown);
            setCooldown(item, cooldown);
        } else {
            timeleft.accept(time);
        }
    }

    /**
     * Use an item and set it on cooldown
     *
     * @param item The item to use
     * @param cooldown The cooldown for the item when used
     */
    public static void use(ItemStack item, long cooldown) {
        long time = getTimeLeft(item);
        if (time < 1) {
            setCooldown(item, cooldown);
        }
    }

}
