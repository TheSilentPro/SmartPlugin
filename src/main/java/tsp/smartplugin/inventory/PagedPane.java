package tsp.smartplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tsp.smartplugin.builder.item.ItemBuilder;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class PagedPane {

    private final SortedMap<Integer, Pane> panes;
    private int current;
    private ItemStack controlNext;
    private ItemStack controlBack;
    private ItemStack controlInfo;

    public PagedPane(Map<Integer, Pane> panes) {
        this.panes = new TreeMap<>(panes);
    }

    public PagedPane() {
        this.panes = new TreeMap<>();
    }

    public SortedMap<Integer, Pane> getPanes() {
        return panes;
    }

    public void open(Player player, int pageIndex) {
        current = pageIndex;

        Pane pane = panes.get(pageIndex);
        Page page = pane.getPage();

        // Controls
        if (current > 1) {
            page.setButton(pane.getSize() - 8, new Button(translateControl(controlBack), e -> open(player, current--)));
        }
        if (current < panes.size()) {
            page.setButton(pane.getSize() - 2, new Button(translateControl(controlNext), e -> open(player, current++)));
        }
        page.setButton(pane.getSize() - 5, new Button(translateControl(controlInfo), e -> {
            InventoryClickEvent event = e.getInventoryEvent();
            if (event.isLeftClick()) {
                Inventory anvil = Bukkit.createInventory(null, InventoryType.ANVIL);
                anvil.addItem(new ItemStack(Material.PAPER));

            }
        }));

        pane.open(player);
    }

    public void open(Player player) {
        open(player, 1);
    }

    public void setControlBack(ItemStack controlBack) {
        this.controlBack = controlBack;
    }

    public void setControlNext(ItemStack controlNext) {
        this.controlNext = controlNext;
    }

    public void setControlInfo(ItemStack controlInfo) {
        this.controlInfo = controlInfo;
    }

    private static final Pattern PATTERN_BACK = Pattern.compile("(%back%)", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_NEXT = Pattern.compile("(%next%)", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_CURRENT = Pattern.compile("(%current%)", Pattern.CASE_INSENSITIVE);

    private ItemStack translateControl(ItemStack item) {
        String name = item.getItemMeta() != null ? item.getItemMeta().getDisplayName() : item.getType().name();
        name = PATTERN_BACK.matcher(name).replaceAll("" + (current - 1));
        name = PATTERN_NEXT.matcher(name).replaceAll("" + (current + 1));
        name = PATTERN_CURRENT.matcher(name).replaceAll("" + current);

        return new ItemBuilder(item)
                .name(name)
                .build();
    }

}
