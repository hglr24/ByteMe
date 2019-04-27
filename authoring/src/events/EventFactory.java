package events;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ui.EntityField;
import ui.manager.LabelManager;
import voogasalad.util.reflection.Reflection;
import java.util.*;

/**
 * This is essentially a Utilities class that EventPane uses in order to display particular options and properties associated
 * with engine.external.events and engine.external.actions. This helps with reflection in creating engine.external.events, as different engine.external.events need to provide different
 * controls to the user to input information necessary to instantiate different engine.external.events and engine.external.actions
 * @author Anna Darwish
 */
public class EventFactory {
    private static final String DEFAULT_RESOURCES_NAME = "basic_event_display";
    private static final String KEY_CODE_DELIMITER = "#";
    private static final String CONTROL_SEPARATOR = "::";
    private static final String PARAMETER_SEPARATOR = ",";
    private static final ResourceBundle DEFAULT_RESOURCES = ResourceBundle.getBundle(DEFAULT_RESOURCES_NAME);


    public void factoryDelegator(String controlResources,String controlKey, Pane myParent, Map<String,StringProperty> myBinding){
        ResourceBundle myControlsOptions = ResourceBundle.getBundle(controlResources);
        for (String controlInformation: myControlsOptions.getString(controlKey.replaceAll(" ","")).split(CONTROL_SEPARATOR)) {
            String keyCode = controlInformation.substring(0,controlInformation.indexOf(KEY_CODE_DELIMITER));
            String factoryInformation = controlInformation.substring(controlInformation.indexOf(KEY_CODE_DELIMITER)+1);
            String methodName = factoryInformation.substring(0, factoryInformation.indexOf(PARAMETER_SEPARATOR));
            String methodParameter = factoryInformation.substring(factoryInformation.indexOf(PARAMETER_SEPARATOR) + 1);
            myParent.getChildren().add((Node) Reflection.callMethod(this,methodName,methodParameter,keyCode,myBinding));
        }
    }

    public static ChoiceBox<String> createBoxFromResources(String resourcesBundleName, String myInformationKey, Map<String,StringProperty> myBinding){
        ResourceBundle optionsResource = ResourceBundle.getBundle(resourcesBundleName);
        Set<String> myActionsSet = optionsResource.keySet();
        List<String> myActionsList = new ArrayList<>(myActionsSet);
        Collections.sort(myActionsList);
        return createChoiceBox(myActionsList,myInformationKey, myBinding);

    }
    public static ChoiceBox<String> createBoxFromResources(String resourcesBundleName, String key, String myInformationKey,
                                                           Map<String,StringProperty> myBinding){
        ResourceBundle optionsResource = ResourceBundle.getBundle(resourcesBundleName);
        String[] keyValues = optionsResource.getString(key).split("::");
        List<String> myActionsList = Arrays.asList(keyValues);
        return createChoiceBox(myActionsList,myInformationKey,myBinding);
    }

    public static ChoiceBox<String> createBoxFromResourcesKey(String key, String myInformationKey,Map<String,StringProperty> myBinding ){
        String[] keyValues = DEFAULT_RESOURCES.getString(key).split("::");
        List<String> myKeyValues = Arrays.asList(keyValues);
        Collections.sort(myKeyValues);
        return createChoiceBox(myKeyValues,myInformationKey,myBinding);
    }

    public static ChoiceBox<String> createChoiceBox(List<String> choiceBoxOptions, String myInformationKey, Map<String,StringProperty> myBinding){
        
        ObservableList<String> myObservableChoices = FXCollections.observableArrayList(choiceBoxOptions);
        ChoiceBox<String> myChoices = new ChoiceBox<>(myObservableChoices);
        myChoices.getStylesheets().add("default.css");

        myChoices.setMinSize(100,40);
        myChoices.setMaxSize(100,40);
        bindBox(myChoices,myInformationKey, myBinding);
        return myChoices;
    }
    public static void bindBox(ChoiceBox<String> myChoices, String myInformationKey, Map<String,StringProperty> myBinding){
        myChoices.setOnAction(e -> myChoices.setAccessibleText(myChoices.getValue()));
        StringProperty myListener = new SimpleStringProperty();
        myListener.bindBidirectional(myChoices.accessibleTextProperty());
        myBinding.put(myInformationKey,myListener);
    }
    public static HBox createDependentComboBoxes(String independentBundle, String myKeys,
                                                 Map<String,StringProperty> myBinding){
        String myIndependentKey = myKeys.substring(0,myKeys.indexOf("%"));
        String myDependentKey = myKeys.substring(myKeys.indexOf("%") + 1);
        ResourceBundle myIndependentResources = ResourceBundle.getBundle(independentBundle);
        Map<String,ObservableList> choiceSelector = new HashMap<>();
        choiceSelector.put("", FXCollections.observableArrayList());
        for (String key : ResourceBundle.getBundle(independentBundle).keySet()){
            String[] dependentOptions = myIndependentResources.getString(key).split("::");
            List dependentOptionsList = Arrays.asList(dependentOptions);
            choiceSelector.put(key,FXCollections.observableArrayList(dependentOptionsList));
        }

        ChoiceBox<String> myControllingChoice = createBoxFromResources(independentBundle,myIndependentKey,myBinding);
        ChoiceBox<String> myDependentChoice = new ChoiceBox<>(FXCollections.observableArrayList());
        myDependentChoice.setMinSize(100,40);
        myDependentChoice.setMaxSize(100,40);
        bindBox(myDependentChoice,myDependentKey,myBinding);
        return makeChoiceBoxesDependent(choiceSelector, myControllingChoice,myDependentChoice);
    }

    public static HBox createInteractionOptions(String keyOptionsBundle, String[] myKeys, LabelManager myDisplayOptions, Map<String,StringProperty> myBinding){
        ResourceBundle independentBundle = ResourceBundle.getBundle(keyOptionsBundle);
        List<String> interactiveTypes = new ArrayList<>(independentBundle.keySet());
        Collections.sort(interactiveTypes);
        Map<String,ObservableList> choiceSelector = new HashMap<>();
        choiceSelector.put("", FXCollections.observableArrayList());
        for (String interactive: interactiveTypes){
            List<String> myInteractiveList = myDisplayOptions.getLabels(EntityField.valueOf(interactive));
            choiceSelector.put(interactive,FXCollections.observableArrayList(myInteractiveList));
        }
        ChoiceBox<String> myControllingChoice = new ChoiceBox<>(FXCollections.observableArrayList(interactiveTypes));
        ChoiceBox<String> myDependentChoice = new ChoiceBox<>(FXCollections.observableArrayList());
        bindBox(myControllingChoice,myKeys[0],myBinding);
        bindBox(myDependentChoice,myKeys[1],myBinding);
        return makeChoiceBoxesDependent(choiceSelector,myControllingChoice,myDependentChoice);


    }

    private static HBox makeChoiceBoxesDependent(Map<String,ObservableList> choiceSelector, ChoiceBox<String> myControllingChoice, ChoiceBox<String> myDependentChoice){

        myControllingChoice.getSelectionModel().selectedItemProperty().addListener((observableEvent, previousEvent, selectedEvent) ->

            myDependentChoice.setItems(FXCollections.observableList(choiceSelector.get(selectedEvent)))
        );
        HBox myHBox = new HBox();
        myHBox.getChildren().add(myControllingChoice);
        myHBox.getChildren().add(myDependentChoice);
        return myHBox;

    }


    public static TextField createDisappearingLabel(String textFieldInformation, String myKeyInformation, Map<String,StringProperty> myBinding){
        TextField myTextField = new TextField();
        myTextField.setPromptText(textFieldInformation);
        myTextField.setFocusTraversable(false);
        myTextField.setOnMouseClicked(mouseEvent -> myTextField.setText(""));
        myTextField.setMinSize(100,40);
        myTextField.setMaxSize(100,40);
        StringProperty myListener = new SimpleStringProperty();
        myListener.bindBidirectional(myTextField.textProperty());
        myBinding.put(myKeyInformation,myListener);
        return myTextField;
        }


    public static TextField createNumericOptions(String numericFieldInformation, String valueKey, Map<String,StringProperty> myBinding){
        TextField myTextField = createDisappearingLabel(numericFieldInformation, valueKey,myBinding);
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

    public static Label createLabel(String labelText, String keyCode, Map<String,StringProperty> myBinding){
        Label myLabel = new Label(labelText);
        myLabel.getStylesheets().add("default.css");
        return myLabel;
    }

}
