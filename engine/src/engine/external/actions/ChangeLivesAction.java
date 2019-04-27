package engine.external.actions;

import engine.external.component.ScoreComponent;

public class ChangeLivesAction extends NumericAction {
    public ChangeLivesAction(ModifyType type, Double lives) {
        setAction(type, lives, ScoreComponent.class);
    }
}
