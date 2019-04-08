package events;

import actions.Action;
import conditions.Condition;

import java.util.Collection;
import java.util.List;

/**
 * This is the API that Authoring can refer to when constructing Events
 *
 * @author this API brought to u by da engine gang
 */
public interface IEventAuthoring {
    /**
     * This method sets the list of ACTIONS associated with this event to
     * a new set.
     */
    void setActions(List<Action> newSetOfActions);

    /**
     * This method adds a list of ACTIONS to the current list of actions
     * associated with this event. The next method allows you to add a single
     * ACTION to the list.
     * @param actionsToAdd
     */
    void addActions(List<Action> actionsToAdd);

    void addActions(Action action);

    /**
     * This method allows you to set the CONDITIONS associated with an event to
     * a new list of conditions.
     * @param newSetOfConditions new list of conditions to set
     */
    void setConditions(List<Condition> newSetOfConditions);

    /**
     * Allows you to add a list of CONDITIONS to the current list of conditions
     * associated with this event. The next method allows you to add a single condition.
     * @param conditionsToAdd
     */
    void addConditions(List<Condition> conditionsToAdd);

    void addConditions(Condition condition);

    Collection<String> getAllEvents();





}
