package actions;

import engine.external.Entity;
import engine.external.component.HealthComponent;

import java.util.function.Consumer;

public class HealthAction extends Action {

    public HealthAction(){ super();}

    /**
     *
     * @param health the new value to set the entity's health to
     * @return the Consumer<Entity> that sets an entity's health component to health
     */
    public Consumer<Entity> makeHealthComponentAction(double health) {
        return makeValueAction(health, HealthComponent.class);
    }
}
