package ui.control;

import javafx.scene.control.ComboBox;
import ui.TestEntity;

public class ComboBoxProperty extends ComboBox<String> implements ControlProperty {

    public ComboBoxProperty(String prompt) {
        this.setPromptText(prompt);
    }

    @Override
    public void populateValue(String newVal) {
        this.setValue(newVal);
    }

    @Override
    public void setAction(TestEntity entity, String action) {

    }
}
