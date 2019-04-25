package engine.external.actions;

import engine.external.component.YAccelerationComponent;

public class YAccelerationAction extends NumericAction {
    public YAccelerationAction(ModifyType type, Double yAcceleration) {
        setAction(type, yAcceleration, YAccelerationComponent.class);
    }
}
