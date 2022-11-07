package tsp.smartplugin.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Listens for click events for the {@link PagedPane} and {@link Pane}
 * Requires to be registered.
 */
public class PaneListener implements Listener {

    public PaneListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Pane) {
            ((Pane) holder).onClick(event);
        }

        if (event.getInventory().getType() == InventoryType.ANVIL) {
            AnvilInventory inventory = (AnvilInventory) event.getInventory();
            if (inventory.getHolder() instanceof InputPane pane) {
                if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                    ItemStack item = inventory.getItem(event.getRawSlot());
                    String name = "";
                    if (item != null && item.getItemMeta() != null) {
                        name = item.getItemMeta().getDisplayName();
                    }

                    InputClickEvent ice = new InputClickEvent(event, item, name);
                    pane.onResult(ice);

                    event.setCancelled(true);
                }

                pane.onClick(event);
            }
        }
    }
}