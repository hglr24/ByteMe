package engine.external.component;

import java.io.Serializable;

/**
 * @param <T> data to be stored; T can be any type (e.g. String, Double, Boolean, Collection, etc.)
 * @author Lucas Liu
 * Containers for storing necessary data of Entities.
 * A `Component` is essentially a container of value. There is an abstract super `Component` class,
 * which is extended by many concrete sub-classes to hold values of certain types (Double, Boolean,
 * String, etc.) for different purposes (position, image, sound, etc.). `Component` values of entities are updated on the game loops.
 * A Component has at its base level just a getter and setter, a very simple API/interface. One
 * must note that the use of the component is clever and useful because it leverages the fact that
 * it is it's own class, which is advantageous over something like an instance variable. Since it is
 * a class, one can filter for it using .class, which is extremely useful for systems.
 * .class does not require an instance of the class to call, which is much neater than creating an
 * instance (purely just for filtering), then calling .getclass(), or some other work around. An
 * instance variable does not lend itself to such an easy built in 'searchability'.
 * Additionally, the class can take on a variety of flexible styles because of generic typing. It is
 * very easy and short to create new kinds of component subclasses thanks to this design. You'll
 * notice that many subclasses are only a few lines. A default value and original value is provided
 * to
 * support a best effort approach - in the case of drastic failure, the software can default a corrupted/otherwise
 * malformed component to a default value, or the original value at instantiation.
 */
public abstract class Component<T> implements Serializable {
    protected T myValue;
    protected T myOriginalValue;

    /**
     * create a component
     *
     * @param value
     */
    public Component(T value) {
        myValue = value;
        myOriginalValue = value;
    }

    /**
     * set value of component
     *
     * @param value
     */
    public void setValue(T value) {
        myValue = value;
    }

    /**
     * get value
     *
     * @return
     */
    public T getValue() {
        return myValue;
    }

    /**
     * set value back to original value at instantiation. useful for best effort error handling,
     * or setting a game back to an original state.
     */
    public void resetToOriginal() {
        myValue = myOriginalValue;
    }
}
