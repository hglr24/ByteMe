package runner.Testing.events;

import java.util.Arrays;
import java.util.List;

public enum EventType {
    COLLISION ("Collision", LeftCollisionEvent.class),
    TIMER ("Timer", TimerEvent.class);
    public static final List<String> allDisplayNames = Arrays.asList(COLLISION.displayName,
            TIMER.displayName);
    private final String displayName;
    private final Class<?> className;
    EventType(String displayName, Class<?> className) {
        this.displayName = displayName;
        this.className = className;
    }


}
