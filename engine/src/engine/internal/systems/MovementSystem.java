package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.PositionComponent;
import engine.external.component.VelocityComponent;
import javafx.geometry.Point3D;

import java.util.Collection;

public class MovementSystem extends System {
    private final Class<? extends Component> POSITION_COMPONENT_CLASS = PositionComponent.class;
    private final Class<? extends Component> VELOCITY_COMPONENT_CLASS = VelocityComponent.class;

    public MovementSystem(Collection<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
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
