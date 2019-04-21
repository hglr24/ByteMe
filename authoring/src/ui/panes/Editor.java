package ui.panes;
import engine.external.events.Event;
@FunctionalInterface
public interface Editor {
    void editEvent(Event toBeEdited);
}
