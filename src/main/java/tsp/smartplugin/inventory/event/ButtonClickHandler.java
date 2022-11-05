package tsp.smartplugin.inventory.event;

@FunctionalInterface
public interface ButtonClickHandler {

    void onButtonClick(ButtonClickEvent event);

}