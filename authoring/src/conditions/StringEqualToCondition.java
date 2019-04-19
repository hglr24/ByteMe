package conditions;

import engine.external.component.Component;

public class StringEqualToCondition extends Condition {
    private String myComponentName;
    private String myValue;
    public StringEqualToCondition(Class<? extends Component> component, String value) {
        setPredicate(entity -> (entity.getComponent(component).getValue()).equals(value));
        myComponentName = component.getSimpleName();
        myValue = value;
    }
    @Override
    public String toString(){
        return myComponentName + " Is " + myValue;
    }
}
