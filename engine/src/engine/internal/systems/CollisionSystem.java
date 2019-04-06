package engine.internal.systems;

import engine.external.component.Component;

import java.util.Collection;

public class CollisionSystem extends System {
    public CollisionSystem(Collection<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
    }

    @Override
    protected void run() {
        //TODO: implement this
    }
}
