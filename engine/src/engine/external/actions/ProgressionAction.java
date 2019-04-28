package engine.external.actions;

import engine.external.component.ProgressionComponent;

public class ProgressionAction extends BooleanAction {
    public ProgressionAction(Boolean progress){
        setAction(progress, ProgressionComponent.class);
    }
}