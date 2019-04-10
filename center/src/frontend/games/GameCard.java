package frontend.games;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import runner.external.GameCenterData;
import frontend.Utilities;
import runner.external.GameRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class GameCard {
    private static final int GAME_IMAGE_SIZE = 200;
    private static final int BUTTON_WIDTH = 100;
    private static final int WRAP_OFFSET = 25;

    public static final double DISPLAY_WIDTH = 300;
    public static final double DISPLAY_HEIGHT = 300;
    private static final String DEFAULT_IMAGE_LOCATION = "center/data/game_information/images/default_game.png";
    private static final String DEFAULT_LANGUAGE_LOCATION = "languages/English";
    private static final String BACKGROUND_SELECTOR = "rectangle";
    private static final String TEXT_SELECTOR = "cardtext";
    private static final String TITLE_SELECTOR = "cardtitle";
    private static final String BODY_SELECTOR = "cardbody";
    private static final String BUTTONS_SELECTOR = "buttons";
    private int myIndex;
    private ResourceBundle myLanguageBundle;
    private GameCenterData myGame;
    private Pane myDisplay;

    public GameCard(GameCenterData game, int index) {
        myGame = game;
        myIndex = index % 2 + 1;
        myLanguageBundle = ResourceBundle.getBundle(DEFAULT_LANGUAGE_LOCATION);
        initializeDisplay();
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        BorderPane tempDisplay = new BorderPane();
        StackPane cardContents = null;
        cardContents = fillCardContents();
        tempDisplay.setCenter(cardContents);
        myDisplay = tempDisplay;
    }

    private StackPane fillCardContents() {
        StackPane cardContents = new StackPane();
        addBackground(cardContents);
        addForeGround(cardContents);
        return cardContents;
    }

    private void addBackground(Pane pane) {
        BorderPane background = new BorderPane();
        background.setBackground(Background.EMPTY);
        Rectangle display = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        display.getStyleClass().add(BACKGROUND_SELECTOR);
        display.getStyleClass().add(BACKGROUND_SELECTOR + myIndex);
        background.setCenter(display);
        pane.getChildren().add(background);
    }

    private void addForeGround(Pane pane) {
        BorderPane foreground = new BorderPane();
        foreground.setPadding(new Insets(0, 0, 20, 0));
        addTitleContent(foreground);
        addImageAndContent(foreground);
        addButtons(foreground);
        pane.getChildren().add(foreground);
    }

    private void addButtons(BorderPane foreground) {
        Button readMore = new Button(Utilities.getValue(myLanguageBundle, "readMoreButton"));
        readMore.setPrefWidth(BUTTON_WIDTH);
        Button play = new Button(Utilities.getValue(myLanguageBundle, "playGameButton"));
        play.setPrefWidth(BUTTON_WIDTH);
        play.setOnAction(e -> handleButton(myGame.getFolderName()));
        HBox buttons = new HBox(readMore, play);
        buttons.getStyleClass().add(BUTTONS_SELECTOR);
        BorderPane buttonPane = new BorderPane();
        buttonPane.setCenter(buttons);
        foreground.setBottom(buttonPane);
    }

    private void handleButton(String folderName) {
        try {
            GameRunner gameRunner = new GameRunner(folderName);
        } catch (FileNotFoundException e) {
            // todo: print error message
        }
    }

    private void addImageAndContent(BorderPane foreground) {
        BorderPane contentPane = new BorderPane();
        try {
            addImage(contentPane);
        } catch (FileNotFoundException e) {
            // do nothing, because in this case there would just be no image on the card which is fine
            // todo: possibly create a type of card with no image & turn this into a factory type class
        }
        Text imageDescription = new Text(myGame.getDescription());
        imageDescription.getStyleClass().add(BODY_SELECTOR);
        imageDescription.getStyleClass().add(TEXT_SELECTOR + myIndex);
        imageDescription.setWrappingWidth(DISPLAY_WIDTH - WRAP_OFFSET);
        contentPane.setCenter(imageDescription);
        contentPane.setPadding(new Insets(7));
        foreground.setCenter(contentPane);
    }

    private void addImage(BorderPane contentPane) throws FileNotFoundException {
        ImageView noGameFound;
        try {
            noGameFound = new ImageView(new Image(new FileInputStream(myGame.getImageLocation())));
        } catch (Exception e) {
            noGameFound = new ImageView(new Image(new FileInputStream(DEFAULT_IMAGE_LOCATION)));
        }
        noGameFound.setPreserveRatio(true);
        noGameFound.setFitWidth(GAME_IMAGE_SIZE);
        BorderPane imagePane = new BorderPane();
        imagePane.setCenter(noGameFound);
        contentPane.setTop(imagePane);
    }

    private void addTitleContent(BorderPane foreground) {
        Text title = new Text(myGame.getTitle());
        title.getStyleClass().add(TITLE_SELECTOR);
        title.getStyleClass().add(TEXT_SELECTOR + myIndex);
        BorderPane titlePane = new BorderPane();
        titlePane.setCenter(title);
        foreground.setTop(titlePane);
    }

}
