package engine.external.conditions;

import java.util.function.Predicate;

/**
 * A Condition object packages a predicate. Conditions are subclassed based on the operator that the condition is
 * evaluated with (e.g. greater than, less than, or equal to) and the value that is being checked (Double vs String
 * for example). See subclasses for details.
 *
 * @author Lucas Liu
 * @author Feroze Mohideen
 */
public abstract class Condition {
    private Predicate myPredicate;

    protected void setPredicate(Predicate predicate) {
        myPredicate = predicate;
    }

    public Predicate getPredicate() {
        return myPredicate;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
