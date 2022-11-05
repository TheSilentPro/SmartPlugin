package tsp.smartplugin.inventory;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class Page {

    private final String title;
    private final Map<Integer, Button> buttons;

    public Page(String title, Map<Integer, Button> buttons) {
        this.title = title;
        this.buttons = buttons != null ? buttons : new HashMap<>();
    }

    public Page(String title) {
        this(title, null);
    }

    public void onClose(InventoryCloseEvent event) {}

    public void render(Inventory inventory) {
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getItem());
        }
    }

    public void addButtons(Button... buttons) {
        for (Button button : buttons) {
            if (button != null) {
                this.buttons.put(this.buttons.size() + 1, button);
            }
        }
    }

    public void setButton(int index, Button button) {
        this.buttons.put(index, button);
    }

    public void removeButtons(int... indexes) {
        for (int index : indexes) {
            this.buttons.remove(index);
        }
    }

    public Optional<Button> getButton(int index) {
        return Optional.ofNullable(this.buttons.get(index));
    }

    /**
     * The buttons map.
     * Modifications to the map are projected onto the page.
     *
     * @return Modifiable map of buttons.
     */
    public Map<Integer, Button> getButtonsMap() {
        return buttons;
    }

    public String getTitle() {
        return title;
    }

}
