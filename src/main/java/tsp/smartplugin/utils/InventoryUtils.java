package tsp.smartplugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Utility class for managing inventories
 *
 * @author TheSilentPro
 */
public final class InventoryUtils {

    private InventoryUtils() {}

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
     * @param inv The inventory to fill
     * @param item The item used for filling
     * @param ignored Ignored slots. Must be ordered lowest -> highest
     */
    public static void binaryFill(Inventory inv, ItemStack item, int... ignored) {
        int size = inv.getSize();

        // Fill
        for (int i = 0; i < size; i++) {
            // If slot is not ignored
            if (ignored != null && Arrays.binarySearch(ignored, i) < 0) {
                ItemStack slotItem = inv.getItem(i);
                if (slotItem == null || slotItem.getType() == Material.AIR) {
                    inv.setItem(i, item);
                }
            }
        }
    }

}
