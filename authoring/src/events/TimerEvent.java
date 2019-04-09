package events;


import conditions.EqualToCondition;
import engine.external.component.TimerComponent;

public class TimerEvent extends Event {
    public TimerEvent(String name, Double timerValue) {
        super(name);

        makeTimerCondition(timerValue);
    }

    private void makeTimerCondition(Double timerValue){
        EqualToCondition timerCondition = new EqualToCondition(TimerComponent.class, timerValue);
        addConditions(timerCondition);
    }

}
