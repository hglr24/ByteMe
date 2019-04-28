/**
 * @Author Megan Phibbons
 * @Date April 2019
 * @Purpose This class wraps up methods that are used in many places into one class. The methods are static because
 * they do not rely on the instantiation of an object to work.
 * @Dependencies none
 * @Uses: Used in almost every class so that there is no duplicated code.
 */

package frontend;

import data.external.DataManager;
import data.external.GameCenterData;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import runner.external.GameRunner;
import voogasalad.util.reflection.Reflection;

import java.io.FileNotFoundException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Utilities {
    private static final String ERROR_MESSAGE = "error";
    private static final String DEFAULT_IMAGE_LOCATION = "default_game";
    private static final String INDIVIDUAL_BUTTON_SELECTOR = "button";
    private static final String BUTTON_LIST_SELECTOR = "buttons";
    private static final String PANE_SELECTOR = "pane";
    private static final Color BACKGROUND_COLOR = Color.rgb(46, 43, 51);
    private static final String TITLE_SELECTOR = "titlefont";
    private static final String SUBTITLE_SELECTOR = "subtitlefont";

    /**
     * @purpose given a resource bundle and a key, get the value. This was pulled out for the sole purpose of adding in
     * error checking, because checking for missing keys makes the process longer than just a line.
     * @param bundle the resource bundle which we access
     * @param key the key that we are looking for in the bundle
     * @return the string that is associated with that key (unless the key is not in the bundle, then return the error message
     */
    public static String getValue(ResourceBundle bundle, String key) {
        try {
            return bundle.getString(key);
        }
        catch (MissingResourceException e) {
            return ERROR_MESSAGE;
        }
    }

    public static Pane getImagePane(DataManager manager, String imageLocation, double gameSize) {
        ImageView gameImage;
        try {
            gameImage = new ImageView(new Image(manager.loadImage(imageLocation)));
        } catch (Exception e) { // if any exceptions come from this, it should just become a default image.
            gameImage = new ImageView(new Image(manager.loadImage(DEFAULT_IMAGE_LOCATION)));
        }
        gameImage.setPreserveRatio(true);
        gameImage.setFitWidth(gameSize);
        BorderPane imagePane = new BorderPane();
        imagePane.setCenter(gameImage);
        return imagePane;
    }

    public static void launchGameRunner(String folderName) {
        try {
            new GameRunner(folderName);
        } catch (FileNotFoundException e) {
            // todo: print error message
        }
    }

    public static HBox makeButtons(Object object, GameCenterData data) {
        HBox buttonList = new HBox();
        buttonList.getStyleClass().add(BUTTON_LIST_SELECTOR);
        ResourceBundle buttonResources = ResourceBundle.getBundle(object.getClass().getSimpleName());
        for(String buttonName : buttonResources.keySet()) {
            Button button = new Button(getValue(buttonResources, buttonName));
            button.getStyleClass().add(INDIVIDUAL_BUTTON_SELECTOR);
            button.setOnAction(e -> Reflection.callMethod(object, buttonName, data));
            buttonList.getChildren().add(button);
        }
        return buttonList;
    }

    public static void showScene(Pane pane, double width, double height) {
        pane.getStylesheets().add("center.css");
        pane.getStyleClass().add(PANE_SELECTOR);
        Scene scene = new Scene(pane, width, height, BACKGROUND_COLOR);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public static void addTitleAndSubtitle(BorderPane pane, String title, String subtitle) {
        Text titleText = new Text(title);
        titleText.getStyleClass().add(TITLE_SELECTOR);
        Text subtitleText = new Text(subtitle);
        subtitleText.getStyleClass().add(SUBTITLE_SELECTOR);
        VBox text = new VBox(titleText, subtitleText);
        text.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(text, Pos.CENTER);
        pane.setTop(text);
    }

}
