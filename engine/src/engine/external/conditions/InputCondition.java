package engine.external.conditions;

import javafx.scene.input.KeyCode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class InputCondition extends Condition {
    public InputCondition(Collection<KeyCode> inputs) {
        setPredicate((Predicate<Collection<KeyCode>> & Serializable) keycodes -> keycodes.containsAll(inputs));
    }

    public InputCondition(KeyCode input) {
        this(Arrays.asList(input));
    }
}
