package tsp.smartplugin.menu.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.smartplugin.menu.InputPane;
import tsp.smartplugin.menu.Pane;
import tsp.smartplugin.listener.BukkitListener;

public class PaneListener extends BukkitListener {

    public PaneListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Pane pane) {
            // own inventory click
            if (event.getRawSlot() > event.getInventory().getSize()) {
                return;
            }
            // outside of inventory
            if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                return;
            }

            pane.getPage().getButton(event.getRawSlot()).ifPresent(button -> button.getClickHandler().ifPresent(handler -> handler.onButtonClick(new ButtonClickEvent(button, event))));

            pane.onClick(event);
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

                    if (ice.isCancelled()) {
                        event.setCancelled(true);
                    }
                }

                pane.onClick(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof Pane pane) {
            pane.onClose(event);
        }

        if (event.getInventory().getHolder() instanceof InputPane pane) {
            pane.onClose(event);
        }
    }

}
