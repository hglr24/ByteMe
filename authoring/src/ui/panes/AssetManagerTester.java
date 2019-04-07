package ui.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.TestEntity;
import ui.UIException;
import ui.manager.AssetManager;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

public class AssetManagerTester extends Application {

    @Override
    public void start(Stage testStage) {
        //AssetManager temp = new AssetManager();
        //temp.show();
        CreateNewTypePane newObjectTypePane = new CreateNewTypePane("Obstacles", "Platform");
        newObjectTypePane.show();
        DefaultTypesPane entityTypePane = new DefaultTypesPane();
        entityTypePane.setPrefSize(300, 600);
        Scene scene = new Scene(entityTypePane, 600, 600);
        //scene.getStylesheets().add("default.css");
        testStage.setScene(scene);
        testStage.show();
        //Stage viewerStage = new Stage();
        //Scene viewerScene = new Scene(new Viewer(1000, 1000), 500, 500);
        //viewerStage.setScene(viewerScene);
        //viewerStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
