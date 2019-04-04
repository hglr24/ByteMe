package ui.manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.TestEntity;

public class AssetManager extends Stage {

    private TestEntity myEntity;

    public AssetManager(TestEntity entity) {
        myEntity = entity;
        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp);
        this.setScene(scene);
        this.setResizable(false);
    }
}
