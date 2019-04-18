package engine.external.events;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.UIException;
import ui.panes.EventPane;

import java.lang.reflect.Method;
import java.util.*;

/**
 * This is essentially a Utilities class that EventPane uses in order to display particular options and properties associated
 * with engine.external.events and engine.external.actions. This helps with reflection in creating engine.external.events, as different engine.external.events need to provide different
 * controls to the user to input information necessary to instantiate different engine.external.events and engine.external.actions
 * @see EventPane
 * @author Anna Darwish
 */
public class EventFactory {
    private static final String DEFAULT_RESOURCES_NAME = "basic_event_display";
    private static final String ACTION_RESOURCES_NAME = "actions_display";
    private static final String CONDITONS_RESOURCES = "condition_values";
    private static final String CONTROL_SEPARATOR = "::";
    private static final String PARAMETER_SEPARATOR = ",";
    private static final ResourceBundle DEFAULT_RESOURCES = ResourceBundle.getBundle(DEFAULT_RESOURCES_NAME);
    private static final ResourceBundle ACTION_RESOURCES = ResourceBundle.getBundle(ACTION_RESOURCES_NAME);

    public void factoryDelegator(String controlResources,String controlKey, Pane myParent, ArrayList<StringProperty> myBinding){
        ResourceBundle myControlsOptions = ResourceBundle.getBundle(controlResources);
        for (String controlInformation: myControlsOptions.getString(controlKey.replaceAll(" ","")).split(CONTROL_SEPARATOR)) {
            String methodName = controlInformation.substring(0, controlInformation.indexOf(PARAMETER_SEPARATOR));
            String methodParameter = controlInformation.substring(controlInformation.indexOf(PARAMETER_SEPARATOR) + 1);
            Class<?>[] myClazz = {String.class, ArrayList.class};
            Object[] myParams = {methodParameter, myBinding};
            try {
                Method m = this.getClass().getDeclaredMethod(methodName, myClazz);
                Object addedOption = m.invoke(this, myParams);
                myParent.getChildren().add((Node) addedOption);
            } catch (Exception e) {
                UIException myEventCreatorException = new UIException("Missing Event Options"); //@TODO refactor to get message from properties file
                myEventCreatorException.displayUIException();
            }
        }
    }

    public static VBox createCollisionOptions(){
        VBox collisionOptions = new VBox();
//        collisionOptions.getChildren().add(new Label("Collision Preferences"));
//
//        HBox labeledPane = new HBox();
//        labeledPane.getChildren().add(new Label("Direction: "));
//        labeledPane.getChildren().add(createBoxFromResourcesKey("Direction"));
//        collisionOptions.getChildren().add(labeledPane);
//
//        HBox entityPane = new HBox();
//        entityPane.getChildren().add(new Label("Collidee: "));
//        entityPane.getChildren().add(createChoiceBox(Arrays.asList("object1","object2","object3")));
//        collisionOptions.getChildren().add(entityPane);
//
//        HBox actionOptions = createActionsOptions();
//        collisionOptions.getChildren().add(myDirections);
//        collisionOptions.getChildren().add(myEntities);
//        collisionOptions.getChildren().add(actionOptions);
        return collisionOptions;
    }
    public static ChoiceBox<String> createBoxFromResources(String resourcesBundleName, ArrayList<StringProperty> myBinding){
        ResourceBundle optionsResource = ResourceBundle.getBundle(resourcesBundleName);
        Set<String> myActionsSet = optionsResource.keySet();
        List<String> myActionsList = new ArrayList<>(myActionsSet);
        Collections.sort(myActionsList);
        return createChoiceBox(myActionsList,myBinding);

    }
    public static ChoiceBox<String> createBoxFromResources(String resourcesBundleName, String key, ArrayList<StringProperty> myBinding){
        ResourceBundle optionsResource = ResourceBundle.getBundle(resourcesBundleName);
        String[] keyValues = optionsResource.getString(key).split("::");
        List<String> myActionsList = Arrays.asList(keyValues);
        return createChoiceBox(myActionsList,myBinding);
    }

    public static ChoiceBox<String> createBoxFromResourcesKey(String key, ArrayList<StringProperty> myBinding ){
        String[] keyValues = DEFAULT_RESOURCES.getString(key).split("::");
        List<String> myKeyValues = Arrays.asList(keyValues);
        Collections.sort(myKeyValues);
        return createChoiceBox(myKeyValues,myBinding);
    }

    public static ChoiceBox<String> createChoiceBox(List<String> choiceBoxOptions, ArrayList<StringProperty> myBinding){
        
        ObservableList<String> myObservableChoices = FXCollections.observableArrayList(choiceBoxOptions);
        ChoiceBox<String> myChoices = new ChoiceBox<>(myObservableChoices);
        myChoices.getStylesheets().add("default.css");

        myChoices.setMinSize(100,40);
        myChoices.setMaxSize(100,40);
        bindBox(myChoices,myBinding);
        return myChoices;
    }
    public static void bindBox(ChoiceBox<String> myChoices, ArrayList<StringProperty> myBinding){
        myChoices.setOnAction(e -> myChoices.setAccessibleText(myChoices.getValue()));
        StringProperty myListener = new SimpleStringProperty();
        myListener.bindBidirectional(myChoices.accessibleTextProperty());
        myBinding.add(myListener);
    }
    public static HBox createDependentComboBoxes(String independentBundle, ArrayList<StringProperty> myBinding){
        ChoiceBox<String> myControllingChoice = createBoxFromResources(independentBundle,myBinding);
        ChoiceBox<String> myDependentChoice = new ChoiceBox<>(FXCollections.observableArrayList());
        myDependentChoice.setMinSize(100,40);
        myDependentChoice.setMaxSize(100,40);
        bindBox(myDependentChoice,myBinding);
        Map<String,ObservableList> choiceSelector = new HashMap<>();
        ResourceBundle myIndependentResources = ResourceBundle.getBundle(independentBundle);
        choiceSelector.put("", FXCollections.observableArrayList());

        for (String key : ResourceBundle.getBundle(independentBundle).keySet()){
            String[] dependentOptions = myIndependentResources.getString(key).split("::");
            List dependentOptionsList = Arrays.asList(dependentOptions);
            choiceSelector.put(key,FXCollections.observableArrayList(dependentOptionsList));
        }
        myControllingChoice.getSelectionModel().selectedItemProperty().addListener((observableEvent, previousEvent, selectedEvent) ->
        {
            myDependentChoice.setItems(FXCollections.observableList(choiceSelector.get(selectedEvent)));
        });



        HBox myHBox = new HBox();
        myHBox.getChildren().add(myControllingChoice);
        myHBox.getChildren().add(myDependentChoice);
        return myHBox;
    }


    public static TextField createDisappearingLabel(String textFieldInformation, ArrayList<StringProperty> myBinding){
        TextField myTextField = new TextField();
        myTextField.setPromptText(textFieldInformation);
        myTextField.setFocusTraversable(false);
        myTextField.setOnMouseClicked(mouseEvent -> myTextField.setText(""));
        myTextField.setMinSize(100,40);
        myTextField.setMaxSize(100,40);
        StringProperty myListener = new SimpleStringProperty();
        myListener.bindBidirectional(myTextField.textProperty());
        myBinding.add(myListener);
        return myTextField;
        }


    public static HBox createActionsOptions(ArrayList<StringProperty> myActionsBinding){
        HBox myActionOptions = new HBox();
        myActionOptions.getStylesheets().add("default.css");
        myActionOptions.getChildren().add(createLabel("Enter Action - "));
        myActionOptions.getChildren().add(createDependentComboBoxes(ACTION_RESOURCES_NAME,myActionsBinding));
//        List<String> componentOptions = new ArrayList<>(ACTION_RESOURCES.keySet());
//        Collections.sort(componentOptions);
//        ChoiceBox<String> actionChoices = createChoiceBox(componentOptions,myActionsBinding);
//        ChoiceBox<String> modifyChoices = createBoxFromResourcesKey("Modifiers", myActionsBinding);
////        modifyChoices.setPromptText("Modifiers");
//        myActionOptions.getChildren().add(modifyChoices);
////        actionChoices.setPromptText("Actions");
//        myActionOptions.getChildren().add(actionChoices);
        myActionOptions.getChildren().add(createDisappearingLabel("Value",myActionsBinding));

        return myActionOptions;
    }





    public static TextField createNumericOptions(String numericFieldInformation, ArrayList<StringProperty> myBinding){
        TextField myTextField = createDisappearingLabel(numericFieldInformation,myBinding);
        myTextField.setPromptText(numericFieldInformation);
        myTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                myTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        return myTextField;
    }



    public static Label createLabel(String labelText){
        Label myLabel = new Label(labelText);
        myLabel.getStylesheets().add("default.css");
        return myLabel;
    }

    public static Label createLabel(String labelText, ArrayList<StringProperty> myBinding){
        Label myLabel = new Label(labelText);
        myLabel.getStylesheets().add("default.css");
        return myLabel;
    }

}
