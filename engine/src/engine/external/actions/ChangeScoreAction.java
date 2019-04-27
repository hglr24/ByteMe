package engine.external.actions;

import engine.external.component.ScoreComponent;

public class ChangeScoreAction extends AssociatedEntityAction {
    public ChangeScoreAction(ModifyType type, Double gravity) {
        setAction(type, gravity, ScoreComponent.class);
    }
}
