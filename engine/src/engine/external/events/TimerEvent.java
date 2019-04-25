package engine.external.events;


import engine.external.conditions.EqualToCondition;
import engine.external.component.TimerComponent;

/**
 * @author Lucas Liu
 * @author Dima Fayyad
 */
public class TimerEvent extends Event {

    /**
     * Class constructor. Makes a new timer event given name of the entity and the Double timerValue
     *
     * @param name       the String name of the entity that the timerEvent will affect
     * @param timerValue Double representing the time at which engine.external.actions associated with time will occur
     */
    public TimerEvent(String name, Double timerValue) {
        super(name);
        makeTimerCondition(timerValue);
    }

    /**
     * Class constructor. Makes a new timer event given name of the entity and the String timerValue
     *
     * @param name       the String name of the entity that the timerEvent will affect
     * @param timerValue String representing the time at which engine.external.actions associated with time will occur
     */
    public TimerEvent(String name, String timerValue) {
        super(name);
        Double value = Double.parseDouble(timerValue);
        makeTimerCondition(value);
    }

    private void makeTimerCondition(Double timerValue) {
        EqualToCondition timerCondition = new EqualToCondition(TimerComponent.class, timerValue);
        addConditions(timerCondition);
    }

    @Override
    public String toString() {
        return "TIMER EQUALS ";
    }

}
