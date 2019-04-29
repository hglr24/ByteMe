package engine.external.actions;

import engine.external.Entity;
import engine.external.component.SpawnEntityComponent;

public class AddEntityInstantAction extends AddComponentAction {
    public AddEntityInstantAction(Entity entity){
        SpawnEntityComponent entityComponent = new SpawnEntityComponent(entity);
        setAbsoluteAction(entityComponent);
        myComponentClass = SpawnEntityComponent.class;
    }

}
