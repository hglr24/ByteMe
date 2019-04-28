package events;

import java.util.Arrays;
import java.util.List;

public enum EventType {

    BottomCollision ("Bottom Collision",true, "engine.external.events.BottomCollisionEvent"),
    LeftCollision("Left Collision", true, "engine.external.events.LeftCollisionEvent"),
    RightCollision ("Right Collision",  true, "engine.external.events.RightCollisionEvent"),
    TopCollision ("Top Collision", true, "engine.external.events.TopCollisionEvent"),
    General ("General", false, "engine.external.events.Event");
    
    public static final List<String> allDisplayNames = Arrays.asList(BottomCollision.displayName,LeftCollision.displayName,
            RightCollision.displayName,TopCollision.displayName, General.displayName);
    private final String displayName;
    private final Boolean interactive;
    private final String className;

    EventType(String displayName, Boolean isInteractive, String className) {
        this.displayName = displayName;
        this.interactive = isInteractive;
        this.className = className;
    }


    public Boolean isInteractive(){return this.interactive;}

    public String getClassName(){return this.className;}


}
