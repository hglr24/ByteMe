package engine.external.actions;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.TimerComponent;

import java.io.Serializable;
import java.util.function.Consumer;

public abstract class AddComponentAction<T> extends Action<T>{
    private static final double timerValue = 10.0;

    protected void setAbsoluteAction(Component component) {
        setAction((Consumer<Entity> & Serializable) (entity) -> {
            entity.addComponent(component);
        });
    }

    protected void setActionWithTimer(Component component){
        setAction((Consumer<Entity> & Serializable) (entity) -> {
            if(!entity.hasComponents(TimerComponent.class)){
                entity.addComponent(new TimerComponent(timerValue));
                entity.addComponent(component);
            } else if(entity.getComponent(TimerComponent.class).getValue().equals(0.0)){
                entity.getComponent(TimerComponent.class).resetToOriginal();
                entity.addComponent(component);
            }
        });
    }
}
