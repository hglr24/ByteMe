package engine.external.actions;

import engine.external.component.WidthComponent;

public class HeightAction extends NumericAction {

        public HeightAction(ModifyType type, Double height){
            setAction(type, height, WidthComponent.class);
        }
}
