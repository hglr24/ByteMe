package ui.panes;
import events.Event;
@FunctionalInterface
public interface Editor {
    void editEvent(Event toBeEdited);
}
