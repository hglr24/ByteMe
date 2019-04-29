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
    private static final String COMPONENT = "Component";
    public EqualToCondition(Class<? extends Component> component, Double value) {
        setPredicate((Predicate<Entity> & Serializable) entity -> ((Double) entity.getComponent(component).getValue()).equals(value));
        myComponentName = component.getSimpleName();
        myValue = value;
        myComponentClass = component;
    }

    @Override
    public String toString(){
        return myComponentName.replaceAll(COMPONENT,"") + DISPLAY + myValue;
    }
}
