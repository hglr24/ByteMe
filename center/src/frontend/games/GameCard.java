package frontend.games;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import runner.external.GameCenterData;
import frontend.Utilities;
import runner.external.GameRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class GameCard {
    public static final int GAME_IMAGE_SIZE = 200;
    public static final int BUTTON_WIDTH = 100;
    public static final int HORIZONTAL_BUTTON_SPACING = 15;
    public static final int SUBTITLE_FONT_SIZE = 24;

    private Pane myDisplay;
    public static final double DISPLAY_WIDTH = 300;
    public static final double DISPLAY_HEIGHT = 300;
    private static final String DEFAULT_IMAGE_LOCATION = "center/data/game_information/images/default_game.png";
    private ResourceBundle myLanguageBundle;
    private GameCenterData myGame;

    public GameCard(GameCenterData game) {
        myGame = game;
        myLanguageBundle = ResourceBundle.getBundle("languages/English");
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
        display.setFill(Color.WHITE);
        background.setCenter(display);
        pane.getChildren().add(background);
    }

    private void addForeGround(Pane pane) {
        BorderPane foreground = new BorderPane();
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
        buttons.setSpacing(HORIZONTAL_BUTTON_SPACING);
        buttons.setAlignment(Pos.CENTER);
        Text spacing = new Text("");
        BorderPane buttonPane= new BorderPane();
        buttonPane.setCenter(buttons);
        buttonPane.setBottom(spacing);
        foreground.setBottom(buttonPane);
    }

    private void handleButton(String folderName) {
        System.out.println("running");
        try {
            GameRunner gameRunner = new GameRunner(folderName);
        } catch (FileNotFoundException e) {
            System.out.println("in center");
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
        imageDescription.setWrappingWidth(DISPLAY_WIDTH);
        imageDescription.setTextAlignment(TextAlignment.CENTER);
        contentPane.setCenter(imageDescription);
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
        title.setFont(new Font(SUBTITLE_FONT_SIZE));
        BorderPane titlePane = new BorderPane();
        titlePane.setCenter(title);
        foreground.setTop(titlePane);
    }

}
