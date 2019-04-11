package pane;

import controls.LauncherSymbol;
import controls.PaneLabel;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class LauncherControlDisplay extends VBox {
    public LauncherControlDisplay(PaneLabel label, LauncherSymbol symbol){
        this.getStylesheets().add("default_launcher.css");
        this.getChildren().add(0,label);
        this.getChildren().add(1, symbol);
    }
    public LauncherControlDisplay(String displayType){
        this.getStylesheets().add("default_launcher.css");
        this.setSpacing(50);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(0, new PaneLabel(displayType));
        this.getChildren().add(1, new LauncherSymbol(displayType));
    }

}
