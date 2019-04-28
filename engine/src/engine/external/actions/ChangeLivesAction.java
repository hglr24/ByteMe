package engine.external.actions;

import engine.external.component.LivesComponent;

public class ChangeLivesAction extends NumericAction {
    public ChangeLivesAction(ModifyType type, Double lives) {
        setAction(type, lives, LivesComponent.class);
    }
}
