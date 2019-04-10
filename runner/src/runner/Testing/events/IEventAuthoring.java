package runner.Testing.events;

import runner.Testing.conditions.*;
import runner.Testing.actions.*;
import javafx.scene.input.KeyCode;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
     * Removes a set of actions associated with this event
     * @param actionsToRemove
     */
    void removeActions(List<Action> actionsToRemove);

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

    /**
     * Removes a set of conditions from the list of conditions associated with this event.
     * @param conditionsToRemove
     */
    void removeConditions(List<Condition> conditionsToRemove);

    /**
     * Returns the set of all available events
     * @return set of all available events
     */
    Collection<String> getAllEvents();



    void setInputs(Set<KeyCode> inputs);

    /**
     * Allows you to add a list of INPUTS to the current list of inputs
     * associated with this event. The next method allows you to add a single input.
     * @param
     */
    void addInputs(Set<KeyCode> inputsToAdd);

    void addInputs(KeyCode inputToAdd);

    /**
     * Removes a set of required inputs from the list  associated with this event.
     * @param inputsToRemove
     */
    void removeInputs(Set<KeyCode> inputsToRemove);


}
