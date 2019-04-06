package events;

import engine.external.component.Component;

public class LessThanCondition extends Condition<Double> {
    public LessThanCondition(Class<? extends Component> component, Double value) {
        setPredicate(entity -> (Double) entity.getComponent(component).getValue() < value);
    }
}
