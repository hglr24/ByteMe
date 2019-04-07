package actions;

import engine.external.Entity;
import engine.external.component.TimerComponent;

import java.util.function.Consumer;

public class TimerAction extends Action {
    public TimerAction(){ super();}

    public Consumer<Entity> makeTimeAction(Integer time) {
        return makeValueAction(time, TimerComponent.class);
    }
}
