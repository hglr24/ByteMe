package ui.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.EntityField;
import ui.ErrorBox;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.UIException;
import ui.manager.GroupManager;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;
import ui.panes.DefaultTypesPane;
import ui.panes.PropertiesPane;
import ui.panes.UserCreatedTypesPane;
import ui.panes.Viewer;

public class MainGUI extends Application {

    @Override
    public void start(Stage testStage) {
        alternateStart(testStage);
        //AssetManager temp = new AssetManager();
        //temp.show();
        /**
         *
         UserCreatedTypesPane userCreatedTypesPane = new UserCreatedTypesPane();
         DefaultTypesPane entityTypePane = new DefaultTypesPane(userCreatedTypesPane);
         Viewer viewer = new Viewer(200, 500);
         HBox hBox = new HBox();
         hBox.getChildren().addAll(viewer, entityTypePane, userCreatedTypesPane);
         entityTypePane.setPrefSize(300, 600);
         Scene scene = new Scene(hBox, 600, 600);
         //scene.getStylesheets().add("default.css");
         testStage.setScene(scene);
         testStage.show();
         */
        //Stage viewerStage = new Stage();
        //Scene viewerScene = new Scene(new Viewer(1000, 1000), 500, 500);
        //viewerStage.setScene(viewerScene);
        //viewerStage.show();

    }

    private void alternateStart(Stage stage){
        DataFormat dataFormat = new DataFormat("Entity");

        ObjectManager objectManager = new ObjectManager(new LabelManager());
        UserCreatedTypesPane userCreatedTypesPane = new UserCreatedTypesPane(objectManager);
        userCreatedTypesPane.setMinWidth(200);
        Viewer viewer = new Viewer(500, 500, userCreatedTypesPane);
        viewer.setMinWidth(400);
        viewer.setMinHeight(300);
        DefaultTypesPane defaultTypesPane = new DefaultTypesPane(userCreatedTypesPane);
        defaultTypesPane.setMinWidth(200);
        LabelManager testLabels = new LabelManager();
        addTestLabels(testLabels);
        ObjectManager manager = new ObjectManager(testLabels);
        AuthoringEntity obj1 = populateTestObjects(manager);
        try {
            PropertiesPane testPaneObj =
                    new PropertiesPane("Object", obj1, "object_properties_list", testLabels);
            PropertiesPane testPaneLvl =
                    new PropertiesPane("Level", new AuthoringLevel("Level_1", manager), "level_properties_list", testLabels);
            PropertiesPane testPaneIns =
                    new PropertiesPane("Instance", obj1, "instance_properties_list", testLabels);
            GroupManager testCreateGroup = new GroupManager(manager);

            BorderPane borderPane = new BorderPane();
            HBox hBox = new HBox();
            hBox.getChildren().addAll(defaultTypesPane, userCreatedTypesPane);
            borderPane.setRight(hBox);
            borderPane.setCenter(viewer);
            HBox hBox1 = new HBox();
            hBox1.getChildren().addAll(testPaneLvl, testPaneIns, testPaneObj);
            borderPane.setBottom(hBox1);

            Scene scene = new Scene(borderPane);
            //scene.getStylesheets().add("default.css");
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1000);
            stage.show();

        } catch (UIException e) {
            ErrorBox errorbox = new ErrorBox("Test", e.getMessage());
            errorbox.display();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }

    private void addTestLabels(LabelManager testLabels) {
        testLabels.addLabel(EntityField.GROUP, "Enemies");
        testLabels.addLabel(EntityField.GROUP, "Platforms");
        testLabels.addLabel(EntityField.LABEL, "Mario");
        testLabels.addLabel(EntityField.LABEL, "Goomba");
        testLabels.addLabel(EntityField.LABEL, "Brick_Block");
    }

    private AuthoringEntity populateTestObjects(ObjectManager manager) {
        AuthoringEntity a = new AuthoringEntity("object1", manager);
        AuthoringEntity b = new AuthoringEntity("object2", manager);
        AuthoringEntity c = new AuthoringEntity("object3", manager);
        AuthoringEntity d = new AuthoringEntity("object4", manager);
        AuthoringEntity e = new AuthoringEntity("object1", manager);
        AuthoringEntity f = new AuthoringEntity("object1", manager);
        manager.addEntity(a);
        manager.addEntity(b);
        manager.addEntity(c);
        manager.addEntity(d);
        manager.addEntity(e);
        manager.addEntity(f);
        a.getPropertyMap().put(EntityField.GROUP, "Enemies");
        b.getPropertyMap().put(EntityField.GROUP, "Platforms");
        c.getPropertyMap().put(EntityField.GROUP, "Enemies");
        d.getPropertyMap().put(EntityField.GROUP, "Platforms");
        return f;
    }

}