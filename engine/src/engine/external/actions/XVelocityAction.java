package engine.external.actions;

import engine.external.component.XVelocityComponent;

public class XVelocityAction extends NumericAction {
    public XVelocityAction(ModifyType type, Double xVelocity){
        setAction(type, xVelocity, XVelocityComponent.class);
    }
}
