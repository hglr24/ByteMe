package frontend.header;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import frontend.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class UserPane {
    private static final String IMAGE_PATH = "center/data/profile_information/images/default.png";
    public static final int IMAGE_SIZE = 75;
    private String myName;
    private Pane myUserDisplay;
    private ResourceBundle myLanguageBundle;

    public UserPane(String username) {
        // todo: make this actually use username
    }

    public UserPane() {
        // todo: This will be used when usernames are implemented
        // this("default");
        myName = "default";
        myLanguageBundle = ResourceBundle.getBundle("languages/English");
        try {
            initializeDisplay();
        } catch (FileNotFoundException e) {
            System.out.println("Please enter a valid username"); // todo: fix this
        }
    }

    public Pane getDisplay() {
        return myUserDisplay;
    }

    private void initializeDisplay() throws FileNotFoundException {
        VBox buttons = makeButtonList();
        Image icon = new Image(new FileInputStream(IMAGE_PATH));
        ImageView profilePicture = new ImageView(icon);
        profilePicture.setPreserveRatio(true);
        profilePicture.setFitWidth(IMAGE_SIZE);
        myUserDisplay = new HBox();
        myUserDisplay.getChildren().add(profilePicture);
        myUserDisplay.getChildren().add(buttons);
    }

    private VBox makeButtonList() { // todo: figure out the issue where making buttons changes the background color
        Button profileButton = new Button(Utilities.getValue(myLanguageBundle, "profileButton")); // todo: make these buttons
        Button settingsButton = new Button(Utilities.getValue(myLanguageBundle, "settingsButton"));
        Button logOutButton = new Button(Utilities.getValue(myLanguageBundle, "logOutButton"));
        VBox buttonList = new VBox();
        buttonList.getChildren().addAll(profileButton, settingsButton, logOutButton);
        return buttonList;
    }

}
