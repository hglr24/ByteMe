package ui.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.TestEntity;
import ui.UIException;

public class PaneTester extends Application {

    private static final String TEST_STYLESHEET = "default.css";

    @Override
    public void start(Stage testStage) {
        testStage.setTitle("Pane Test");
        try {
            PropertiesPane testPane = new PropertiesPane("Test", new TestEntity());
            Scene testScene = new Scene(testPane);
            testScene.getStylesheets().add(TEST_STYLESHEET);
            testStage.setScene(testScene);
        } catch (UIException e) {
            ErrorBox errorbox = new ErrorBox("Test", e.getMessage());
            errorbox.display();
        }
        testStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
