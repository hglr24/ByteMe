package engine.external.component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anna Darwish
 */

public enum ComponentType {
    DESTROY("Destroy Entity"),
    DIRECTION("Direction"),
    GRAVITY("Gravity Constant"),
    HEALTH("Health"),
    HEIGHT("Height"),
    SOUND("Audio"),
    SPRITE("Sprite"),
    VISIBILITY("Visibility"),
    WIDTH("Width"),
    XPOSITION("X Position"),
    YPOSITION("Y Position"),
    XVELOCITY("X Velocity"),
    YVELOCITY("Y Velocity");

    public static final List<String> allDisplayNames = Arrays.asList(DESTROY.myDisplayName, DIRECTION.myDisplayName, GRAVITY.myDisplayName,
            HEALTH.myDisplayName, SOUND.myDisplayName, SPRITE.myDisplayName, VISIBILITY.myDisplayName, WIDTH.myDisplayName,
            XPOSITION.myDisplayName, YPOSITION.myDisplayName, XVELOCITY.myDisplayName, YVELOCITY.myDisplayName);

    private final String myDisplayName;

    ComponentType(String displayName) {
        myDisplayName = displayName;
    }

}
