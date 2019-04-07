package ui.control;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import ui.Propertable;
import ui.UIException;
import ui.manager.LabelManager;

import java.lang.reflect.Constructor;

public class ButtonProperty extends Button implements ControlProperty {

    public ButtonProperty(String label) {
        super(label);
    }

    @Override
    public void populateValue(String type, String newVal, LabelManager labels) {}

    /**
     * ButtonProperty action will always open another window of a certain type, passing the propertable object along with it
     * @param prop Propertable object to pass
     * @param action Class name of new stage to open
     */
    @Override
    public void setAction(Propertable prop, String propLabel, String action) throws UIException {
        try {
            Class<?> clazz = Class.forName(action);
            Constructor<?> constructor = clazz.getConstructor(Propertable.class);
            Stage instance = (Stage) constructor.newInstance(prop);
            this.setOnAction(e -> instance.show());
        } catch (Exception e) {
            throw new UIException("Error binding property control actions");
        }
    }
}
