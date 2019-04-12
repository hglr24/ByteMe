package actions;

import engine.external.component.VisibilityComponent;

public class VisibilityAction extends BooleanAction {
    public VisibilityAction(Boolean isVisible){
        setAction(isVisible, VisibilityComponent.class);
    }
}
