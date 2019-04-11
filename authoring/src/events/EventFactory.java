package events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;
import java.util.Set;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class EventFactory {
    private static final String DEFAULT_RESOURCES_NAME = "basic_event_display";
    private static final ResourceBundle DEFAULT_RESOURCES = ResourceBundle.getBundle(DEFAULT_RESOURCES_NAME);

    public static ComboBox<Button> createBoxFromResources(String resourcesBundleName){
        ResourceBundle optionsResource = ResourceBundle.getBundle(resourcesBundleName);
        Set<String> myActionsSet = optionsResource.keySet();
        List<String> myActionsList = new ArrayList<>(myActionsSet);
        Collections.sort(myActionsList);
        return createComboBox(myActionsList);

    }

    public static ComboBox<Button> createBoxFromResourcesKey(String key){
        String[] keyValues = DEFAULT_RESOURCES.getString(key).split("::");
        List<String> myKeyValues = Arrays.asList(keyValues);
        Collections.sort(myKeyValues);
        return createComboBox(myKeyValues);
    }

    private static ComboBox<Button> createComboBox(List<String> choiceBoxOptions){

        ComboBox<Button> myChoices = new ComboBox<>();
        myChoices.getStylesheets().add("default.css");
        myChoices.setPromptText(choiceBoxOptions.get(0));
        List<Button> boxInfo = new ArrayList<>();
        for (String label: choiceBoxOptions){
            boxInfo.add(new Button(label));
        }
        ObservableList<Button> myButtons = FXCollections.observableArrayList(boxInfo);
        myChoices.setItems(myButtons);
        myChoices.setValue(myButtons.get(0));
        return myChoices;
    }
    public static TextField createDisappearingLabel(String textFieldInformation){
        TextField myTextField = new TextField();
        myTextField.setPromptText(textFieldInformation);
        myTextField.setFocusTraversable(false);
        myTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    myTextField.setText("");
            }

         });
        return myTextField;
        }

    public static TextField createNumericOptions(String numericFieldInformation){
        TextField myTextField = createDisappearingLabel(numericFieldInformation);
        myTextField.textProperty().addListener(new ChangeListener<String>() {
            //This stops the user from entering any non-numeric value
            //@Todo Set a default value to help with error checking and guarantee that we will get a valid value from these fields
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    myTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        return myTextField;
    }

}
