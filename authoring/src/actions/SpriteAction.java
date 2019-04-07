package actions;

import engine.external.Entity;
import engine.external.component.SpriteComponent;

import java.util.function.Consumer;

public class SpriteAction extends Action {
    public SpriteAction(){ super();}

    public Consumer<Entity> makeSpriteAction(String pathname) {
        return makeValueAction(pathname, SpriteComponent.class);
    }
}
