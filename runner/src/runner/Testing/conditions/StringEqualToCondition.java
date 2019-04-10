package runner.Testing.conditions;

import engine.external.component.Component;

public class StringEqualToCondition extends Condition {
    public StringEqualToCondition(Class<? extends Component> component, String value) {
        setPredicate(entity -> (entity.getComponent(component).getValue()).equals(value));
    }
}
