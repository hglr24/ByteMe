package ui.panes;
import events.Event;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ui.manager.Refresher;

import java.util.HashMap;
import java.util.Map;

public class CurrentEventsPane extends ScrollPane {
    private static final String CSS_STYLE_SHEET = "default.css";
    private Map<Event,CurrentEventDisplay> myCurrentDisplays = new HashMap<>();
    ObservableList<Event> myCurrentEvents;
    private Editor myEditor = eventToBeEdited -> {
    };
    private Editor myRemover = eventToBeRemoved -> removeCurrentEvent(eventToBeRemoved);
    private Refresher myCurrentEventsRefresher;
    public CurrentEventsPane(ObservableList<Event> myEvents, Refresher eventsRefresher){
        myCurrentDisplays.clear();
        myCurrentEvents = myEvents;
        myCurrentEventsRefresher = eventsRefresher;
        VBox myEventsListing = new VBox();
        this.setMinHeight(630);
        this.setMinWidth(800);
        this.setMaxHeight(630);
        this.setMaxWidth(800);
        myEventsListing.getStylesheets().add(CSS_STYLE_SHEET);
        for (Event event: myEvents) {
            CurrentEventDisplay currEventDisplay = new CurrentEventDisplay(event.getEventInformation(),event,myRemover);
            myCurrentDisplays.put(event,currEventDisplay);
            myEventsListing.getChildren().add(currEventDisplay);
        }
        this.setContent(myEventsListing);

    }

    public void editCurrentEvent(Event unfinishedEvent){

    }

    public void removeCurrentEvent(Event obsoleteEvent){
        myCurrentEvents.remove(obsoleteEvent);
        myCurrentEventsRefresher.refresh();
    }

}
