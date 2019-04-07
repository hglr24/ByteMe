package actions;

import engine.external.Entity;
import engine.external.component.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class Action {
    private List<Class<? extends Action>> actionsList= new ArrayList<>();

    public Action(){
        actionsList.add(getClass());
    }

    protected Consumer<Entity> makeValueAction(Object newValue, Class<? extends Component> componentClass) {
        return (Consumer<Entity>) (entity) -> {
            Component component = entity.getComponent(componentClass);
            component.setValue(newValue);
        };
    }

    /**
     * Get the list of available Actions to display to the user in the authoring environment
     * @return an unmodifiable List of the subclasses of Action
     */
    public List<Class<? extends Action>> getActionList(){
        return Collections.unmodifiableList(actionsList);
    }
}
