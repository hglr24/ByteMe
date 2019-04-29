package frontend.ratings;

import data.external.DataManager;
import data.external.GameCenterData;
import data.external.GameRating;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class RatingList {
    private GameCenterData myData;
    private BorderPane myDisplay;
    private List<GameRating> myRatings;
    private DataManager myManager;

    public RatingList(GameCenterData data, DataManager manager) {
        myData = data;
        myManager = manager;
        try {
            myRatings = myManager.getAllRatings(data.getTitle());
        } catch (SQLException e) {
            // todo: fix this
        }
        initializeDisplay();
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        VBox allRatings = new VBox();
        for(GameRating rating : myRatings) {
            SingleRating ratingDisplay = new SingleRating(rating, myManager, myData);
            allRatings.getChildren().add(ratingDisplay.getDisplay());
        }
        allRatings.setSpacing(10);
        myDisplay.setCenter(allRatings);
    }

}
