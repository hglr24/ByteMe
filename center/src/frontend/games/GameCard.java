/**
 * @Author Megan Phibbons
 * @Date April 2019
 * @Purpose Act as a display component for game objects. Contains title, description, image, and buttons in a neater package.
 * @Dependencies javafx, GameRunner, GameCenterData, Utilities, and java.io
 * @Uses: Used by GameList for displaying single games at a time.
 */

package frontend.games;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    private static final String FOREGROUND_SELECTOR = "cardpadding";
    private static final String INDIVIDUAL_BUTTON_SELECTOR = "button";
    private static final String CONTENT_SELECTOR = "contentpadding";
    private int myIndex;
    private ResourceBundle myLanguageBundle;
    private GameCenterData myGame;
    private Pane myDisplay;

    /**
     * @purpose constructor that sets up parameters of the game and initializes the resource bundle used for text.
     * @param game the GameCenterData object that represents the contents of the GameCard
     * @param index the index that the game card is in a list. This is used for styling purposes
     */
    public GameCard(GameCenterData game, int index) {
        myGame = game;
        myIndex = index % 2 + 1;
        myLanguageBundle = ResourceBundle.getBundle(DEFAULT_LANGUAGE_LOCATION);
        initializeDisplay();
    }

    /**
     * @purpose share the display of the pane. This is used in GameList so that the contents of the GameCard can actually
     * be displayed.
     * @return the current display stored in GameCard.
     */
    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        BorderPane tempDisplay = new BorderPane();
        StackPane cardContents = fillCardContents();
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
        Rectangle display = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        display.getStyleClass().add(BACKGROUND_SELECTOR + myIndex);
        background.setCenter(display);
        pane.getChildren().add(background);
    }

    private void addForeGround(Pane pane) {
        BorderPane foreground = new BorderPane();
        foreground.getStyleClass().add(FOREGROUND_SELECTOR);
        addTitleContent(foreground);
        addImageAndContent(foreground);
        addButtons(foreground);
        pane.getChildren().add(foreground);
    }

    private void addButtons(BorderPane foreground) {
        Button readMore = new Button(Utilities.getValue(myLanguageBundle, "readMoreButton"));
        readMore.getStyleClass().add(INDIVIDUAL_BUTTON_SELECTOR);
        Button play = new Button(Utilities.getValue(myLanguageBundle, "playGameButton"));
        play.getStyleClass().add(INDIVIDUAL_BUTTON_SELECTOR);
        play.setOnAction(e -> handleButton(myGame.getFolderName()));
        HBox buttons = new HBox(readMore, play);
        buttons.getStyleClass().add(BUTTONS_SELECTOR);
        BorderPane buttonPane = new BorderPane();
        buttonPane.setCenter(buttons);
        foreground.setBottom(buttonPane);
    }

    private void handleButton(String folderName) {
        try {
            new GameRunner(folderName);
        } catch (FileNotFoundException e) {
            // todo: print error message
        }
    }

    private void addImageAndContent(BorderPane foreground) {
        BorderPane contentPane = new BorderPane();
        try {
            addImage(contentPane);
        } catch (FileNotFoundException e) { // this means that even the default image could not be found
            // do nothing, because in this case there would just be no image on the card which is fine
            // todo: possibly create a type of card with no image & turn this into a factory type class
        }
        Text imageDescription = new Text(myGame.getDescription());
        imageDescription.getStyleClass().add(BODY_SELECTOR);
        imageDescription.getStyleClass().add(TEXT_SELECTOR + myIndex);
        imageDescription.setWrappingWidth(DISPLAY_WIDTH - WRAP_OFFSET);
        contentPane.setCenter(imageDescription);
        contentPane.getStyleClass().add(CONTENT_SELECTOR);
        foreground.setCenter(contentPane);
    }

    private void addImage(BorderPane contentPane) throws FileNotFoundException {
        ImageView gameImage;
        try {
            gameImage = new ImageView(new Image(new FileInputStream(myGame.getImageLocation())));
        } catch (Exception e) { // if any exceptions come from this, it should just become a default image.
            gameImage = new ImageView(new Image(new FileInputStream(DEFAULT_IMAGE_LOCATION)));
        }
        gameImage.setPreserveRatio(true);
        gameImage.setFitWidth(GAME_IMAGE_SIZE);
        BorderPane imagePane = new BorderPane();
        imagePane.setCenter(gameImage);
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
