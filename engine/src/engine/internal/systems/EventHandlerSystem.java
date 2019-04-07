package engine.internal.systems;


import engine.external.IEvent;
import engine.external.component.Component;
import engine.internal.Engine;

import java.util.ArrayList;


import java.util.Collection;



/**
 * Checks all Events every cycle
 */
public class EventHandlerSystem extends System {
    Collection<IEvent> myEvents;
    
    public EventHandlerSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine, Collection<IEvent> events) {
        super(requiredComponents, engine);
        myEvents = events;
    }

    @Override
    protected void run() {
        for (IEvent e : myEvents) {
            e.execute(new ArrayList<>(this.getEntities()));

        }
    }
}
