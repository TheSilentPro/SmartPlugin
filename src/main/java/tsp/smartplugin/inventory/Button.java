package tsp.smartplugin.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tsp.smartplugin.inventory.event.ButtonClickHandler;
import tsp.smartplugin.utils.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class Button {

    private final ItemStack item;
    private final ButtonClickHandler buttonClickHandler;

    public Button(@Nonnull ItemStack item, @Nullable ButtonClickHandler buttonClickHandler) {
        Validate.notNull(item, "Item can not be null!");

        this.item = item;
        this.buttonClickHandler = buttonClickHandler;
    }

    @Nonnull
    public Optional<ButtonClickHandler> getClickHandler() {
        return Optional.ofNullable(buttonClickHandler);
    }

    @Nonnull
    public ItemStack getItem() {
        return item;
    }

    public static class Builder {

        private ItemStack item = new ItemStack(Material.GRASS_BLOCK);
        private ButtonClickHandler buttonClickHandler = null;

        public Builder item(ItemStack item) {
            this.item = item;
            return this;
        }

        public Builder clickHandler(ButtonClickHandler handler) {
            this.buttonClickHandler = handler;
            return this;
        }

        public Button build() {
            return new Button(item, buttonClickHandler);
        }

    }

}
