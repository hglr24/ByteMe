package conditions;

import engine.external.component.Component;

public class LessThanCondition extends Condition {
    public LessThanCondition(Class<? extends Component> component, Double value) {
        setPredicate(entity -> (Double) entity.getComponent(component).getValue() < value);
    }
}
