package engine.external.actions;

import engine.external.Entity;
import engine.external.component.SpawnEntityComponent;

public class AddEntityAction extends AddComponentAction {
    public AddEntityAction(Entity entity){
        SpawnEntityComponent entityComponent = new SpawnEntityComponent(entity);
        setActionWithTimer(entityComponent);
        myComponentClass = SpawnEntityComponent.class;
    }

}
