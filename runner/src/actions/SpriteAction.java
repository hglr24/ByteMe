package actions;

import engine.external.component.SpriteComponent;

public class SpriteAction extends StringAction {
    public SpriteAction(String pathname){
        setAction(pathname, SpriteComponent.class);
    }
}
