package actions;

import engine.external.Entity;
import engine.external.component.SoundComponent;

import java.util.function.Consumer;

public class SoundAction extends Action {
    public SoundAction(){ super();}

    public Consumer<Entity> makeSoundAction(String pathname) {
        return makeValueAction(pathname, SoundComponent.class);
    }
}
