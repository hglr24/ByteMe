package ui.control;

import ui.TestEntity;
import ui.UIException;

public interface ControlProperty {

    void populateValue(String newValue);

    void setAction(TestEntity entity, String action) throws UIException;
}
