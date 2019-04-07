package ui.manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.Propertable;
import ui.TestEntity;

public class EventManager extends Stage {

    private TestEntity myEntity;

    public EventManager(Propertable prop) { // Loads common Events for object instance based on type label
        myEntity = (TestEntity) prop; // EventManager is only ever used for an Entity, so cast can happen
        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp);
        this.setScene(scene);
        this.setResizable(false);
    }
}
