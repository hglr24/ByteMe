package actions;

import engine.external.Entity;
import engine.external.component.PositionComponent;
import javafx.geometry.Point3D;

import java.util.function.Consumer;

public class PositionAction extends Action {

    public PositionAction(){ super();}

    public Consumer<Entity> makePositionAction(double xPosition,double yPosition) {
        return makeValueAction(new Point3D(xPosition, yPosition, 0), PositionComponent.class);
    }
}
