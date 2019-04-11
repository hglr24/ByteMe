package pane;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import manager.SwitchToUserOptions;

public class UserOptionsDisplay extends HBox {
    private static final String CREATE_LAUNCHER = "create";
    private static final String PLAY_LAUNCHER = "play";
    public UserOptionsDisplay(SwitchToUserOptions switchDisplay, SwitchToUserOptions switchToLauncher){
        this.getStyleClass().add("default_launcher.css");
        this.setTranslateY(100);
        setUpImages(switchDisplay,switchToLauncher);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(100);

    }
    private void setUpImages(SwitchToUserOptions switchDisplay, SwitchToUserOptions switchToLauncher){
        LauncherControlDisplay myCreator = new LauncherControlDisplay(CREATE_LAUNCHER);
        myCreator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switchDisplay.switchPage();
            }
        });
        LauncherControlDisplay myPlayer = new LauncherControlDisplay(PLAY_LAUNCHER);
        myPlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switchToLauncher.switchPage();
            }
        });
        this.getChildren().add(0,myCreator);
        this.getChildren().add(1, myPlayer);
    }
}
