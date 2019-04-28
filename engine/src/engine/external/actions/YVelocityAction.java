package engine.external.actions;

import engine.external.component.YVelocityComponent;

public class YVelocityAction extends NumericAction {
    public YVelocityAction(ModifyType type, Double yVelocity) {
        setAction(type, yVelocity, YVelocityComponent.class);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}
