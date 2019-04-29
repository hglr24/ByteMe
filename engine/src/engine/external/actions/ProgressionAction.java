package engine.external.actions;

import engine.external.component.ProgressionComponent;

public class ProgressionAction extends AddComponentAction<Boolean> {
    public ProgressionAction(Boolean progress){
        setAbsoluteAction(new ProgressionComponent(progress));
        myComponentClass = ProgressionComponent.class;
    }


}