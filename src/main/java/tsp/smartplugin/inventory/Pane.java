package tsp.smartplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import tsp.smartplugin.utils.Validate;

import javax.annotation.Nonnull;

public class Pane implements InventoryHolder {

    private final Inventory inventory;
    private final Page page;

    public Pane(int rows, @Nonnull String title) {
        Validate.notNull(title, "title can not be null!");

        if (rows > 6) {
            throw new IllegalArgumentException("Rows must be <= 6, got " + rows);
        }

        this.page = new Page(title);
        this.inventory = Bukkit.createInventory(this, rows * 9, page.getTitle());
    }

    public void onClick(InventoryClickEvent event) {}

    public void onClose(InventoryCloseEvent event) {}

    public int getSize() {
        return inventory.getSize();
    }

    public void update() {
        inventory.clear();
        page.render(inventory);
    }

    public void open(Player player, boolean update) {
        if (update) this.update();
        player.openInventory(inventory);
    }

    public void open(Player player) {
        open(player, true);
    }

    public Page getPage() {
        return page;
    }

    @Override
    @Nonnull
    public Inventory getInventory() {
        return inventory;
    }

}
