package tsp.smartplugin.inventory.paged;

import tsp.smartplugin.inventory.Button;
import tsp.smartplugin.inventory.Page;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * A paged pane. Credits @ I Al Ianstaan
 */
public class PagedPane implements InventoryHolder {

    private final Inventory inventory;

    private final SortedMap<Integer, Page> pages = new TreeMap<>();
    private int currentIndex;
    private final int pageSize;

    @SuppressWarnings("WeakerAccess")
    protected Button controlBack;
    @SuppressWarnings("WeakerAccess")
    protected Button controlNext;

    /**
     * @param pageSize The page size. tsp.smartaddon.inventory rows - 2
     */
    public PagedPane(int pageSize, int rows, String title) {
        Objects.requireNonNull(title, "title can not be null!");
        if (rows > 6) {
            throw new IllegalArgumentException("Rows must be <= 6, got " + rows);
        }
        if (pageSize > 6) {
            throw new IllegalArgumentException("Page size must be <= 6, got" + pageSize);
        }

        this.pageSize = pageSize;
        inventory = Bukkit.createInventory(this, rows * 9, color(title));

        pages.put(0, new Page(pageSize));
    }

    /**
     * @param button The button to add
     */
    public void addButton(Button button) {
        for (Map.Entry<Integer, Page> entry : pages.entrySet()) {
            if (entry.getValue().addButton(button)) {
                if (entry.getKey() == currentIndex) {
                    reRender();
                }
                return;
            }
        }
        Page page = new Page(pageSize);
        page.addButton(button);
        pages.put(pages.lastKey() + 1, page);

        reRender();
    }

    /**
     * @param button The Button to remove
     */
    @SuppressWarnings("unused")
    public void removeButton(Button button) {
        for (Iterator<Map.Entry<Integer, Page>> iterator = pages.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Integer, Page> entry = iterator.next();
            if (entry.getValue().removeButton(button)) {

                // we may need to delete the page
                if (entry.getValue().isEmpty()) {
                    // we have more than one page, so delete it
                    if (pages.size() > 1) {
                        iterator.remove();
                    }
                    // the currentIndex now points to a page that does not exist. Correct it.
                    if (currentIndex >= pages.size()) {
                        currentIndex--;
                    }
                }
                // if we modified the current one, re-render
                // if we deleted the current page, re-render too
                if (entry.getKey() >= currentIndex) {
                    reRender();
                }
                return;
            }
        }
    }

    /**
     * @return The amount of pages
     */
    @SuppressWarnings("WeakerAccess")
    public int getPageAmount() {
        return pages.size();
    }

    /**
     * @return The number of the current page (1 based)
     */
    @SuppressWarnings("WeakerAccess")
    public int getCurrentPage() {
        return currentIndex + 1;
    }

    /**
     * @param index The index of the new page
     */
    @SuppressWarnings("WeakerAccess")
    public void selectPage(int index) {
        if (index < 0 || index >= getPageAmount()) {
            throw new IllegalArgumentException(
                    "Index out of bounds s: " + index + " [" + 0 + " " + getPageAmount() + ")"
            );
        }
        if (index == currentIndex) {
            return;
        }

        currentIndex = index;
        reRender();
    }

    /**
     * Renders the tsp.smartaddon.inventory again
     */
    @SuppressWarnings("WeakerAccess")
    public void reRender() {
        inventory.clear();
        pages.get(currentIndex).render(inventory);

        controlBack = null;
        controlNext = null;
        createControls(inventory);
    }

    /**
     * @param event The {@link InventoryClickEvent}
     */
    @SuppressWarnings("WeakerAccess")
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        // back item
        if (event.getSlot() == inventory.getSize() - 8) {
            if (controlBack != null) {
                controlBack.onClick(event);
            }
            return;
        }
        // next item
        else if (event.getSlot() == inventory.getSize() - 2) {
            if (controlNext != null) {
                controlNext.onClick(event);
            }
            return;
        }

        pages.get(currentIndex).handleClick(event);

    }

    /**
     * Get the object's tsp.smartaddon.inventory.
     *
     * @return The tsp.smartaddon.inventory.
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Creates the controls
     *
     * @param inventory The tsp.smartaddon.inventory
     */
    @SuppressWarnings("WeakerAccess")
    protected void createControls(Inventory inventory) {
        // create separator
        fillRow(
                inventory.getSize() / 9 - 2,
                getItemStack(Material.BLACK_STAINED_GLASS_PANE, " "),
                inventory
        );

        if (getCurrentPage() > 1) {
            String name = String.format(
                    Locale.ROOT,
                    "&3&lPage &a&l%d &7/ &c&l%d",
                    getCurrentPage() - 1, getPageAmount()
            );
            String lore = String.format(
                    Locale.ROOT,
                    "&7Brings you back to the page &c%d",
                    getCurrentPage() - 1
            );
            ItemStack itemStack = getItemStack(Material.COAL_BLOCK, name, lore);
            controlBack = new Button(itemStack, event -> selectPage(currentIndex - 1));
            inventory.setItem(inventory.getSize() - 8, itemStack);
        }

        if (getCurrentPage() < getPageAmount()) {
            String name = String.format(
                    Locale.ROOT,
                    "&3&lPage &a&l%d &7/ &c&l%d",
                    getCurrentPage() + 1, getPageAmount()
            );
            String lore = String.format(
                    Locale.ROOT,
                    "&7Brings you to the page &c%d",
                    getCurrentPage() + 1
            );
            ItemStack itemStack = getItemStack(Material.IRON_BLOCK, name, lore);
            controlNext = new Button(itemStack, event -> selectPage(getCurrentPage()));
            inventory.setItem(inventory.getSize() - 2, itemStack);
        }

        {
            String name = String.format(
                    Locale.ROOT,
                    "&3&lPage &a&l%d &7/ &c&l%d",
                    getCurrentPage(), getPageAmount()
            );
            String lore = String.format(
                    Locale.ROOT,
                    "&7You are on page &a%d &7/ &c%d",
                    getCurrentPage(), getPageAmount()
            );
            ItemStack itemStack = getItemStack(Material.BOOK, name, lore);
            inventory.setItem(inventory.getSize() - 5, itemStack);
        }
    }

    private void fillRow(int rowIndex, ItemStack itemStack, Inventory inventory) {
        int yMod = rowIndex * 9;
        for (int i = 0; i < 9; i++) {
            int slot = yMod + i;
            inventory.setItem(slot, itemStack);
        }
    }

    /**
     * @param type The {@link Material} of the {@link ItemStack}
     * @param name The name. May be null.
     * @param lore The lore. May be null.
     *
     * @return The item
     */
    @SuppressWarnings("WeakerAccess")
    protected ItemStack getItemStack(Material type, String name, String... lore) {
        ItemStack itemStack = new ItemStack(type);

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(color(name));
        }
        if (lore != null && lore.length != 0) {
            itemMeta.setLore(Arrays.stream(lore).map(this::color).collect(Collectors.toList()));
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @SuppressWarnings("WeakerAccess")
    protected String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * @param player The {@link Player} to open it for
     */
    public void open(Player player) {
        reRender();
        player.openInventory(getInventory());
    }

}
