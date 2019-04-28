package engine.external.actions;

import engine.external.component.PizzaComponent;

public class PizzaAction extends AddComponentAction<Boolean> {

    public PizzaAction(Boolean pizza) {
        setAbsoluteAction(new PizzaComponent(pizza));
    }
}
