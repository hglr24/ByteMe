package ui.control;

import javafx.scene.control.ComboBox;
import ui.Propertable;
import ui.manager.LabelManager;

public class ComboBoxProperty extends ComboBox<String> implements ControlProperty {

    public ComboBoxProperty(String prompt) {
        this.setPromptText(prompt);
    }

    @Override
    public void populateValue(String type, String newVal, LabelManager labels) {
        this.itemsProperty().set(labels.getLabels(type));
        this.setValue(newVal);
    }

    @Override
    public void setAction(Propertable propertable, String propLabel, String action) {
        this.setOnAction(e -> updateProperty(propertable, propLabel, this.getValue()));
    }

    private void updateProperty(Propertable prop, String propLabel, String value) {
        prop.getPropertyMap().put(propLabel, value);
    }
}
