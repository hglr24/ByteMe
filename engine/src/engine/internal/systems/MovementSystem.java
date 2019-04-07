package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.PositionComponent;
import engine.external.component.VelocityComponent;
import engine.internal.Engine;
import javafx.geometry.Point3D;

import java.util.Collection;

public class MovementSystem extends System {

    public MovementSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {
        for (Entity e: getEntities()) {
            PositionComponent positionComponent = (PositionComponent) e.getComponent(POSITION_COMPONENT_CLASS);
            VelocityComponent velocityComponent = (VelocityComponent) e.getComponent(VELOCITY_COMPONENT_CLASS);

            double x = positionComponent.getValue().getX() + velocityComponent.getValue().getX();
            double y = positionComponent.getValue().getY() + velocityComponent.getValue().getY();
            double z = positionComponent.getValue().getZ();

            positionComponent.setValue(new Point3D(x, y, z));
        }
    }
}
