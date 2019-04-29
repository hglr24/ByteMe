package engine.external.actions;


import engine.external.component.NextLevelComponent;

public class NextLevelAction extends AddComponentAction {
    public NextLevelAction(Double level){
        setAbsoluteAction(new NextLevelComponent(level));
        myComponentClass = NextLevelComponent.class;
    }


}