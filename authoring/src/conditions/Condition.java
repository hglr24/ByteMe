package conditions;

import engine.external.Entity;

import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * A Condition object packages a predicate. Conditions are subclassed based on the operator that the condition is
 * evaluated with (e.g. greater than, less than, or equal to) and the value that is being checked (Double vs String
 * for example). See subclasses for details.
 * 
 * @author Lucas Liu
 * @author Feroze Mohideen
 */
public abstract class Condition<T> {
    private final ResourceBundle CONDITION_TYPES_RESOURCES = ResourceBundle.getBundle("Conditions");

    private Predicate<Entity> myPredicate;

    protected void setPredicate(Predicate<Entity> predicate) {
        myPredicate = predicate;
    }

    public Predicate<Entity> getPredicate() {
        return myPredicate;
    }

    /**
     * Returns a collection of all available conditions for the user to add to any event
     * @return Collection<String>
     */
    public Collection<String> getAllConditions(){ return CONDITION_TYPES_RESOURCES.keySet();}
}
