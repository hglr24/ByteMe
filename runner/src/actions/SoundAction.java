package actions;

import engine.external.component.SoundComponent;

public class SoundAction extends StringAction {
    public SoundAction(String pathname){
        setAction(pathname, SoundComponent.class);
    }
}
