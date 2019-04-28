package runner.internal.browser_src.browser;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * This represents the heart of the browser: the collections
 * that organize all the URLs into useful structures.
 * 
 * @author Robert C. Duvall
 */
public class BrowserModel {
    // constants
    public static final String DEFAULT_RESOURCES = "resources/Errors";
    // properties
    private SimpleStringProperty myCurrentURLProperty;
    private SimpleIntegerProperty myCurrentIndexProperty;
    private SimpleObjectProperty<URL> myHomeProperty;
    private SimpleObjectProperty<ObservableList<String>> myFavoritesProperty;
    private SimpleBooleanProperty myPreviousDisabledProperty;
    private SimpleBooleanProperty myNextDisabledProperty;
    private SimpleBooleanProperty myHomeDisabledProperty;
    // state
    private URL myCurrentURL;
    private int myCurrentIndex;
    private List<URL> myHistory;
    private Map<String, URL> myFavorites;
    // get strings from resource file
    private ResourceBundle myResources;


    /**
     * Creates an empty model.
     */
    public BrowserModel (String language) {
        myCurrentURL = null;
        myCurrentIndex = -1;
        myHistory = new ArrayList<>();
        myFavorites = new HashMap<>();
        // use resources for errors
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCES + language);
        // setup properties
        initProperties();
    }

    /**
     * Returns the first page in next history, null if next history is empty.
     */
    public URL next () throws BrowserException {
        return move("NoNext", 1);
    }

    /**
     * Returns the first page in back history, null if back history is empty.
     */
    public URL back () throws BrowserException {
        return move("NoPrevious", -1);
    }

    /**
     * Changes current page to given URL, removing next history.
     */
    public URL go (String url) throws BrowserException {
        try {
            var tmp = completeURL(url);
            // unfortunately, completeURL may not have returned a valid URL, so test it
            tmp.openStream();
            // if successful, remember this URL
            updateCurrentURL(tmp);
            if (hasNext()) {
                myHistory = myHistory.subList(0, myCurrentIndex + 1);
            }
            myHistory.add(myCurrentURL);
            updateCurrentIndex(myCurrentIndex + 1);
            return myCurrentURL;
        }
        catch (Exception e) {
            throw new BrowserException(e, myResources.getString("NoLoad"), url);
        }
    }

    /**
     * Returns URL of the current home page or null if none is set.
     */
    public URL getHome () {
        return myHomeProperty.get();
    }

    /**
     * Sets current home page to the current URL being viewed.
     */
    public void setHome () {
        // just in case, might be called before a page is visited
        if (myCurrentURL != null) {
            myHomeProperty.set(myCurrentURL);
        }
    }

    /**
     * Adds current URL being viewed to favorites collection with given name.
     */
    public void addFavorite (String name) throws BrowserException {
        if (myCurrentURL != null && name != null && !name.equals("")) {
            myFavorites.put(name, myCurrentURL);
            // update property as well
            myFavoritesProperty.getValue().add(name);
        }
        else {
            throw new BrowserException(myResources.getString("BadFavorite"), name);
        }
    }

    /**
     * Returns URL from favorites associated with given name, null if none set.
     */
    public URL getFavorite (String name) throws BrowserException {
        // just in case, might be called before a page is visited
        if (name != null && !name.equals("") && myFavorites.containsKey(name)) {
            return myFavorites.get(name);
        }
        else {
            throw new BrowserException(myResources.getString("BadFavorite"), name);
        }
    }


    /**
     * Returns property for binding by the View.
     */
    public SimpleStringProperty getCurrentURLProperty () {
        return myCurrentURLProperty;
    }

    /**
     * Returns property for binding by the View.
     */
    public SimpleBooleanProperty getPreviousDisabledProperty () {
        return myPreviousDisabledProperty;
    }

    /**
     * Returns property for binding by the View.
     */
    public SimpleBooleanProperty getNextDisabledProperty () {
        return myNextDisabledProperty;
    }

    /**
     * Returns property for binding by the View.
     */
    public SimpleBooleanProperty getHomeDisabledProperty () {
        return myHomeDisabledProperty;
    }

    /**
     * Returns property for binding by the View.
     */
    public SimpleObjectProperty<ObservableList<String>> getFavoritesProperty () {
        return myFavoritesProperty;
    }


    // returns true if there is a next URL available
    private boolean hasNext () {
        return myCurrentIndex < (myHistory.size() - 1);
    }

    // returns true if there is a previous URL available
    private boolean hasPrevious () {
        return myCurrentIndex > 0;
    }

    // set up properties for binding by the View
    private void initProperties() {
        myPreviousDisabledProperty = new SimpleBooleanProperty(true);
        myNextDisabledProperty = new SimpleBooleanProperty(true);
        myHomeDisabledProperty = new SimpleBooleanProperty(true);
        myCurrentIndexProperty = new SimpleIntegerProperty(myCurrentIndex);
        myCurrentIndexProperty.addListener((o, old, neww) -> {
            myPreviousDisabledProperty.set(! hasPrevious());
            myNextDisabledProperty.set(! hasNext());
        });
        myCurrentURLProperty = new SimpleStringProperty(null);
        myHomeProperty = new SimpleObjectProperty<>(null);
        myHomeProperty.addListener((o, old, neww) -> myHomeDisabledProperty.set(myHomeProperty.get() == null));
        myFavoritesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    }

    // update both the instance variable and its property
    private void updateCurrentIndex (int newIndex) {
        myCurrentIndex = newIndex;
        myCurrentIndexProperty.set(newIndex);
    }

    // update both the instance variable and its property
    private void updateCurrentURL (URL url) {
        myCurrentURL = url;
        myCurrentURLProperty.set(url.toString());
    }

    // move through the history, checking for possible error
    private URL move (String msgKey, int direction) throws BrowserException {
        var nextIndex = myCurrentIndex + direction;
        if (0 <= nextIndex && nextIndex < myHistory.size()) {
            updateCurrentIndex(nextIndex);
            updateCurrentURL(myHistory.get(nextIndex));
            return myCurrentURL;
        }
        else {
            throw new BrowserException(myResources.getString(msgKey));
        }
    }

    // deal with a potentially incomplete URL
    private URL completeURL (String possible)  throws MalformedURLException {
        final var PROTOCOL_PREFIX = "http://";
        try {
            // try it as is
            return new URL(possible);
        }
        catch (MalformedURLException e) {
            try {
                // e.g., let user leave off initial protocol
                return new URL(PROTOCOL_PREFIX + possible);
            }
            catch (MalformedURLException ee) {
                try {
                    // try it as a relative link
                    // FIXME: need to generalize this :(
                    return new URL(myCurrentURL.toString() + "/" + possible);
                }
                catch (MalformedURLException eee) {
                    // catch not needed, but nice to be explicit
                    throw eee;
                }
            }
        }
    }
}
