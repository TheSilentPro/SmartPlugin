package tsp.smartplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import tsp.smartplugin.builder.item.ItemBuilder;

import javax.annotation.Nonnull;

public class InputPane implements InventoryHolder {

    private final AnvilInventory inventory;
    private ItemStack item;

    public InputPane(String title) {
        this.inventory = (AnvilInventory) Bukkit.createInventory(this, InventoryType.ANVIL, title);
        item = new ItemBuilder(Material.PAPER)
                .name("Search...")
                .build();
    }

    public void onResult(InputClickEvent event) {}

    public void onClick(InventoryClickEvent event) {}

    public void onClose(InventoryCloseEvent event) {}

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void render() {
        inventory.clear();
        inventory.addItem(item);
    }

    public void open(Player player) {
        render();
        player.openInventory(inventory);
    }

    @Override
    @Nonnull
    public Inventory getInventory() {
        return inventory;
    }

}
