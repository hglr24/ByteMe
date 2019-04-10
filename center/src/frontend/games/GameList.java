package frontend.games;

import data.external.DataManager;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import runner.external.GameCenterData;

import java.util.ArrayList;
import java.util.List;

public class GameList {
    private Pane myDisplay;
    private static final double CARD_H_OFFSET = 50;
    private static final double CARD_V_OFFSET = 50;
    private static final int NUM_CARDS_DISPLAYED = 3;
    private List<GameCenterData> myGames;

    public GameList() {
        DataManager manager = new DataManager();
        List<Object> originalList = manager.loadAllGameInfoObjects();
        myGames = (List<GameCenterData>) (Object) originalList;
        initializeDisplay();
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        FlowPane gameList = new FlowPane();
        gameList.setAlignment(Pos.CENTER);
        gameList.setVgap(CARD_V_OFFSET);
        gameList.setHgap(CARD_H_OFFSET);
        gameList.setPrefWrapLength((GameCard.DISPLAY_WIDTH + CARD_H_OFFSET) * NUM_CARDS_DISPLAYED);
        gameList.setStyle("-fx-background-color: transparent;");
        int index = 0;
        for(GameCenterData game : myGames) {
            GameCard c = new GameCard(game, index);
            gameList.getChildren().add(c.getDisplay());
            index++;
        }
        ScrollPane scroller = new ScrollPane();
        scroller.setFitToWidth(true);
        scroller.getStylesheets().add("center.css");
        scroller.setContent(gameList);
        scroller.setStyle("-fx-background-color: transparent;");
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setMinHeight(GameCard.DISPLAY_HEIGHT * 2);
        BorderPane content = new BorderPane(scroller);
        myDisplay = content;
    }

}
