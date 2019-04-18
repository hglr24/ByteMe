package conditions;

import engine.external.Entity;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.function.Predicate;

public class GreaterThanCondition extends Condition {
    public GreaterThanCondition(Class<? extends Component> component, Double value) {
        setPredicate((Predicate<Entity> & Serializable) entity -> (Double) entity.getComponent(component).getValue() > value);
    }
}
