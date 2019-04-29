package frontend.popups;

import data.external.DataManager;
import data.external.GameCenterData;
import frontend.Utilities;
import frontend.games.GameList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class UserProfileDisplay extends Popup {
    private String myUsername;
    private String myCurrentUser;
    private GameCenterData myData;

    private static final double USER_WIDTH = 1100;
    private static final double USER_HEIGHT = 650;
    private static final double IMAGE_WIDTH = 125;
    private static final double SCROLL_OFFSET = 50;
    private static final double SUBTITLE_WRAP_LENGTH = USER_WIDTH - 2 * IMAGE_WIDTH - 20;
    private static final String BODY_SELECTOR = "bodyfont";
    private static final String TITLE_SELECTOR = "titlefont";
    private static final String SUBTITLE_SELECTOR = "subtitlefont";
    private static final String DEFAULT_IMAGE_LOCATION = "center/data/profile_information/images/default.png";

    public UserProfileDisplay(GameCenterData data, DataManager manager, String currentUser, String username) {
        super(manager);
        myData = data;
        myUsername = username;
        myCurrentUser = currentUser;
        initializeDisplay();
        display();
    }

    public void closeButton(GameCenterData data) {
        ((Stage) myDisplay.getScene().getWindow()).close();
    }

    @Override
    protected void addHeader() {
        StackPane header = new StackPane();
        try {
            BorderPane titleAndSubtitle = new BorderPane();
            Text username = new Text(myUsername);
            username.getStyleClass().add(TITLE_SELECTOR);
            BorderPane.setAlignment(username, Pos.CENTER);
            Text bio = new Text(myManager.getBio(myUsername));
            bio.setWrappingWidth(SUBTITLE_WRAP_LENGTH);
            bio.getStyleClass().add(BODY_SELECTOR);
            titleAndSubtitle.setTop(username);
            titleAndSubtitle.setCenter(bio);
            ImageView userImage;
            try {
                userImage = new ImageView(new Image(myManager.getProfilePic(myUsername)));
            } catch(Exception e) {
                try {
                    userImage = new ImageView(new Image(new FileInputStream(DEFAULT_IMAGE_LOCATION)));
                } catch (FileNotFoundException e1) {
                    myDisplay.setTop(titleAndSubtitle);
                    return;
                }
            }
            userImage.setPreserveRatio(true);
            userImage.setFitWidth(IMAGE_WIDTH);
            BorderPane imagePane = new BorderPane();
            imagePane.setLeft(userImage);
            header.getChildren().addAll(titleAndSubtitle, imagePane);
            myDisplay.setTop(header);
        } catch (SQLException e) {
            //todo: handle this
        }
    }

    @Override
    protected void addBody() {
        BorderPane gameListPane = new BorderPane();
        Text title = new Text(myUsername + Utilities.getValue(myLanguageBundle, "userPageGameList"));
        title.getStyleClass().add(SUBTITLE_SELECTOR);
        BorderPane.setAlignment(title, Pos.CENTER);
        gameListPane.setTop(title);
        gameListPane.setCenter(new GameList(myCurrentUser, myUsername, USER_HEIGHT).getDisplay());
        myDisplay.setCenter(gameListPane);
    }

    @Override
    protected void addButtons() {
        // intentionally doing nothing here because this screen doesn't  need buttons
    }

    @Override
    protected void display() {
        showScene(myDisplay, USER_WIDTH, USER_HEIGHT + SCROLL_OFFSET);
    }
}
