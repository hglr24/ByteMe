package events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class EventFactory {
    private static final String ACTION_NAME = "actions_display";
    private static final ResourceBundle ACTION_RESOURCES = ResourceBundle.getBundle(ACTION_NAME);

    public static ComboBox<Button> createActionBox(ArrayList<String> tellAnnaToReplace){
        Set<String> myActionsSet = ACTION_RESOURCES.keySet();
        ArrayList<String> myActionsList = new ArrayList<>();
        for (String s: myActionsSet){
            myActionsList.add(s.replaceAll(","," "));
        }
        Collections.sort(myActionsList);
        return createComboBox(myActionsList);
    }



    public static ComboBox<Button> createComboBox(ArrayList<String> choiceBoxOptions){
        ComboBox<Button> myChoices = new ComboBox<>();
        myChoices.setPromptText(choiceBoxOptions.get(0));
        ArrayList<Button> boxInfo = new ArrayList<>();
        choiceBoxOptions.remove(0);
        for (String label: choiceBoxOptions){
            boxInfo.add(new Button(label));
        }
        ObservableList<Button> myButtons = FXCollections.observableArrayList(boxInfo);
        myChoices.setItems(myButtons);
        return myChoices;
    }
    public static TextField createDisappearingLabel(ArrayList<String> textFieldInformation){
        TextField myTextField = new TextField(textFieldInformation.get(0));
        myTextField.setPromptText(textFieldInformation.get(0));
        myTextField.setFocusTraversable(false);
        myTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    myTextField.setText("");
            }

         });
        return myTextField;
        }

    public static TextField createNumericOptions(ArrayList<String> myBounds){
        TextField textField = new TextField(myBounds.get(0));
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        return textField;
    }

}
