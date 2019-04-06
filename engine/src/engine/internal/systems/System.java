package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;

import java.util.ArrayList;
import java.util.Collection;

public abstract class System {
    private Collection<Class<? extends Component>> myRequiredComponents;
    private Collection<Entity> myEntities;

    public System(Collection<Class<? extends Component>> requiredComponents) {
        myRequiredComponents = requiredComponents;
    }

    public void update(Collection<Entity> entities) {
        myEntities = new ArrayList<>();
        for (Entity e: entities) {
            if (filter(e)) {
                myEntities.add(e);
            }
        }
        run();
    }



    protected boolean filter(Entity e) {
        return e.hasComponents(myRequiredComponents);
    }

    protected abstract void run();

    protected Collection<Entity> getEntities() {
        return myEntities;
    }

}
