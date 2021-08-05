package tsp.smartplugin.inventory.single;

import org.bukkit.entity.Player;
import tsp.smartplugin.inventory.Button;
import tsp.smartplugin.inventory.Page;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

/**
 * Represents a Pane
 *
 * @author TheSilentPro
 */
public class Pane implements InventoryHolder {

    private final Inventory inventory;
    private final Page page;

    public Pane(int rows, String title) {
        Objects.requireNonNull(title, "title can not be null!");
        if (rows > 6) {
            throw new IllegalArgumentException("Rows must be <= 6, got " + rows);
        }

        this.inventory = Bukkit.createInventory(this, rows * 9, title);
        this.page = new Page(rows);
    }

    public void addButton(Button button) {
        if (page.hasSpace()) {
            page.addButton(button);
            reRender();
        }
    }

    public void setButton(int i, Button button) {
        if (page.hasSpace()) {
            page.setButton(i, button);
            reRender();
        }
    }

    public void removeButton(Button button) {
        page.removeButton(button);
        reRender();
    }

    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        page.handleClick(event);
    }

    public void reRender() {
        inventory.clear();
        page.render(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public Page getPage() {
        return page;
    }

    /**
     * @param player The {@link Player} to open it for
     */
    public void open(Player player) {
        reRender();
        player.openInventory(getInventory());
    }

}
