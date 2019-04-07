package engine.external;

import javafx.scene.input.KeyCode;

import java.util.List;

public interface IEvent {
    // need to make this take in a list of keycode inputs as well
    void execute(List<Entity> entities);
}
