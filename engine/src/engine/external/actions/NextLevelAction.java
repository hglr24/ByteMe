package engine.external.actions;


import engine.external.component.NextLevelComponent;

public class NextLevelAction extends NumericAction {
    public NextLevelAction(NumericAction.ModifyType type, Double level){
        setAction(type, level, NextLevelComponent.class);
    }

}