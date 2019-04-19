package engine.external.actions;

import engine.external.component.Component;

/**
 * A BooleanAction is a variant of an Action, and can only change the boolean value of a
 * component to the new boolean value.
 *
 * @author Feroze
 */
public abstract class BooleanAction extends Action<Boolean> {
    Boolean myValue;
    Class<? extends Component<Boolean>> myComponentClass;

    public void setAction(Boolean newValue, Class<? extends Component<Boolean>> componentClass) {
        super.setAbsoluteAction(newValue, componentClass);
        myValue = newValue;
        myComponentClass = componentClass;
    }

    public String toString() {
        String actionMessage = "";
        if (myValue) {
            actionMessage += "DO ";
        } else {
            actionMessage += "DON'T ";
        }
        actionMessage += myComponentClass.getSimpleName();
        return actionMessage;
    }
}
