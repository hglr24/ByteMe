package ui.control;
import javafx.scene.control.Button;

public class EventMenuProperty extends Button {
    private String myDisplayName;
    public EventMenuProperty(String displayName){
        this.setText(displayName);
        myDisplayName = displayName;
    }
    @Override
    public String toString(){
        return myDisplayName;
    }
}
