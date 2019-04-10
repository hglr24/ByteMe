package ui;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import ui.manager.EventManager;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

/**
 * @author Harry Ross
 */
public class EventManagerTester extends Application {

    @Override
    public void start(Stage testStage) {
        ObjectManager manager = new ObjectManager(new SimpleObjectProperty<>());
        addTestLabels(manager.getLabelManager());
        AuthoringEntity testEntity = populateTestObjects(manager);
        EventManager testEventManager = new EventManager(testEntity);
        testEventManager.show();
    }

    private void addTestLabels(LabelManager testLabels) {
        testLabels.addLabel(EntityField.GROUP, "Enemies");
        testLabels.addLabel(EntityField.GROUP, "Platforms");
    }

    private AuthoringEntity populateTestObjects(ObjectManager manager) {
        AuthoringEntity a = new AuthoringEntity("object1", manager);
        manager.addEntityType(a);
        return a;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
