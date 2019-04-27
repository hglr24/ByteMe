package engine.external.actions;

import engine.external.component.DirectionComponent;

public class DirectionAction extends NumericAction {
    public DirectionAction(ModifyType type, Double direction) {
        setAction(type, direction, DirectionComponent.class);
    }
}