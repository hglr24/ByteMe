package ui.control;

import ui.Propertable;
import ui.UIException;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

/**
 * @author Harry Ross
 */
public interface ControlProperty {

    void populateValue(Propertable prop, Enum type, String newValue, LabelManager labels);

    void setAction(ObjectManager manager, Propertable propertable, Enum label, String action) throws UIException;
}
