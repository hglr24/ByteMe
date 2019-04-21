package engine.external.actions;


import engine.external.component.HealthComponent;


public class HealthAction extends NumericAction {
    public HealthAction(ModifyType type, Double health){
        setAction(type, health, HealthComponent.class);
    }

}
