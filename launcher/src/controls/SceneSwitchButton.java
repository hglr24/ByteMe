package controls;

import javafx.scene.control.ToggleButton;

class SceneSwitchButton extends ToggleButton {
    SceneSwitchButton(String label){
        this.setText(label);
        this.getStyleClass().add("default_launcher.css");

    }

}
