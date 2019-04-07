package engine.internal.systems;

import engine.external.IEvent;
import engine.external.component.Component;
import java.util.Collection;
import java.util.List;


/**
 * Checks all Events every cycle
 */
public class EventHandlerSystem extends System {
    List<IEvent> events;

    //TODO: feed Constructor a list of Events, not Compone  nts
    public EventHandlerSystem(Collection<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
    }

    @Override
    protected void run() {

        //TODO: implement this

        for (IEvent e : events) {
            //e.execute;
        }
    }
}
