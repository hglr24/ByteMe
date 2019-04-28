package ui.panes;
import engine.external.events.Event;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import ui.manager.LabelManager;
import ui.manager.RefreshLabels;
import ui.manager.Refresher;

public class CurrentEventsPane extends ScrollPane {
    private static final String CSS_CLASS = "current-events-pane";
    private ObservableList<Event> myCurrentEvents;
    private Refresher myCurrentEventsRefresher;

    public CurrentEventsPane(ObservableList<Event> myEvents, Refresher eventsRefresher){
        myCurrentEvents = myEvents;
        myCurrentEventsRefresher = eventsRefresher;

        VBox myEventsListing = new VBox();
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        myEventsListing.getStyleClass().add(CSS_CLASS);
        Editor myEditor = this::editCurrentEvent;
        Editor myRemover = this::removeCurrentEvent;

        for (Event event: myEvents) {
            CurrentEventDisplay currEventDisplay = new CurrentEventDisplay(event.getEventInformation(),event,myRemover,
                    myEditor);
            if (currEventDisplay.getChildren().size() != 0) {
                myEventsListing.getChildren().add(currEventDisplay);
            }
        }
        this.setContent(myEventsListing);
        this.getStyleClass().add(CSS_CLASS);
        this.setFitToWidth(true);

    }

    private void editCurrentEvent(Event unfinishedEvent){
        EventEditorPane myPane = new EventEditorPane(unfinishedEvent,myCurrentEventsRefresher);
        myPane.initModality(Modality.WINDOW_MODAL);
        myPane.show();
    }

    private void removeCurrentEvent(Event obsoleteEvent){
        myCurrentEvents.remove(obsoleteEvent);
        myCurrentEventsRefresher.refresh();
    }

}
