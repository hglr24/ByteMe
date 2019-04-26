package engine.external.actions;

import engine.external.component.DestroyComponent;
import engine.external.component.ProgressionComponent;

public class ProgressionAction extends BooleanAction {
    public ProgressionAction(Boolean destroy){
        setAction(destroy, ProgressionComponent.class);
    }
}