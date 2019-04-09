package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.Engine;

import java.util.Collection;

public class CleanupSystem extends VoogaSystem {

    public CleanupSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {
        for(Entity entity:this.getEntities()){
            if((Boolean)entity.getComponent(DESTROY_COMPONENT_CLASS).getValue()){
                this.myEngine.getEntities().remove(entity);
            }
        }
    }
}
