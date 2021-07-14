package tsp.smartplugin.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tsp.smartplugin.inventory.paged.PagedPane;
import tsp.smartplugin.inventory.single.Pane;

import java.util.Arrays;

/**
 * Utility class for managing inventories
 *
 * @author TheSilentPro
 */
public class InventoryUtils {

    /**
     * Creates a {@link PagedPane}
     *
     * @param size The size of the pane (0 - 6)
     * @param rows The number of rows (0 - 6)
     * @param title The title of the pane
     * @return The PagedPane
     */
    public static PagedPane createPagedPane(int size, int rows, String title) {
        return new PagedPane(size, rows, title);
    }

    /**
     * Creates a {@link Pane}
     *
     * @param rows The number of rows (0 - 6)
     * @param title The title of the pane
     * @return The Pane
     */
    public static Pane createPane(int rows, String title) {
        return new Pane(rows, title);
    }

    /**
     * Fills the borders of an {@link Inventory}
     *
     * @param inv The target tsp.smartaddon.inventory
     * @param item Item to use for filling
     */
    public static void fillBorder(Inventory inv, ItemStack item) {
        int size = inv.getSize();
        int rows = (size + 1) / 9;

        // Fill top
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, item);
        }

        // Fill bottom
        for (int i = size - 9; i < size; i++) {
            inv.setItem(i, item);
        }

        // Fill sides
        for (int i = 2; i <= rows - 1; i++) {
            int[] slots = new int[]{i * 9 - 1, (i - 1) * 9};
            inv.setItem(slots[0], item);
            inv.setItem(slots[1], item);
        }
    }

    /**
     * Fills a row in an {@link Inventory}
     *
     * @param rowIndex Index of the row to fill (0 - 6)
     * @param itemStack The item to use for filling
     * @param inventory The target tsp.smartaddon.inventory
     */
    private void fillRow(int rowIndex, ItemStack itemStack, Inventory inventory) {
        int yMod = rowIndex * 9;
        for (int i = 0; i < 9; i++) {
            int slot = yMod + i;
            inventory.setItem(slot, itemStack);
        }
    }

    /**
     * Fills the empty slots of an {@link Inventory}
     *
     * @param inv The target tsp.smartaddon.inventory
     * @param item The item used for filling
     * @param ignored Ignored slots
     */
    public static void fill(Inventory inv, ItemStack item, int... ignored) {
        int size = inv.getSize();

        // Fill
        for (int i = 0; i < size; i++) {
            if (ignored != null && !contains(i, ignored)) {
                ItemStack slotItem = inv.getItem(i);
                if (slotItem == null || slotItem.getType() == Material.AIR) {
                    inv.setItem(i, item);
                }
            }
        }
    }

    /**
     * Checks if an array contains a number
     * This is mainly for internal use
     *
     * @param n The number to check for
     * @param array The array to check
     * @return If the number was found in the array
     */
    public static boolean contains(int n, int... array) {
        return Arrays.binarySearch(array, n) > -1;
    }

}
