package engine.internal.systems;

import engine.external.component.Component;
import engine.internal.Engine;

import java.util.Collection;

public class HealthSystem extends System{
    public HealthSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {
        //TODO: implement this
    }
}
