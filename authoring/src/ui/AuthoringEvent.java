package ui;

import engine.external.events.Event;
import events.EventBuilder;
import events.EventFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import events.ValueFieldProperty;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.manager.RefreshLabels;
import ui.manager.Refresher;

import java.util.*;

public abstract class AuthoringEvent extends Stage {
    private static final String ASSOCIATED_OPTIONS_DELIMITER = "::";
    private static final String USER_ACTION_PROMPT = "Select Action...";
    private static final String ACTION_RESOURCE = "actions_display";
    private static final String CANCEL = "Cancel";

    private StringProperty componentName = new SimpleStringProperty(); //Name of the component for the conditional
    private StringProperty modifierOperator = new SimpleStringProperty(); //type of modifier such as set or scale
    private StringProperty triggerValue = new SimpleStringProperty();  //value associated with action

    abstract VBox generateEventOptions();

    public abstract void addSaveComponents(Refresher myRefresher, ObservableList<Event> myEntityEvents);

    public void saveEvent(Event createdEvent, Refresher myRefresher, ObservableList<Event> myEntityEvents){
        myEntityEvents.add(createdEvent);
        myRefresher.refresh();
    }

    public void render(){
        Scene myScene = new Scene(generateEventOptions());
        this.setScene(myScene);
        this.show();
    }

    protected Map<String, ObservableList<String>> createAssociatedOptions(String bundleName){
        Map<String, ObservableList<String>> associatedOptions = new HashMap<>();
        ResourceBundle associatedOptionsResource = ResourceBundle.getBundle(bundleName);

        for (String independentOption: getIndependentOptionsListing(associatedOptionsResource)){
            String[] dependentOptionsArray = associatedOptionsResource.getString(independentOption).split(ASSOCIATED_OPTIONS_DELIMITER);
            List<String> dependentOptionsList = Arrays.asList(dependentOptionsArray);
            associatedOptions.put(independentOption, FXCollections.observableArrayList(dependentOptionsList));
        }

        return associatedOptions;
    }

    protected HBox createActionOptions(){
        return createEventComponentOptions(USER_ACTION_PROMPT,ACTION_RESOURCE,componentName,modifierOperator,triggerValue);
    }

    protected void saveAction(Event buildingEvent){
        EventBuilder myBuilder = new EventBuilder();
        myBuilder.createGeneralAction(componentName.getValue(),modifierOperator.getValue(),triggerValue.getValue(), buildingEvent);
    }

    protected HBox createEventComponentOptions(String prompt, String resourceName, StringProperty componentName,
                                               StringProperty modifierOperator, StringProperty triggerValue){
        HBox eventComponentOptions = new HBox();
        Label userPrompt = EventFactory.createLabel(prompt);
        eventComponentOptions.getChildren().add(userPrompt);
        Map<String,ObservableList<String>> eventOperatorOptions = createAssociatedOptions(resourceName);
        ChoiceBox<String> componentChoiceBox = setUpPairedChoiceBoxes(eventOperatorOptions,componentName,modifierOperator,eventComponentOptions);


        ValueFieldProperty myTriggerControl = new ValueFieldProperty();
        triggerValue.bindBidirectional(myTriggerControl.textProperty());
        createDependencyForValueField(componentChoiceBox,myTriggerControl);
        eventComponentOptions.getChildren().add(myTriggerControl);
        return eventComponentOptions;

    }

    protected ChoiceBox<String> setUpPairedChoiceBoxes(Map<String,ObservableList<String>> actionOperatorOptions,
                                                     StringProperty controller, StringProperty dependent,HBox parent){
        List<String> componentOptions = new ArrayList<>(actionOperatorOptions.keySet());
        Collections.sort(componentOptions);
        ChoiceBox<String> componentChoiceBox = setUpBoundChoiceBox(componentOptions,controller,parent);
        ChoiceBox<String> comparatorChoiceBox = setUpBoundChoiceBox(new ArrayList<>(), dependent,parent);
        createDependencyBetweenChoiceBoxes(componentChoiceBox,comparatorChoiceBox,actionOperatorOptions);
        return componentChoiceBox;
    }

    protected void createDependencyBetweenChoiceBoxes(ChoiceBox<String> controller, ChoiceBox<String> controllee,
                                                    Map<String,ObservableList<String>> conditionOperatorOptions){
        controller.getSelectionModel().selectedItemProperty().addListener((observableEvent, oldComponent, newComponent) -> {
            controllee.setItems(FXCollections.observableList(conditionOperatorOptions.get(newComponent)));
            if (conditionOperatorOptions.get(newComponent).size() == 1){
                controllee.setValue(conditionOperatorOptions.get(newComponent).get(0));
            }
        });
    }

    protected ChoiceBox<String> setUpBoundChoiceBox(List<String> controllerOptions, StringProperty binder, HBox parent){
        ChoiceBox<String> choice = new ChoiceBox<>(FXCollections.observableArrayList(controllerOptions));
        choice.setOnAction(e -> choice.setAccessibleText(choice.getValue()));
        binder.bindBidirectional(choice.accessibleTextProperty());
        parent.getChildren().add(choice);
        return choice;
    }

    protected void createDependencyForValueField(ChoiceBox<String> controller,ValueFieldProperty valueField){
        controller.getSelectionModel().selectedItemProperty().addListener((observableEvent, oldComponent, newComponent) -> {
            valueField.clearListeners();
            valueField.addListeners(newComponent);
        });
    }

    protected HBox createToolBar(Button saveButton){
        HBox toolBar = new HBox();
        Button cancelButton = new Button(CANCEL);
        cancelButton.setOnMouseClicked(mouseEvent -> closeWindow());
        toolBar.getChildren().add(saveButton);
        toolBar.getChildren().add(cancelButton);
        return toolBar;
    }

    private void closeWindow(){
        this.close();
    }

    protected ValueFieldProperty createAssociatedControls(ChoiceBox<String> componentNames) {
        ValueFieldProperty myTextField = new ValueFieldProperty();
        componentNames.getSelectionModel().selectedItemProperty().addListener((observableValue, oldComponentName, newComponentName) -> {
            myTextField.clearListeners();
            myTextField.addListeners(newComponentName);
        });

        return null;
    }

    private List<String> getIndependentOptionsListing(ResourceBundle associatedOptionsResource){
        List<String> independentOptions = new ArrayList<>(associatedOptionsResource.keySet());
        Collections.sort(independentOptions);
        return independentOptions;
    }

}
