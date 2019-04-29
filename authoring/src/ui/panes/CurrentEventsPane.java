package ui.panes;
import engine.external.events.Event;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import ui.manager.AddKeyCode;
import ui.manager.LabelManager;
import ui.manager.RefreshLabels;
import ui.manager.Refresher;

public class CurrentEventsPane extends ScrollPane {
    private static final String CSS_CLASS = "current-events-pane";
    private ObservableList<Event> myCurrentEvents;
    private Refresher myCurrentEventsRefresher = this::refresh;
    private VBox myEventsListing;

    private Editor myEditor;
    private Editor myRemover;
    private AddKeyCode myKeyCodeEditor;

    public CurrentEventsPane(ObservableList<Event> myEvents){
        myCurrentEvents = myEvents;

        myEventsListing = new VBox();
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        myEventsListing.getStyleClass().add(CSS_CLASS);
        myEditor = this::editCurrentEvent;
        myRemover = this::removeCurrentEvent;
        myKeyCodeEditor = (eventToModify, keycode) -> addKeyCode(eventToModify,keycode);

        for (Event event: myEvents) { //TODO workaround: check if null?
            CurrentEventDisplay currEventDisplay = new CurrentEventDisplay(event.getEventInformation(),event,myRemover,
                    myEditor, myKeyCodeEditor);
            if (currEventDisplay.getChildren().size() != 0) {
                myEventsListing.getChildren().add(currEventDisplay);
            }
        }
        this.setContent(myEventsListing);
        this.getStyleClass().add(CSS_CLASS);
        this.setFitToWidth(true);

    }

    public Refresher getRefreshEvents(){
        return myCurrentEventsRefresher;
    }

    private void refresh(){
        myEventsListing.getChildren().clear();
        for (Event event: myCurrentEvents) {
            CurrentEventDisplay currEventDisplay = new CurrentEventDisplay(event.getEventInformation(),event,myRemover,
                    myEditor, myKeyCodeEditor);
            if (currEventDisplay.getChildren().size() != 0) {
                myEventsListing.getChildren().add(currEventDisplay);
            }
        }
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

    private void addKeyCode(Event unfinishedEvent, KeyCode keyCode){
        unfinishedEvent.clearInputs();
        unfinishedEvent.addInputs(keyCode);
    }

}
