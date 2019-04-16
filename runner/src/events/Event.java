package events;


import actions.Action;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import conditions.Condition;
import engine.external.Entity;
import engine.external.IEventEngine;
import engine.external.component.NameComponent;
import javafx.scene.input.KeyCode;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * Events are intended for creating/handling custom logic that is specific to a game, and cannot be reasonably anticipated by the engine beforehand
 *
 * @author Lucas Liu
 * @author Feroze Mohideen
 */
public class Event implements IEventEngine, IEventAuthoring {
    @XStreamOmitField
    private transient ResourceBundle EVENT_TYPES_RESOURCES;
    @XStreamImplicit
    private List<Action> actions = new ArrayList<>();
    @XStreamImplicit
    private List<Condition> conditions = new ArrayList<>();
    private String myType;
    @XStreamImplicit
    private Set<KeyCode> myInputs = new HashSet<>();


    /**
     * An Event is created using the name of the type of entity that this event will apply to
     * e.g. Event e = new Event("Mario") if a user has created an entity/group called "Mario"
     *
     * @param name
     */
    public Event(String name) {
        myType = name;
        init();
    }

    private void init() {
        EVENT_TYPES_RESOURCES = ResourceBundle.getBundle("Events");
        myInputs = new HashSet<>();
//        actions = new ArrayList<>();
//        conditions = new ArrayList<>();
    }

    //need to make this method take in keycode inputs as well
    @Override
    public void execute(List<Entity> entities, Collection<KeyCode> inputs) {
        if (!inputs.containsAll(myInputs)) {
            return;
        }
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
        try {
//            return conditions.stream().allMatch(condition -> condition.getPredicate().test(entity));
            return true;
        }catch(Exception e){
            e.printStackTrace(); //TODO find exact exceptions to catch
            return false;
        }
    }

    private void executeActions(Entity entity) {
        actions.forEach((Consumer<Action> & Serializable) action -> action.getAction().accept(entity));
    }

    public void addActions(List<Action> actionsToAdd) {
        actions.addAll(actionsToAdd);
    }

    public void addActions(Action action) {
        addActions(Arrays.asList(action));
    }

    public void addConditions(List<Condition> conditionsToAdd) {
        conditions.addAll(conditionsToAdd);
    }

    public void addConditions(Condition condition) {
        addConditions(Arrays.asList(condition));
    }

    public void setConditions(List<Condition> newSetOfConditions) {
        conditions = newSetOfConditions;
    }

    public void removeConditions(List<Condition> conditionsToRemove) {
        conditions.removeAll(conditionsToRemove);
    }

    public void setActions(List<Action> newSetOfActions) {
        actions = newSetOfActions;
    }

    public void removeActions(List<Action> actionsToRemove) {
        actions.removeAll(actionsToRemove);
    }


    public Collection<String> getAllEvents() {
        return EVENT_TYPES_RESOURCES.keySet();
    }

    @Override
    public void setInputs(Set<KeyCode> inputs) {
        myInputs = inputs;
    }

    @Override
    public void addInputs(Set<KeyCode> inputsToAdd) {
        myInputs.addAll(inputsToAdd);
    }

    @Override
    public void addInputs(KeyCode inputToAdd) {
        myInputs.add(inputToAdd);
    }

    @Override
    public void removeInputs(Set<KeyCode> inputsToRemove) {
        myInputs.remove(inputsToRemove);
    }


}

