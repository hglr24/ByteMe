package engine.external.conditions;

import engine.external.Entity;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * @author Lucas Liu
 * @author Anna Darwish
 * @author Dima Fayyad
 * Condition for Event that checks for Component String to match the target string
 */
public class StringEqualToCondition extends Condition {
    private String myComponentName;
    private String myValue;
    private static final String DISPLAY = " Is ";
    private static final String COMPONENT = "Component";
    public StringEqualToCondition(Class<? extends Component> component, String value) {
        setPredicate((Predicate<Entity> & Serializable) entity -> (entity.getComponent(component).getValue()).equals(value));
        myComponentName = component.getSimpleName();
        myComponentClass = component;
        myValue = value;
    }

    @Override
    public String toString(){
        return myComponentName.replaceAll(COMPONENT,"") + DISPLAY + myValue;
    }

    public Class<? extends Component> getComponentClass() {
        return myComponentClass;
    }

    public String getValue() {
        return myValue;
    }
}
