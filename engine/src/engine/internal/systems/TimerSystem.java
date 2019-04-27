package engine.internal.systems;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.TimerComponent;

import java.util.Collection;

public class TimerSystem extends VoogaSystem {
    private static final double timerStep = -1.0;
    private static final double timerZero = 0.0;

    public TimerSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }
    @Override
    protected void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(TIMER_COMPONENT_CLASS) &&  !entity.getComponent(TimerComponent.class).getValue().equals(timerZero)){
                Component timerComponent = entity.getComponent(TimerComponent.class);
                timerComponent.setValue((double) timerComponent.getValue() + timerStep);
            }
        }
    }
}
