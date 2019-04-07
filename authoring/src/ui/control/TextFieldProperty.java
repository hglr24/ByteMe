package ui.control;

import javafx.scene.control.TextField;
import ui.ErrorBox;
import ui.Propertable;
import ui.Utility;
import ui.manager.LabelManager;

import java.util.ResourceBundle;

/**
 * @author Harry Ross
 */
public class TextFieldProperty extends TextField implements ControlProperty {

    private LabelManager myLabelManager;
    private String myCurrentValue;

    private static final String PROP_SYNTAX = "property_syntax";

    public TextFieldProperty() {
        myLabelManager = new LabelManager();
        myCurrentValue = "";
    }

    /**
     * Only used when creating a new TextField
     * @param type
     * @param newVal
     * @param labels
     */
    @Override
    public void populateValue(String type, String newVal, LabelManager labels) {
        this.setText(newVal);
        myCurrentValue = newVal;
        myLabelManager = labels;
    }

    @Override
    public void setAction(Propertable propertable, String label, String action) {
        this.focusedProperty().addListener(e -> updateProperty(propertable, label, this.getText()));
    }

    private void updateProperty(Propertable prop, String propLabel, String value) {
        if (isValidValue(propLabel, value)) {
            prop.getPropertyMap().put(propLabel, value); // Ask immediately if new value if valid, not later
            myCurrentValue = value;
        }
        else {
            this.setText(myCurrentValue); // Revert to old value if validity check fails
        }
    }

    /**
     * Check validity of new value from based on regex syntax from properties file, whether or not label exists in LabelManager already
     */
    private boolean isValidValue(String key, String newVal) {
        if (key.equals("Label") && myLabelManager.getLabels("Label").contains(newVal))
            return false;
        return Utility.isValidValue(key, newVal, PROP_SYNTAX);
    }
}
