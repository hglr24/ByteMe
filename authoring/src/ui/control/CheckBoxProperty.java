package ui.control;

import javafx.scene.control.CheckBox;
import ui.EntityField;
import ui.Propertable;
import ui.UIException;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

public class CheckBoxProperty extends CheckBox implements ControlProperty {

    @Override
    public void populateValue(Propertable prop, Enum type, String newValue, LabelManager labels) {
        this.setSelected(Boolean.parseBoolean(prop.getPropertyMap().get(type)));
    }

    @Override
    public void setAction(ObjectManager manager, Propertable propertable, Enum label, String action) throws UIException {
        this.setOnAction(event -> {
            if (label.equals(EntityField.CAMERA))
                manager.flushCameraAssignment(propertable);
            propertable.getPropertyMap().put(label,
                    Boolean.toString(!Boolean.parseBoolean(propertable.getPropertyMap().get(label))));
        });
    }
}
