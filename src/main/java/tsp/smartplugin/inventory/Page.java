package tsp.smartplugin.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import tsp.smartplugin.utils.Validate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Page {

    private final List<Button> buttons = new ArrayList<>();
    private final int maxSize;

    public Page(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * @param event The click event
     */
    public void handleClick(@Nonnull InventoryClickEvent event) {
        Validate.notNull(event, "InventoryClickEvent must not be null!");

        // user clicked in his own tsp.smartaddon.inventory. Silently drop it
        if (event.getRawSlot() > event.getInventory().getSize()) {
            return;
        }
        // user clicked outside of the tsp.smartaddon.inventory
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return;
        }
        if (event.getSlot() >= buttons.size()) {
            return;
        }
        Button button = buttons.get(event.getSlot());
        button.onClick(event);
    }

    /**
     * @return True if there is space left
     */
    public boolean hasSpace() {
        return buttons.size() < maxSize * 9;
    }

    /**
     * @param button The {@link Button} to add
     *
     * @return True if the button was added, false if there was no space
     */
    public boolean addButton(@Nonnull Button button) {
        Validate.notNull(button, "Button must not be null!");

        if (!hasSpace()) {
            return false;
        }
        buttons.add(button);

        return true;
    }

    /**
     * @param i Slot
     * @param button The {@link Button} to add
     * @return True if the button was added
     */
    public boolean setButton(int i, @Nonnull Button button) {
        Validate.notNull(button, "Button must not be null!");

        buttons.set(i, button);
        return true;
    }

    /**
     * @param button The {@link Button} to remove
     *
     * @return True if the button was removed
     */
    public boolean removeButton(@Nonnull Button button) {
        Validate.notNull(button, "Button must not be null!");

        return buttons.remove(button);
    }

    /**
     * @param inventory The inventory to render in
     */
    public void render(@Nonnull Inventory inventory) {
        Validate.notNull(inventory, "Inventory must not be null!");

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);

            inventory.setItem(i, button.getItemStack());
        }
    }

    /**
     * @return True if this page is empty
     */
    public boolean isEmpty() {
        return buttons.isEmpty();
    }
}