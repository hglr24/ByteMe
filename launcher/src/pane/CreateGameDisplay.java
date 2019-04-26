package pane;

import controls.InformativeField;
import data.external.DataManager;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manager.SwitchToAuthoring;
import manager.SwitchToUserOptions;
import data.external.GameCenterData;

import java.io.File;

public class CreateGameDisplay extends VBox {
    private static final String FOLDER_KEY = "user_files";
    private static final String CREATE_LAUNCHER = "create";
    private static final String GAME_FIELD = "Enter Game Name";
    private static final String GAME_DESCRIPITON = "Enter Game Description";
    private static final String PLAY_LAUNCHER = "play";
    private File myFile;
    private InformativeField gameName = new InformativeField(GAME_FIELD);
    private InformativeField gameDescription = new InformativeField(GAME_DESCRIPITON);
    /**
     * This page will prompt the user to enter information about the new game they wish to create, such as its cover image,
     * title, and description. Then, it will create a new GameDisplay object to be passed to the authoring environment so they
     * can create a folder for the user to put other necessary files in, such as images they want to use in the creation
     * of their game
     * @author Anna Darwish
     */
    public CreateGameDisplay(SwitchToAuthoring sceneSwitch, SwitchToUserOptions sceneSwitchCenter){
        this.getStyleClass().add("default_launcher.css");
        setUpImages(sceneSwitch,sceneSwitchCenter);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

    }
    private void setUpImages(SwitchToAuthoring sceneSwitch, SwitchToUserOptions sceneSwitchCenter){
        LauncherControlDisplay myCreator = new LauncherControlDisplay(FOLDER_KEY);
        myCreator.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
             myFile =  fileChooser.showOpenDialog(new Stage());
            });
        LauncherControlDisplay myPlayer = new LauncherControlDisplay(CREATE_LAUNCHER);
        myPlayer.setOnMouseClicked(mouseEvent -> {
                DataManager dm = new DataManager();
                dm.createGameFolder("GameOne");
                GameCenterData myDataCenter = new GameCenterData(gameName.getTextEntered(),
                        gameDescription.getTextEntered(), "GameOne", myFile.toURI().toString(),"DummyUserName");
            sceneSwitch.switchScene(myDataCenter);
        });
        LauncherControlDisplay myCenter = new LauncherControlDisplay(PLAY_LAUNCHER);
        myCenter.setOnMouseClicked(mouseEvent -> sceneSwitchCenter.switchPage());

        makeGameInfo(gameName,200);
        makeGameInfo(gameDescription, 300);
        this.getChildren().add(myCreator);
        this.getChildren().add(gameName);
        this.getChildren().add(gameDescription);
        HBox playerAndCenter = new HBox();
        playerAndCenter.getStylesheets().add("default_launcher.css");
        playerAndCenter.setAlignment(Pos.CENTER);
        playerAndCenter.setSpacing(100);
        playerAndCenter.getChildren().add(myPlayer);
        playerAndCenter.getChildren().add(myCenter);
        this.getChildren().add(playerAndCenter);


    }

    private void makeGameInfo(InformativeField field, int buttonWidth){
        field.setMaxWidth(buttonWidth);
        field.setAlignment(Pos.CENTER);

    }

}
