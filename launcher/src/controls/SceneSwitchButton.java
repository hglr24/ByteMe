package controls;

import javafx.scene.control.ToggleButton;

public class SceneSwitchButton extends ToggleButton {
    public SceneSwitchButton(String label){
        this.setText(label);
        this.getStyleClass().add("default_launcher.css");

    }

}
