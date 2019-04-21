package engine.external;

import javafx.scene.input.KeyCode;

import java.util.Collection;
import java.util.List;

public interface IEventEngine {
    /**
     * Execute takes in ALL the entities in play, FILTERS them using the
     * entity type that the event is associated with, checks the CONDITIONS
     * of the event for each of the filtered entities, and applies the ACTIONS
     * of the event for each of the filtered entities that pass the engine.external.conditions.
     * @param entities All the entities in play
     */
    void execute(List<Entity> entities, Collection<KeyCode> inputs);
}
