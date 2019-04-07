package actions;

import engine.external.Entity;
import engine.external.component.DirectionComponent;
import engine.external.component.SpriteComponent;

import java.util.function.Consumer;

public class DirectionAction extends Action {
   public DirectionAction(){ super();}

    public Consumer<Entity> makeDirectionAction(Integer direction) {
        return makeValueAction(direction, DirectionComponent.class);
    }
}