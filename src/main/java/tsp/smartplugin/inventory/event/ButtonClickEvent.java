package tsp.smartplugin.inventory.event;

import org.bukkit.event.inventory.InventoryClickEvent;
import tsp.smartplugin.inventory.Button;

public class ButtonClickEvent {

    private final Button button;
    private final InventoryClickEvent event;

    public ButtonClickEvent(Button button, InventoryClickEvent event) {
        this.button = button;
        this.event = event;
    }

    public Button getButton() {
        return button;
    }

    public InventoryClickEvent getInventoryEvent() {
        return event;
    }

}
