package engine.external.events;

import engine.external.actions.Action;
import engine.external.conditions.Condition;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Set;

/**
 * This is the API that Authoring can refer to when constructing Events
 *
 * @author Lucas Liu
 * @author Dima Fayyad
 * @author Feroze Mohideen
 */
public interface IEventAuthoring {
    /**
     * This method sets the list of ACTIONS associated with this event to
     * a new set.
     */
    void setActions(List<Action> newSetOfActions);

    /**
     * This method adds a list of ACTIONS to the current list of engine.external.actions
     * associated with this event. The next method allows you to add a single
     * ACTION to the list.
     *
     * @param actionsToAdd
     */
    void addActions(List<Action> actionsToAdd);

    void addActions(Action action);

    /**
     * Removes a set of engine.external.actions associated with this event
     *
     * @param actionsToRemove
     */
    void removeActions(List<Action> actionsToRemove);

    /**
     * This method allows you to set the CONDITIONS associated with an event to
     * a new list of engine.external.conditions.
     *
     * @param newSetOfConditions new list of engine.external.conditions to set
     */
    void setConditions(List<Condition> newSetOfConditions);

    /**
     * Allows you to add a list of CONDITIONS to the current list of engine.external.conditions
     * associated with this event. The next method allows you to add a single condition.
     *
     * @param conditionsToAdd
     */
    void addConditions(List<Condition> conditionsToAdd);

    void addConditions(Condition condition);

    /**
     * Removes a set of engine.external.conditions from the list of engine.external.conditions associated with this event.
     *
     * @param conditionsToRemove
     */
    void removeConditions(List<Condition> conditionsToRemove);

    void setInputs(Set<KeyCode> inputs);

    /**
     * Allows you to add a list of INPUTS to the current list of inputs
     * associated with this event. The next method allows you to add a single input.
     *
     * @param
     */
    void addInputs(Set<KeyCode> inputsToAdd);

    void addInputs(KeyCode inputToAdd);

    /**
     * Removes a set of required inputs from the list  associated with this event.
     *
     * @param inputsToRemove
     */
    void removeInputs(Set<KeyCode> inputsToRemove);


}
