package events;

import engine.external.component.Component;

public class GreaterThanCondition extends Condition<Double> {
    public GreaterThanCondition(Class<? extends Component> component, Double value) {
        setPredicate(entity -> (Double) entity.getComponent(component).getValue() > value);
    }
}
