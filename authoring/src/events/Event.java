package events;


import engine.external.Entity;
import engine.external.IEvent;
import engine.external.component.NameComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Events are intended for creating/handling custom logic that is specific to a game, and cannot be reasonably anticipated by the engine beforehand
 *
 * @author Lucas Liu
 * @author Feroze Mohideen
 */
public abstract class Event implements IEvent {
    private List<Consumer<Entity>> actions;
    private List<Condition> conditions;
    private String myType;

    //this is the name that the event is attached to
    public Event(String name) {
        myType = name;
    }

    @Override
    public void execute(List<Entity> entities) {
        List<Entity> filtered_entities = filter(entities);
        for (Entity e : filtered_entities) {
            if (conditionsMet(e)) {
                executeActions(e);
            }
        }
    }

    private List<Entity> filter(List<Entity> entities) {
        List<Entity> filtered_entities = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getComponent(NameComponent.class).getValue().equals(myType)) {
                filtered_entities.add(entity);
            }
        }
        return filtered_entities;
    }

    private boolean conditionsMet(Entity entity) {
        //TODO: make this sexier
        for (Condition condition: conditions) {
            if (!condition.getPredicate().test(entity)) {
                return false;
            }
        }
        return true;
    }

    private void executeActions(Entity entity) {
        //TODO: implement
        actions.forEach(action -> action.accept(entity));
    }
}
