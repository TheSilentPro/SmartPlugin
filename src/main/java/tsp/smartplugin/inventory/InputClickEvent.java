package tsp.smartplugin.inventory;

import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InputClickEvent implements Cancellable {

    private final InventoryClickEvent inventoryClickEvent;
    private final ItemStack item;
    private final String text;
    private boolean cancelled;

    public InputClickEvent(InventoryClickEvent event, ItemStack item, String text) {
        this.inventoryClickEvent = event;
        this.item = item;
        this.text = text;
    }

    public InventoryClickEvent getInventoryClickEvent() {
        return inventoryClickEvent;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}