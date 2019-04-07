package ui;

import javafx.scene.control.Button;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * @author Carrie Hunner
 * This class was created to provide general methods that can be used across the UI.
 * It's a place to hold reflection code that may be needed across multiple classes.
 */
public class Utility {
    private static final String RESOURCE = "utility";

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
    public static Button makeButon(Object o, String methodName, String buttonText){
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

    /**
     * Check validity of new value from based on regex syntax from properties file
     */
    public static boolean isValidValue(String key, String newVal, String syntaxResource) {
        ResourceBundle bundle = ResourceBundle.getBundle(syntaxResource);
        if (bundle.containsKey(key)) { // Label matches syntax, valid
            if (newVal.matches(bundle.getString(key))) {
                return true;
            } else {
                ErrorBox error = new ErrorBox("Variable Error", "Invalid variable, refer to documentation for syntax");
                error.display();
            }
        }
        return false;
    }

}
