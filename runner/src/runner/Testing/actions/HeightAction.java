package runner.Testing.actions;

import engine.external.component.HeightComponent;

public class HeightAction extends NumericAction {

        public HeightAction(ModifyType type, Double height){
            setAction(type, height, HeightComponent.class);
        }
}
