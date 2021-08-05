package tsp.smartplugin.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

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
    public void handleClick(InventoryClickEvent event) {
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
    public boolean addButton(Button button) {
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
    public boolean setButton(int i, Button button) {
        if (!hasSpace()) {
            return false;
        }
        buttons.set(i, button);

        return true;
    }

    /**
     * @param button The {@link Button} to remove
     *
     * @return True if the button was removed
     */
    public boolean removeButton(Button button) {
        return buttons.remove(button);
    }

    /**
     * @param inventory The tsp.smartaddon.inventory to render in
     */
    public void render(Inventory inventory) {
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
