package engine.internal.systems;


import engine.external.IEventEngine;
import engine.external.component.Component;
import engine.external.Engine;

import java.util.ArrayList;


import java.util.Collection;


/**
 * Checks all Events every cycle
 */
public class EventHandlerSystem extends VoogaSystem {

    Collection<IEventEngine> myEvents;

    public EventHandlerSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine, Collection<IEventEngine> events) {
        super(requiredComponents, engine);
        myEvents = events;
    }

    @Override
    protected void run() {
        for (IEventEngine e : myEvents) {
            e.execute(new ArrayList<>(this.getEntities()), this.getKeyCodes());
        }
    }
}
