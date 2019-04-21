package engine.external.actions;

import engine.external.component.XAccelerationComponent;

public class XAccelerationAction extends NumericAction  {
    public XAccelerationAction(ModifyType type, Double xAcceleration){ setAction(type, xAcceleration, XAccelerationComponent.class); }
}
