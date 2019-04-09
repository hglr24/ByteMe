package conditions;

import engine.external.component.Component;

public class StringEqualToCondition extends Condition<String> {
    public StringEqualToCondition(Class<? extends Component> component, String value) {
        setPredicate(entity -> (entity.getComponent(component).getValue()).equals(value));
    }
}
