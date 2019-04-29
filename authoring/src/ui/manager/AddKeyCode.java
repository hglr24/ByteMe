package ui.manager;

import engine.external.events.Event;
import javafx.scene.input.KeyCode;

@FunctionalInterface
public interface AddKeyCode {
    void refresh(Event eventToModify, KeyCode keycode);
}
