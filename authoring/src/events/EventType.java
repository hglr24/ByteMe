package events;

import java.util.Arrays;
import java.util.List;

public enum EventType {

    BOTTOMCOLLISION ("Bottom Collision", BottomCollisionEvent.class, new Class<?>[]{String.class,String.class}),
    LEFTCOLLISION ("Left Collision", LeftCollisionEvent.class, new Class<?>[]{String.class,String.class}),
    RIGHTCOLLISION ("Right Collision", RightCollisionEvent.class, new Class<?>[]{String.class,String.class}),
    TOPCOLLISION ("Top Collision", TopCollisionEvent.class, new Class<?>[]{String.class,String.class}),
    TIMER ("Timer", TimerEvent.class, new Class<?>[]{String.class,Double.class});

    public static final List<String> allDisplayNames = Arrays.asList(BOTTOMCOLLISION.displayName,LEFTCOLLISION.displayName,
            RIGHTCOLLISION.displayName,TOPCOLLISION.displayName,TIMER.displayName);
    private final String displayName;
    private final Class<?> className;
    private final Class<?>[] classConstructorTypes;

    EventType(String displayName, Class<?> className, Class<?>[] constructorTypes) {
        this.displayName = displayName;
        this.className = className;
        this.classConstructorTypes = constructorTypes;
    }

    public Class<?>[] getConstructorTypes(){
        return this.classConstructorTypes;
    }

    public Class<?> getClassName(){
        return this.className;
    }




}
