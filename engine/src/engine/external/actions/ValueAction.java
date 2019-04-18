package engine.external.actions;

import engine.external.component.ValueComponent;

public class ValueAction extends NumericAction {
    public ValueAction(ModifyType type, Double newValue){
        setAction(type, newValue, ValueComponent.class);
    }

}
