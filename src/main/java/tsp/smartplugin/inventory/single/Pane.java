package tsp.smartplugin.inventory.single;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import tsp.smartplugin.inventory.Button;
import tsp.smartplugin.inventory.Page;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nonnull;

/**
 * Represents a single page Pane
 *
 * @author TheSilentPro
 */
public class Pane implements InventoryHolder {

    private final Inventory inventory;
    private final Page page;

    public Pane(int rows, @Nonnull String title) {
        Validate.notNull(title, "title can not be null!");

        if (rows > 6) {
            throw new IllegalArgumentException("Rows must be <= 6, got " + rows);
        }

        this.inventory = Bukkit.createInventory(this, rows * 9, title);
        this.page = new Page(rows);
    }

    public void addButton(@Nonnull Button button) {
        Validate.notNull(button, "Button must not be null!");

        if (page.hasSpace()) {
            page.addButton(button);
            reRender();
        }
    }

    public void setButton(int i, @Nonnull Button button) {
        Validate.notNull(button, "Button must not be null!");

        if (page.hasSpace()) {
            page.setButton(i, button);
            reRender();
        }
    }

    public void removeButton(@Nonnull Button button) {
        Validate.notNull(button, "Button must not be null!");

        page.removeButton(button);
        reRender();
    }

    public void onClick(@Nonnull InventoryClickEvent event) {
        Validate.notNull(event, "InventoryClickEvent must not be null!");

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
    public void open(@Nonnull Player player) {
        Validate.notNull(player, "Player must not be null!");

        reRender();
        player.openInventory(getInventory());
    }

}
