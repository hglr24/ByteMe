package engine.external.component;

import java.io.Serializable;

/**
 * Containers for storing necessary data of Entities
 * @param <T> data to be stored; T can be any type (e.g. String, Double, Boolean, Collection, etc.)
 */
public abstract class Component<T> implements Serializable {
    protected T myValue;

    public Component(T value){
        myValue = value;
    }

    public void setValue(T value){
        myValue = value;
    }

    public T getValue(){
        return myValue;
    }
}
