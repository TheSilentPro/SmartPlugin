package tsp.smartplugin.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import tsp.smartplugin.builder.item.ItemBuilder;

import java.util.SortedMap;
import java.util.regex.Pattern;

public class PagedPane {

    private SortedMap<Integer, Pane> panes;
    private int current;
    private ItemStack controlNext;
    private ItemStack controlBack;
    private ItemStack controlInfo;

    private int defaultRows;
    private String defaultTitle;

    public PagedPane(int rows, String title) {
        this.defaultRows = rows;
        this.defaultTitle = title;
    }

    public SortedMap<Integer, Pane> getPanes() {
        return panes;
    }

    public void addPane(Pane pane) {
        this.panes.put(panes.size() + 1, pane);
    }

    public void open(Player player, int pageIndex) {
        current = pageIndex;

        Pane pane = panes.get(pageIndex);
        Page page = pane.getPage();

        // Controls
        if (current > 1) {
            page.setButton(pane.getSize() - 8, new Button(translateControl(controlBack), e -> open(player, current - 1)));
        }
        if (current < panes.size()) {
            page.setButton(pane.getSize() - 2, new Button(translateControl(controlNext), e -> open(player, current + 1)));
        }
        page.setButton(pane.getSize() - 5, new Button(translateControl(controlInfo), e -> {
            InventoryClickEvent event = e.getInventoryEvent();
            if (event.isLeftClick()) {
                InputPane input = new InputPane("Select Page");
                input.setItem(new ItemBuilder(Material.PAPER).name("").build());
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
