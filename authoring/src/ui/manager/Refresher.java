package ui.manager;

import ui.panes.EventPane;

@FunctionalInterface
public interface Refresher {
    /**
     * This is a functional interface used to refresh the current display of engine.external.events that the user has created once a valid
     * event has been saved, as the window displaying the engine.external.events opens a window of options where the save button is located.
     * This removes the need for a circular dependency.
     * @see EventPane
     * @author Anna Darwish
     */
    void refresh();
}
