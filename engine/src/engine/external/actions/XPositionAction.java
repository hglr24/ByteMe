package engine.external.actions;

import engine.external.component.XPositionComponent;

public class XPositionAction extends NumericAction {
    public XPositionAction(ModifyType type, Double xPosition){
        setAction(type, xPosition, XPositionComponent.class);
    }
}
