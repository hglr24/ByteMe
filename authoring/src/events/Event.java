package events;


import actions.Action;
import conditions.Condition;
import engine.external.Entity;
import engine.external.IEventEngine;
import engine.external.component.NameComponent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Events are intended for creating/handling custom logic that is specific to a game, and cannot be reasonably anticipated by the engine beforehand
 *
 * @author Lucas Liu
 * @author Feroze Mohideen
 */
public class Event implements IEventEngine, IEventAuthoring {
    private List<Action> actions = new ArrayList<>();
    private List<Condition> conditions = new ArrayList<>();
    private List<KeyCode> inputs = new ArrayList<>();
    private String myType;

    /**
     * An Event is created using the name of the type of entity that this event will apply to
     * e.g. Event e = new Event("Mario") if a user has created an entity/group called "Mario"
     * @param name
     */
    public Event(String name) {
        myType = name;
    }

    //need to make this method take in keycode inputs as well
    @Override
    public void execute(List<Entity> entities, Collection<KeyCode> inputs) {
        //TODO: do something with the inputs
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
        actions.forEach(action -> action.getAction().accept(entity));
    }

    public void addActions(List<Action> actionsToAdd){
        actions.addAll(actionsToAdd);
    }

    public void addActions(Action action) { addActions(Arrays.asList(action));}

    public void addConditions(List<Condition> conditionsToAdd){
        conditions.addAll(conditionsToAdd);
    }

    public void addConditions(Condition condition) { addConditions(Arrays.asList(condition));}

    public void setConditions(List<Condition> newSetOfConditions) { conditions = newSetOfConditions;}

    public void setActions(List<Action> newSetOfActions){
        actions = newSetOfActions;
    }

    public void removeActions(List<Action> actionsToRemove){
        actions.removeAll(actionsToRemove);
    }

    public Collection<String> getAllEvents(){
        //TODO: implement
        return new ArrayList<>();
    };

}
