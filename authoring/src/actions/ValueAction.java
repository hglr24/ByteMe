package actions;

import engine.external.Entity;
import engine.external.component.ValueComponent;

import java.util.function.Consumer;

public class ValueAction extends Action {

    public ValueAction(){ super();}

    public Consumer<Entity> makeValueAction(double newValue) {
        return makeValueAction(newValue, ValueComponent.class);
    }

}
