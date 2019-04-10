package runner.Testing.actions;

import engine.external.component.YPositionComponent;

public class YPositionAction extends NumericAction {
    public YPositionAction(ModifyType type, Double yPosition){
        setAction(type, yPosition, YPositionComponent.class);
    }
}
