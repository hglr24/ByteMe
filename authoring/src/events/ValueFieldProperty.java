package events;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import ui.windows.AudioManager;
import ui.windows.ImageManager;
import voogasalad.util.reflection.Reflection;


import java.util.ResourceBundle;


public class ValueFieldProperty extends TextField {
    private ChangeListener<String> myListener;
    private EventHandler showFileOptions;

    private static final String listenerResourceName = "component_value_listener";
    private ResourceBundle listenerResource = ResourceBundle.getBundle(listenerResourceName);

    private static final String PROMPT = "Value";
    private static final String SOUND = "Sound";
    private static final String IMAGE = "Image";
    private static final String NUMBER = "Number";

    private static final ResourceBundle VALUE_PROMPTS_RESOURCE = ResourceBundle.getBundle("value_field_prompts");

    public ValueFieldProperty(){
        this.setPromptText(PROMPT);
    }

    public void clearListeners(){
        showFileOptions = event -> doNothing();
        this.setOnMouseClicked(showFileOptions);
        if (myListener != null)
            this.textProperty().removeListener(myListener);

    }

    public void addListeners(String componentName){
        Reflection.callMethod(this,listenerResource.getString(componentName));
    }


    public void addNumericRestriction(){
        resetValueField(NUMBER);
        myListener = (observableValue, s, newValue) -> {
            if (!newValue.matches("^-?\\d+(?:\\.\\d+)?")) {
                setText(newValue.replaceAll("^-?\\d+(?:\\.\\d+)?", ""));
            }
        };
        this.textProperty().addListener(myListener);
    }

    public void addSoundFilePrompt(){
        resetValueField(SOUND);
        showFileOptions = (EventHandler<MouseEvent>) mouseEvent -> {
            AudioManager myManager = new AudioManager();
            myManager.show();
            myManager.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    System.out.println(myManager.getAssetName());
                }
            });
        };
        this.setOnMouseClicked(showFileOptions);
    }

    public void addImageFilePrompt(){
        resetValueField(IMAGE);
        showFileOptions = (EventHandler<MouseEvent>) mouseEvent -> {
            ImageManager myManager = new ImageManager();
            myManager.show();
        };
        this.setOnMouseClicked(showFileOptions);
    }

    private void resetValueField(String promptKey){
        this.setText("");
        this.setPromptText(VALUE_PROMPTS_RESOURCE.getString(promptKey));
    }

    private void doNothing(){

    }
}
