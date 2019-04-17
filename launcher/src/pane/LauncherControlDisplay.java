package pane;

import controls.LauncherSymbol;
import controls.PaneLabel;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

class LauncherControlDisplay extends VBox {
    /**
     * Many of the controls in this launcher environment combine an @LauncherSymbol and @PaneLabel to navigate between
     * scenes, so this control helps to standardize this display between pages
     * @author Anna Darwish
     */
    LauncherControlDisplay(String displayType){
        this.getStylesheets().add("default_launcher.css");
        this.setSpacing(50);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(0, new PaneLabel(displayType));
        this.getChildren().add(1, new LauncherSymbol(displayType));
    }

}
