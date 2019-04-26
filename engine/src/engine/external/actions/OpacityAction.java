package engine.external.actions;


import engine.external.component.OpacityComponent;

public class OpacityAction extends NumericAction {
    public OpacityAction(ModifyType type, Double opacity){
        setAction(type, opacity, OpacityComponent.class);
    }

}