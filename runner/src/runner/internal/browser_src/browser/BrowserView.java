package runner.internal.browser_src.browser;

import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javax.imageio.ImageIO;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;


/**
 * A class used to display the viewer for a simple HTML browser.
 * 
 * See this tutorial for help on how to use the variety of components:
 *   http://download.oracle.com/otndocs/products/javafx/2/samples/Ensemble/
 * 
 * @author Owen Astrachan
 * @author Marcin Dobosz
 * @author Yuzhang Han
 * @author Edwin Ward
 * @author Robert C. Duvall
 */
public class BrowserView {
    // constants
    public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String STYLESHEET = "default.css";
    public static final String BLANK = " ";

    // scene, needed to report back to Application
    private Scene myScene;
    // web page
    private WebView myPage;
    // information area
    private Label myStatus;
    // navigation
    private TextField myURLDisplay;
    // favorites
    private ComboBox<String> myFavorites;
    // get strings from resource file
    private ResourceBundle myResources;
    // the data
    private BrowserModel myModel;


    /**
     * Create a view of the given model of a web browser.
     */
    public BrowserView (BrowserModel model, String language) {
        myModel = model;
        // use resources for labels
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        var root = new BorderPane();
        // must be first since other panels may refer to page
        root.setCenter(makePageDisplay());
        root.setTop(makeInputPanel());
        root.setBottom(makeInformationPanel());
        // create scene to hold UI
        myScene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
        // TODO: uncomment to activate CSS styling
        //myScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_PACKAGE + STYLESHEET).toExternalForm());
    }

    /**
     * Returns scene for this view so it can be added to stage.
     */
    public Scene getScene () {
        return myScene;
    }

    /**
     * Display given URL.
     */
    public void showPage (String url) {
        try {
            update(myModel.go(url));
        }
        catch (BrowserException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Display given message as information in the GUI.
     */
    public void showStatus (String message) {
        myStatus.setText(message);
    }

    /**
     * Display given message as an error in the GUI.
     */
    public void showError (String message) {
        var alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    // move to the next URL in the history
    private void next () {
        update(myModel.next());
    }

    // move to the previous URL in the history
    private void back () {
        update(myModel.back());
    }

    // change current URL to the home page, if set
    private void home () {
        showPage(myModel.getHome().toString());
    }

    // change page to favorite choice
    private void showFavorite (String favorite) {
        showPage(myModel.getFavorite(favorite).toString());
    }

    // prompt user for name of favorite to add to collection
    private void addFavorite () {
        var input = new TextInputDialog("");
        input.setTitle(myResources.getString("FavoritePromptTitle"));
        input.setContentText(myResources.getString("FavoritePrompt"));
        var response = input.showAndWait();
        // did user make a choice?
        if (response.isPresent()) {
            myModel.addFavorite(response.get());
        }
    }

    // update just the view to display given URL
    private void update (URL url) {
        myPage.getEngine().load(url.toString());
    }

    // convenience method to create HTML page display
    private Node makePageDisplay () {
        myPage = new WebView();
        // catch "browsing" events within web page
        myPage.getEngine().getLoadWorker().stateProperty().addListener(new LinkListener());
        return myPage;
    }

    // organize user's options for controlling/giving input to model
    private Node makeInputPanel () {
        var result = new VBox();
        result.getChildren().addAll(makeNavigationPanel(), makePreferencesPanel());
        return result;
    }

    // make the panel where "would-be" clicked URL is displayed
    private Node makeInformationPanel () {
        // BLANK must be non-empty or status label will not be displayed in GUI
        myStatus = new Label(BLANK);
        return myStatus;
    }

    // make buttons for setting favorites/home URLs
    private Node makePreferencesPanel () {
        var result = new HBox();
        myFavorites = new ComboBox<>();
        myFavorites.setPromptText(myResources.getString("FavoriteFirstItem"));
        myFavorites.valueProperty().addListener((o, old, neww) -> showFavorite(neww));
        myFavorites.itemsProperty().bind(myModel.getFavoritesProperty());

        result.getChildren().add(makeButton("AddFavoriteCommand", event -> addFavorite()));
        result.getChildren().add(myFavorites);
        result.getChildren().add(makeButton("SetHomeCommand", event -> myModel.setHome()));
        return result;
    }

    // make user-entered URL/text field and back/next buttons
    private Node makeNavigationPanel () {
        var result = new HBox();
        // create buttons, with their associated actions
        // old style way to do set up callback (anonymous class)
        var backButton = makeButton("BackCommand", event -> back());
        backButton.disableProperty().bind(myModel.getPreviousDisabledProperty());
        result.getChildren().add(backButton);
        // new style way to do set up callback (lambdas)
        var nextButton = makeButton("NextCommand", event -> next());
        nextButton.disableProperty().bind(myModel.getNextDisabledProperty());
        result.getChildren().add(nextButton);
        var homeButton = makeButton("HomeCommand", event -> home());
        homeButton.disableProperty().bind(myModel.getHomeDisabledProperty());
        result.getChildren().add(homeButton);
        // if user presses button or enter in text field, load/show the URL
        var showHandler = new ShowPage();
        result.getChildren().add(makeButton("GoCommand", showHandler));
        myURLDisplay = makeInputField(40, showHandler);
        myURLDisplay.textProperty().bindBidirectional(myModel.getCurrentURLProperty());
        result.getChildren().add(myURLDisplay);
        return result;
    }

    // makes a button using either an image or a label
    private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        // represent all supported image suffixes
        final var IMAGEFILE_SUFFIXES =
                String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));

        var result = new Button();
        var label = myResources.getString(property);
        if (label.matches(IMAGEFILE_SUFFIXES)) {
            result.setGraphic(new ImageView(
                                  new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PACKAGE + label))));
        }
        else {
            result.setText(label);
        }
        result.setOnAction(handler);
        return result;
    }

    // make text field for input
    private TextField makeInputField (int width, EventHandler<ActionEvent> handler) {
        var result = new TextField();
        result.setPrefColumnCount(width);
        result.setOnAction(handler);
        return result;
    }


    // display page
    // very old style way create a callback (inner class)
    private class ShowPage implements EventHandler<ActionEvent> {
        @Override
        public void handle (ActionEvent event) {
            showPage(myURLDisplay.getText());
        }
    }

    // Inner class to deal with link-clicks and mouse-overs Mostly taken from
    //   http://blogs.kiyut.com/tonny/2013/07/30/javafx-webview-addhyperlinklistener/
    private class LinkListener implements ChangeListener<State> {
        public static final String HTML_LINK = "href";
        public static final String EVENT_CLICK = "click";
        public static final String EVENT_MOUSEOVER = "mouseover";
        public static final String EVENT_MOUSEOUT = "mouseout";

        @Override
        public void changed (ObservableValue<? extends State> ov, State oldState, State newState) {
            if (newState == Worker.State.SUCCEEDED) {
                EventListener listener = event -> {
                    final var href = ((Element) event.getTarget()).getAttribute(HTML_LINK);
                    if (href != null) {
                        var domEventType = event.getType();
                        if (domEventType.equals(EVENT_CLICK)) {
                            showPage(href);
                        }
                        else if (domEventType.equals(EVENT_MOUSEOVER)) {
                            showStatus(href);
                        }
                        else if (domEventType.equals(EVENT_MOUSEOUT)) {
                            showStatus(BLANK);
                        }
                    }
                };
                var doc = myPage.getEngine().getDocument();
                var nodes = doc.getElementsByTagName("a");
                for (var i = 0; i < nodes.getLength(); i++) {
                    var node = (EventTarget)nodes.item(i);
                    node.addEventListener(EVENT_CLICK, listener, false);
                    node.addEventListener(EVENT_MOUSEOVER, listener, false);
                    node.addEventListener(EVENT_MOUSEOUT, listener, false);
                }
            }
        }
    }
}