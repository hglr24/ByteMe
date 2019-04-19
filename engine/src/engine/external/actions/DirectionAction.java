package engine.external.actions;

import engine.external.component.DirectionComponent;

public class DirectionAction extends NumericAction {
   public DirectionAction(ModifyType type, Integer direction){
       setAction(type, direction, DirectionComponent.class);
   }
}