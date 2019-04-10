package runner.Testing.actions;

import engine.external.component.GravityComponent;

public class GravityAction extends NumericAction {
    public GravityAction(ModifyType type, Double gravity){
        setAction(type, gravity, GravityComponent.class);
    }
}
