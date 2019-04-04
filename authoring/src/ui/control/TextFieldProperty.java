package ui.control;

import javafx.scene.control.TextField;
import ui.TestEntity;

import java.lang.reflect.Method;

public class TextFieldProperty extends TextField implements ControlProperty {

    public TextFieldProperty() {

    }

    @Override
    public void populateValue(String newVal) {
        this.setText(newVal);
    }

    @Override
    public void setAction(TestEntity entity, String action) {
        try {
            Method m = TestEntity.class.getDeclaredMethod(action);
            //this.setOnAction((oldVal, newVal) -> m.invoke(entity, ));
        } catch (Exception e) {

        }
    }
}
