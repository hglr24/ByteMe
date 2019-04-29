package engine.external.actions;

import engine.external.component.Component;

/**
 * A BooleanAction is a variant of an Action, and can only change the boolean value of a
 * component to the new boolean value.
 *
 * @author Lucas
 * @author Feroze
 */

public abstract class BooleanAction extends Action<Boolean> {
    private Boolean myValue;
    private static final String VALID = "DO ";
    private static final String INVALID = "DON'T ";
    public void setAction(Boolean newValue, Class<? extends Component<Boolean>> componentClass) {
        super.setAbsoluteAction(newValue, componentClass);
        myValue = newValue;
        myComponentClass = componentClass;
    }

    public String toString() {
        String actionMessage = "";
        if (myValue) {
            actionMessage += VALID;
        } else {
            actionMessage += INVALID;
        }
        actionMessage += getMyComponentClass().getSimpleName();
        return actionMessage;
    }
}
