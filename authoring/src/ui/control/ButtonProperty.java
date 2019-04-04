package ui.control;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import ui.TestEntity;
import ui.UIException;

import java.lang.reflect.Constructor;

public class ButtonProperty extends Button implements ControlProperty {

    public ButtonProperty(String label) {
        super(label);
    }

    @Override
    public void populateValue(String newVal) {}

    /**
     * ButtonProperty action will always open another window of a certain type, passing the entity along with it
     * @param entity Entity to pass
     * @param action Class name of new stage to open
     */
    @Override
    public void setAction(TestEntity entity, String action) throws UIException {
        try {
            Class<?> clazz = Class.forName(action);
            Constructor<?> constructor = clazz.getConstructor(TestEntity.class);
            Stage instance = (Stage) constructor.newInstance(entity);
            this.setOnAction(e -> instance.show());
        } catch (Exception e) {
            throw new UIException("Error binding property control actions");
        }
    }
}
