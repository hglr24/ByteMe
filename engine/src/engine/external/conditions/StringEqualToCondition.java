package engine.external.conditions;

import engine.external.Entity;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.function.Predicate;

public class StringEqualToCondition extends Condition {
    private String myComponentName;
    private String myValue;
    public StringEqualToCondition(Class<? extends Component> component, String value) {
        setPredicate((Predicate<Entity> & Serializable) entity -> (entity.getComponent(component).getValue()).equals(value));
        myComponentName = component.getSimpleName();
        myValue = value;
    }
    @Override
    public String toString(){
        return myComponentName + " Is " + myValue;
    }
}
