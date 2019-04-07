package engine.external.component;

public abstract class Component<T> {
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
