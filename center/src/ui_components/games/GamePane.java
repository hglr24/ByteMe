package ui_components.games;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ui_components.Utilities;

import java.util.ResourceBundle;

public class GamePane {
    public static final int GAME_PANE_TOP_PADDING = 10;
    public static final int GAME_PANE_PADDING = 100;
    public static final int TITLE_FONT_SIZE = 32;
    public static final int NUM_GAMES = 6;
    private Pane myDisplay;
    private ResourceBundle myResources;

    public GamePane() {
        myResources = ResourceBundle.getBundle("languages/English");
        initializeDisplay();
    }

    private void initializeDisplay() {
        BorderPane gamePane = new BorderPane();
        gamePane.setPadding(new Insets(GAME_PANE_TOP_PADDING, GAME_PANE_PADDING, GAME_PANE_PADDING, GAME_PANE_PADDING));
        Text subtitle = new Text(Utilities.getValue(myResources, "gamePaneTitle"));
        subtitle.setFill(Color.WHITE);
        subtitle.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setAlignment(subtitle, Pos.CENTER);
        subtitle.setFont(new Font(TITLE_FONT_SIZE));
        gamePane.setTop(subtitle);
        gamePane.setCenter((new GameList(NUM_GAMES)).getDisplay());
        myDisplay = gamePane;
    }

    public Pane getDisplay() {
        return myDisplay;
    }

}
