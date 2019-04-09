package ui;

import engine.external.Entity;
import engine.external.component.HeightComponent;
import engine.external.component.SpriteComponent;
import engine.external.component.WidthComponent;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.panes.ImageWithEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Carrie
 * @author Harry Ross
 * This class was created to provide general methods that can be used across the UI.
 * It's a place to hold reflection code that may be needed across multiple classes.
 */
public class Utility {

    private static final String RESOURCE = "utility";
    private static final String DEFAULT_STYLESHEET = "default.css";

    /**
     * Creates and returns a Button
     * If the reflection fails for the eventHandler, when the button is pressed, the text
     * on the button will change to the text associated with the utility.properties file under the
     * key "ButtonFail". This prevents the program from ever crashing
     * @param o The class that this method is being called in
     *          Ex. If one were making a button in the AssetManager, you would call this method
     *          Utility.makeButton(this, "closeWindow", "Close");
     * @param methodName Name of the method to be called when the button is pressed
     * @param buttonText Text to be displayed on the button
     * @return A Button with the above parameters
     */
    public static Button makeButton(Object o, String methodName, String buttonText){
        ResourceBundle resources = ResourceBundle.getBundle(RESOURCE);
        Button button = new Button();
        button.setOnMouseClicked(e -> {
            try {
                Method buttonMethod = o.getClass().getDeclaredMethod(methodName);
                buttonMethod.setAccessible(true);
                buttonMethod.invoke(o);
            } catch (Exception e1) {
                e1.printStackTrace();
                button.setText(resources.getString("ButtonFail"));
            }});
        button.setText(buttonText);
        return button;
    }

    public static Button makeButton(Object o, String methodName, String buttonText, Object... methodParams){
        ResourceBundle resources = ResourceBundle.getBundle(RESOURCE);
        Button button = new Button();
        Method reference = findMethod(o, methodName, methodParams);
        button.setOnAction(e -> {
            try {
                reference.setAccessible(true);
                reference.invoke(o, methodParams);
            } catch (Exception e1) {
                button.setText(resources.getString("ButtonFail"));
            }});
        button.setText(buttonText);
        return button;
    }

    /**
     * Locates method with signature corresponding with given parameters, including superclasses, returns reference
     * to method -- important shortcoming, cannot distinguish between overloaded methods with same superclass as parameter
     * @param o Object to locate found method within class of
     * @param methodName String name of method to locate
     * @param methodParams Parameters to be used in search for correct method signature
     * @return Reference to found method
     */
    private static Method findMethod(Object o, String methodName, Object[] methodParams) {
        Method reference = null;
        for (Method m : o.getClass().getDeclaredMethods()) {
            if (m.getName().equals(methodName) && m.getParameterCount() == methodParams.length) {
                boolean matchesSignature = true;
                for (int i = 0; i < methodParams.length; i++) {
                    if (!m.getParameterTypes()[i].isAssignableFrom(methodParams[i].getClass())) {
                        matchesSignature = false;
                        break;
                    }
                }
                if (matchesSignature) {
                    reference = m;
                    break;
                }
            }
        }
        return reference;
    }

    /**
     * Check validity of new value from based on regex syntax from properties file
     */
    public static boolean isValidValue(Enum key, String newVal, String syntaxResource) {
        ResourceBundle bundle = ResourceBundle.getBundle(syntaxResource);
        if (bundle.containsKey(key.name())) { // Label matches syntax, valid
            if (newVal.matches(bundle.getString(key.name()))) {
                return true;
            } else {
                ErrorBox error = new ErrorBox("Variable Error", "Invalid variable, refer to documentation for syntax");
                error.display();
            }
        }
        return false;
    }

    public static Scene createGeneralPane(Node header, Node content, List<Node> myNodes){
        HBox userControls = new HBox();
        userControls.getChildren().addAll(myNodes);
        BorderPane borderPane = new BorderPane(content, header, null, userControls, null);
        borderPane.getStylesheets().add("default.css");
        borderPane.getStyleClass().add("dialog-window");
        borderPane.getCenter().getStyleClass().add("center-pane");
        borderPane.getTop().getStyleClass().add("top-pane");
        return new Scene(borderPane);

    }

    public static Scene createDialogPane(Node header, Node content, List<Button> buttonsList) {
        if (header == null)
            header = new HBox();
        if (content == null)
            content = new HBox();

        BorderPane borderPane = new BorderPane(content, header, null, createButtonBar(buttonsList), null);
        Scene scene = new Scene(borderPane);

        scene.getStylesheets().add(DEFAULT_STYLESHEET);
        borderPane.getStyleClass().add("dialog-window");
        borderPane.getCenter().getStyleClass().add("center-pane");
        borderPane.getTop().getStyleClass().add("top-pane");

        return scene;
    }

    public static Node createButtonBar(List<Button> buttonList) {
        HBox rtn = new HBox();
        rtn.getChildren().addAll(buttonList);
        rtn.getStyleClass().add("buttons-bar");
        if (!buttonList.isEmpty())
            Platform.runLater(() -> buttonList.get(0).requestFocus());
        return rtn;
    }

    /**
     * Passed a Map where keys are CSS selectors for label and values are label text
     */
    public static Node createLabelsGroup(Map<String, List<String>> labels) {
        VBox labelBox = new VBox();
        for (String labelType : labels.keySet()) {
            for (String newLabelText : labels.get(labelType)) {
                Label newLabel = new Label(newLabelText);
                newLabel.getStyleClass().add(labelType);
                labelBox.getChildren().add(newLabel);
            }
        }
        return labelBox;
    }


    public static ImageWithEntity createImageWithEntity(AuthoringEntity entity){
        ResourceBundle generalResources = ResourceBundle.getBundle("authoring_general");
        ResourceBundle utilityResources = ResourceBundle.getBundle(RESOURCE);
        String imageName = (String) entity.getBackingEntity().getComponent(new SpriteComponent("").getClass()).getValue();
        String imagePath = generalResources.getString("images_filepath");
        Double width = (Double) entity.getBackingEntity().getComponent(new WidthComponent(0.0).getClass()).getValue();
        Double height = (Double) entity.getBackingEntity().getComponent(new HeightComponent(0.0).getClass()).getValue();
        try {
            ImageWithEntity imageWithEntity = new ImageWithEntity(new FileInputStream(imagePath + imageName), entity, width, height);
            return imageWithEntity;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            String[] info = utilityResources.getString("FileException").split(",");
            ErrorBox errorBox = new ErrorBox(info[0], info[1]);
            errorBox.display();
            //TODO: get rid of this stack trace. rn it's just in case this happens and we need to know where
            e.printStackTrace();
            return null;

        }
    }
}
