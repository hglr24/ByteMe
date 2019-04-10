package runner.Testing.actions;

import engine.external.component.WidthComponent;

public class WidthAction extends NumericAction {
    public WidthAction(ModifyType type, Double width){
        setAction(type, width, WidthComponent.class);
    }
}
