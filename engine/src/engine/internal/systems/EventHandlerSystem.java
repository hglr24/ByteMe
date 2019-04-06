package engine.internal.systems;

import engine.external.Event;
import engine.external.component.Component;

import java.util.Collection;
import java.util.List;


/**
 * Checks all Events every cycle
 */
public class EventHandlerSystem extends System {
    List<Event> events;

    //TODO: feed Constructor a list of Events, not Components
    public EventHandlerSystem(Collection<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
    }

    @Override
    protected void run() {
        for (Event e : events) {
            e.execute();
        }
    }
}
