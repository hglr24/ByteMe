package conditions;

import engine.external.component.Component;

public class LessThanCondition extends Condition {
    private String myComponentName;
    private Double myValue;
    public LessThanCondition(Class<? extends Component> component, Double value) {
        setPredicate(entity -> (Double) entity.getComponent(component).getValue() < value);
        myComponentName = component.getSimpleName();
        myValue = value;
    }
    @Override
    public String toString(){
        return myComponentName + " Less Than " + myValue;
    }
}
