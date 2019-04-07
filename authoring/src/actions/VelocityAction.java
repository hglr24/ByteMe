package actions;

import engine.external.Entity;
import engine.external.component.VelocityComponent;
import javafx.geometry.Point3D;

import java.util.function.Consumer;

public class VelocityAction extends Action {

    public VelocityAction(){ super();}

    public Consumer<Entity> makeVelocityAction(double xVelocity,double yVelocity) {
        return (Consumer<Entity>) (entity) -> {
            VelocityComponent component = (VelocityComponent) entity.getComponent(VelocityComponent.class);
            component.setValue(new Point3D(xVelocity,yVelocity,component.getValue().getZ()));
        };
    }
}
