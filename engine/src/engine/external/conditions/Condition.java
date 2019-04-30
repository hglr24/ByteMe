package engine.external.conditions;

import engine.external.Entity;
import engine.external.component.Component;

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
    protected Class<? extends Component> myComponentClass;


    public void checkComponents(Entity entity) {
        if (!entity.hasComponents(myComponentClass)) {
            //System.out.println("Condition adding missing component: " + myComponentClass);
            try {
                entity.addComponent(myComponentClass.getConstructor().newInstance());
            } catch (Exception e) {
                //Do nothing
                //System.out.println("Could not instantiate new constructor");
            }
        }
    }

    protected void setPredicate(Predicate predicate) {
        myPredicate = predicate;
    }

    public Predicate getPredicate() {
        return myPredicate;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Class<? extends Component> getMyComponentClass() {
        return myComponentClass;
    }
}
