package tsp.smartplugin.utils;

import tsp.smartplugin.inventory.Button;
import tsp.smartplugin.inventory.Page;
import tsp.smartplugin.inventory.Pane;

public class PaneUtils {

    public static void fillBorder(Pane pane, Button item) {
        int size = pane.getSize();
        int rows = (size + 1) / 9;

        Page page = pane.getPage();

        // Fill top
        for (int i = 0; i < 9; i++) {
            page.setButton(i, item);
        }

        // If inventory is only one row, no need for anything else
        if (size > 9) {
            // Fill bottom
            for (int i = size - 9; i < size; i++) {
                page.setButton(i, item);
            }

            // Fill sides
            for (int i = 2; i <= rows - 1; i++) {
                page.setButton(i * 9 - 1, item);
                page.setButton((i - 1) * 9, item);
            }
        }
    }

}
