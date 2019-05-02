package events;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.util.*;

/**
 * This is essentially a Utilities class that EventPane uses in order to display particular options and properties associated
 * with engine.external.events and engine.external.actions. This helps with reflection in creating engine.external.events, as different engine.external.events need to provide different
 * controls to the user to input information necessary to instantiate different engine.external.events and engine.external.actions
 * @author Anna Darwish
 */
public class EventFactory {
    private static final String ASSOCIATED_OPTIONS_DELIMITER = "::";

    private static List<String> getIndependentOptionsListing(ResourceBundle associatedOptionsResource){
        List<String> independentOptions = new ArrayList<>(associatedOptionsResource.keySet());
        Collections.sort(independentOptions);
        return independentOptions;
    }

    private static Map<String, ObservableList<String>> createAssociatedOptions(String bundleName){
        Map<String, ObservableList<String>> associatedOptions = new HashMap<>();
        ResourceBundle associatedOptionsResource = ResourceBundle.getBundle(bundleName);

        for (String independentOption: getIndependentOptionsListing(associatedOptionsResource)){
            String[] dependentOptionsArray = associatedOptionsResource.getString(independentOption).split(ASSOCIATED_OPTIONS_DELIMITER);
            List<String> dependentOptionsList = Arrays.asList(dependentOptionsArray);
            associatedOptions.put(independentOption, FXCollections.observableArrayList(dependentOptionsList));
        }

        return associatedOptions;
    }

    public static HBox createEventComponentOptions(String prompt, String resourceName, StringProperty componentName,
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
        eventComponentOptions.getStyleClass().add("event-options");
        return eventComponentOptions;

    }

    public static ChoiceBox<String> setUpPairedChoiceBoxes(Map<String,ObservableList<String>> actionOperatorOptions,
                                                       StringProperty controller, StringProperty dependent,HBox parent){
        List<String> componentOptions = new ArrayList<>(actionOperatorOptions.keySet());
        Collections.sort(componentOptions);
        ChoiceBox<String> componentChoiceBox = setUpBoundChoiceBox(componentOptions,controller,parent);
        ChoiceBox<String> comparatorChoiceBox = setUpBoundChoiceBox(new ArrayList<>(), dependent,parent);
        createDependencyBetweenChoiceBoxes(componentChoiceBox,comparatorChoiceBox,actionOperatorOptions);
        return componentChoiceBox;
    }

    private static void createDependencyBetweenChoiceBoxes(ChoiceBox<String> controller, ChoiceBox<String> controllee,
                                                      Map<String,ObservableList<String>> conditionOperatorOptions){
        controller.getSelectionModel().selectedItemProperty().addListener((observableEvent, oldComponent, newComponent) -> {
            controllee.setItems(FXCollections.observableList(conditionOperatorOptions.get(newComponent)));
            if (conditionOperatorOptions.get(newComponent).size() == 1){
                controllee.setValue(conditionOperatorOptions.get(newComponent).get(0));
            }
        });
    }

    private static ChoiceBox<String> setUpBoundChoiceBox(List<String> controllerOptions, StringProperty binder, HBox parent){
        ChoiceBox<String> choice = new ChoiceBox<>(FXCollections.observableArrayList(controllerOptions));
        choice.setOnAction(e -> choice.setAccessibleText(choice.getValue()));
        binder.bindBidirectional(choice.accessibleTextProperty());
        parent.getChildren().add(choice);
        return choice;
    }

    private static void createDependencyForValueField(ChoiceBox<String> controller,ValueFieldProperty valueField){
        controller.getSelectionModel().selectedItemProperty().addListener((observableEvent, oldComponent, newComponent) -> {
            valueField.clearListeners();
            valueField.addListeners(newComponent);
        });
    }


    public static Label createLabel(String labelText){
        Label myLabel = new Label(labelText);
        myLabel.getStylesheets().add("default.css");
        return myLabel;
    }


}
