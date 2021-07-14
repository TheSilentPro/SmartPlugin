package tsp.smartplugin.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tsp.smartplugin.SmartPlugin;
import tsp.smartplugin.data.PersistentDataAPI;

public class ItemUtils {

    public static final NamespacedKey COOLDOWN_KEY = new NamespacedKey(SmartPlugin.getInstance(), "item_cooldown");
    public static final NamespacedKey LAST_USED_KEY = new NamespacedKey(SmartPlugin.getInstance(), "item_lastused");

    /**
     * Add cooldown to an item
     *
     * @param item The item to add a cooldown to
     * @param time The cooldown time
     */
    public static void addCooldown(ItemStack item, int time) {
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

}
