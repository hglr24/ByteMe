package ui.panes;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.EntityField;
import ui.ErrorBox;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.Propertable;
import ui.PropertableType;
import ui.UIException;
import ui.manager.GroupManager;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

/**
 * @author Harry Ross
 */
public class PaneTester extends Application {

    private static final String TEST_STYLESHEET = "default.css";

    @Override
    public void start(Stage testStage) {
        testStage.setTitle("Pane Test");
        testStage.setResizable(false);
        ObjectManager manager = new ObjectManager(new SimpleObjectProperty<>());
        addTestLabels(manager.getLabelManager());
        AuthoringEntity obj1 = populateTestObjects(manager);
        SimpleObjectProperty<Propertable> testObj = new SimpleObjectProperty<>(obj1);
        SimpleObjectProperty<Propertable> testLvl = new SimpleObjectProperty<>(new AuthoringLevel("Level_1"));

        try {
            PropertiesPane testPaneObj = new PropertiesPane(manager, PropertableType.OBJECT, testObj, manager.getLabelManager());
            PropertiesPane testPaneLvl = new PropertiesPane(manager, PropertableType.LEVEL, testLvl, manager.getLabelManager());
            PropertiesPane testPaneIns = new PropertiesPane(manager, PropertableType.INSTANCE, testObj, manager.getLabelManager());
            GroupManager testCreateGroup = new GroupManager(manager);

            Scene testScene = new Scene(testPaneObj);
            testCreateGroup.show();

            testScene.getStylesheets().add(TEST_STYLESHEET);
            testStage.setScene(testScene);
            Scene testScene2 = new Scene(testPaneIns);
            Scene testScene3 = new Scene(testPaneLvl);

            Stage testStage2 = new Stage();
            testStage2.setScene(testScene2);
            testStage2.setResizable(false);
            Stage testStage3 = new Stage();
            testStage3.setScene(testScene3);
            testStage3.setResizable(false);
            testScene2.getStylesheets().add(TEST_STYLESHEET);
            testScene3.getStylesheets().add(TEST_STYLESHEET);
            testStage2.show();
            testStage3.show();

            testStage.show();
        } catch (UIException e) {
            ErrorBox errorbox = new ErrorBox("Test", e.getMessage());
            errorbox.display();
        }
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
        manager.addEntityType(a);
        manager.addEntityType(b);
        manager.addEntityType(c);
        manager.addEntityType(d);
        manager.addEntityType(e);
        manager.addEntityType(f);
        a.getPropertyMap().put(EntityField.GROUP, "Enemies");
        b.getPropertyMap().put(EntityField.GROUP, "Platforms");
        c.getPropertyMap().put(EntityField.GROUP, "Enemies");
        d.getPropertyMap().put(EntityField.GROUP, "Platforms");
        return f;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
