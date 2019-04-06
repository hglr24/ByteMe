package engine.external;

import java.util.List;

public interface IEvent {
    void execute(List<Entity> entities);
}
