package engine.external.conditions;

import engine.external.Entity;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * @author Lucas Liu
 * @author Anna Darwish
 * <p>
 * Condition for Event that checks for equality to a given double value
 */
public class EqualToCondition extends Condition {
    private String myComponentName;
    private Double myValue;
    private static final String DISPLAY = " Equals ";
    public EqualToCondition(Class<? extends Component> component, Double value) {
        setPredicate((Predicate<Entity> & Serializable) entity -> (Double) entity.getComponent(component).getValue() == value);
        myComponentName = component.getSimpleName();
        myValue = value;
    }

    @Override
    public String toString(){
        return myComponentName + DISPLAY + myValue;
    }
}
