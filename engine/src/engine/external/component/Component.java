package engine.external.component;

import java.io.Serializable;

public abstract class Component<T> implements Serializable {
    private T myValue;

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
