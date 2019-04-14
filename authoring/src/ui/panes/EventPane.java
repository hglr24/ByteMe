package ui.panes;
import actions.Action;
import actions.HealthAction;
import actions.NumericAction;
import events.Event;
import events.EventFactory;
import events.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.UIException;
import ui.Utility;
import ui.manager.Refresher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
/**
 * The EventPane displays a list of possible events that user may add. Upon selection of one of these options,
 * a window will pop up specific to that particular action for the user to input values needed to instantiate such
 * an event, which is then tied to the current entity of concern. Additionally, this pane will use reflection to
 * also instantiate Actions that the entity in focus will perform when the Event occurs while the game is running
 * @see EventPane
 * @author Anna Darwish
 */
public class EventPane extends Stage {

    private static final String EVENT_PARAMETERS = "event_parameters";
    private static final String EVENT_CLASS_NAME = "event_classes";
    private static final String EVENT_CONSTRUCTORS = "event_constructors";
    private static final String ACTION_LISTING = "actions_display";
    private static final String GET_VALUE_METHOD = "getValue";
    private static final String GET_TEXT_METHOD = "getText";
    private static final String INVALID_EVENT_EXCEPTION = "This event is not accessible to users";

    private String myEventName;
    private String myClassName;
    private EventFactory myEventFactory = new EventFactory();
    private VBox myEventParameters = new VBox();
    private Object[] eventParameters;
    VBox myEventDisplay;


    public EventPane(String eventDisplayName, String actorName,
                     ObservableList<Event> myEvents, Refresher refreshEventOptions) throws UIException{
        myEventName = eventDisplayName.replaceAll(" ","");
        myClassName = ResourceBundle.getBundle(EVENT_CLASS_NAME).getString(myEventName);
        
        String eventDisplayOptions = ResourceBundle.getBundle(EVENT_PARAMETERS).getString(myEventName);
        setUpEvent(eventDisplayOptions, actorName);

        myEventDisplay = new VBox();
        VBox myActions = setUpActions();
        VBox myEventButtons = new VBox();

        myEventDisplay.getChildren().add(myEventParameters);
        myEventButtons.getChildren().add(makeButtons(myEvents,refreshEventOptions));

        myEventDisplay.getChildren().add(myActions);
        myEventDisplay.getChildren().add(myEventButtons);
        myEventDisplay.getStylesheets().add("default.css");

        Scene myScene = new Scene(myEventDisplay);
        this.setScene(myScene);
        this.show();
    }

    private VBox setUpActions(){
        VBox actionsPanel = new VBox();
        ComboBox<Button> myActionsListing = myEventFactory.createBoxFromResources(ACTION_LISTING);
//        myActionsListing.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                displayActionOptions(myActionsListing.getValue().getText());
//            }
//        });
//        List<Label> simplisticActionsListing = new ArrayList<>();
//        for (Button b: myActionsListing.getItems()) {
//            Label currentLabel = new Label(b.getText());
//            currentLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    displayActionOptions("hi");
//                }
//            });
//            simplisticActionsListing.add(currentLabel);
//        }
//        ComboBox<Label> simplisticActions = new ComboBox<Label>(FXCollections.observableList(simplisticActionsListing));
        actionsPanel.getChildren().add(myActionsListing);
        return actionsPanel;

    }

    private void manageVisibilityOfButtons(VBox parentPanel, VBox innerPanel, Bounds intermediaryPanel ){
        if ( innerPanel.isVisible()) {
            parentPanel.getChildren().remove(innerPanel);
        }
        else {
            parentPanel.getChildren().add(innerPanel);
            innerPanel.setVisible(true);
        }
    }

    private void displayActionOptions(String actionSelected){
        VBox actionOptions = new VBox();

        HBox modifyValue = new HBox();
        modifyValue.getChildren().add(new Label("Modifier: "));
        ChoiceBox<String> modifierOptions = new ChoiceBox<>();
        List<String> modifierStrings = Arrays.asList("Set To","Scale By","Change By");
        modifierOptions.setItems(FXCollections.observableArrayList(modifierStrings));

        actionOptions.getChildren().add(modifierOptions);
        actionOptions.getChildren().add(new Spinner<Double>(-100,100,1.0));
        actionOptions.getChildren().add(new Button("Save"));
        Stage smallActionOptions = new Stage();
        smallActionOptions.setScene(new Scene(actionOptions));
        actionOptions.getStylesheets().add("default.css");

        smallActionOptions.show();

    }



    private void setUpEvent(String eventDisplayOptions, String actorName) throws UIException {
        eventParameters = new Object[eventDisplayOptions.split(",").length + 1];
        eventParameters[0] = actorName;
        int paramCounter = 1;
        for (String nodeType: eventDisplayOptions.split(",")) {
            String methodName = nodeType.substring(0,nodeType.indexOf("::"));
            String methodParameter = nodeType.substring(nodeType.indexOf("::") + 2);
            try {
                Method m = myEventFactory.getClass().getDeclaredMethod(methodName, String.class);
                Object[] paramMethod = {methodParameter};
                Object addedOption = m.invoke(myEventFactory,paramMethod);
                myEventParameters.getChildren().add((Node)addedOption);
                if (addedOption instanceof ComboBox<?>) {
                    eventParameters[paramCounter] = ((ComboBox<?>)addedOption).getValue().toString();
                }
                if (addedOption instanceof TextField)
                    eventParameters[paramCounter] = ((TextField)addedOption).getText();
                paramCounter++;
            }
            catch (Exception e){
                throw new UIException(INVALID_EVENT_EXCEPTION);
            }

        }
    }

    private Node makeButtons(ObservableList<Event> userMadeEvents, Refresher refreshOptions){
        Button mySaveButton = new Button("Save");
        mySaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateUserEvent(userMadeEvents);
                refreshOptions.refresh();
                closeThisPane();
            }
        });
        Button myCancelButton = new Button("Cancel");
        myCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                closeThisPane();
            }
        });

        return Utility.createButtonBar(Arrays.asList(mySaveButton,myCancelButton));
    }
    private Object getParameterValue(Node param){
        try {
            Method m = param.getClass().getDeclaredMethod(GET_VALUE_METHOD);
            return m.invoke(param);

        } catch (Exception e) {
            try {
                Method m = param.getClass().getDeclaredMethod(GET_TEXT_METHOD);
                return m.invoke(param);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return "";
    }

    private void generateUserEvent(ObservableList<Event> userMadeEvents){
        Class<?>[] constructorClassReferences = EventType.valueOf(myEventName.toUpperCase()).getConstructorTypes();
        try {
            Class eventClass = EventType.valueOf(myEventName.toUpperCase()).getClassName();
            Constructor eventConstructor = eventClass.getConstructor(constructorClassReferences);
            Event userMadeEvent = (Event) eventConstructor.newInstance(eventParameters);
            Action associatedAction = generateUserAction();
            userMadeEvents.add(userMadeEvent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private Action generateUserAction() {
        return new HealthAction(NumericAction.ModifyType.RELATIVE,-1.0);
    }
    private void closeThisPane(){
        this.close();
    }

}
