package runner.Testing.actions;

import engine.external.Entity;
import engine.external.component.Component;

import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * An Action is a wrapper class for a lambda function that takes in a value and a component, and
 * either sets the component value to that value, or scales/displaces the component's existing
 * value by that value. There are 3 types of actions: NumericAction, BooleanAction, and StringAction,
 * each of which handle components of the different types.
 *
 * @param <T> Abstract type handled by an action, e.g. NumericAction<Double> can be used to alter the XPositionComponent
 *
 * @author Feroze
 * @author Lucas
 * @author Dima
 */
public abstract class Action<T> {
    private final ResourceBundle ACTION_TYPES_RESOURCES = ResourceBundle.getBundle("Actions");
    private Consumer<Entity> myAction;

    /**
     * Sets the value of a component to a new value.
     * @param newValue new value for the component
     * @param componentClass class that specifies the component type
     */
    protected void setAbsoluteAction(T newValue, Class<? extends Component<T>> componentClass) {
        setAction((entity) -> {
            Component component = entity.getComponent(componentClass);
            component.setValue(newValue);
        });
    }

    /**
     * Sets the lambda of this action
     * @param action a lambda
     */
    protected void setAction(Consumer<Entity> action) {
        myAction = action;
    }

    /**
     * Returns the lambda associated with this action
     * @return lambda
     */
    public Consumer<Entity> getAction() {
        return myAction;
    }

    /**
     * Returns the complete set of all available actions
     * @return Collection<String> representing set of all available actions
     */
    public Collection<String> getAllActions(){ return ACTION_TYPES_RESOURCES.keySet();}
}
