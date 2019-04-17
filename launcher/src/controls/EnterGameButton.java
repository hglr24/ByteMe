package controls;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import manager.SwitchToUserOptions;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class EnterGameButton extends SceneSwitchButton {
    private static final String LOGIN_RESOURCE = "user_credentials";
    private static final ResourceBundle myResources = ResourceBundle.getBundle(LOGIN_RESOURCE);
    private CredentialValidator userNameAccessor;
    private CredentialValidator passWordAccessor;
    /**
     * Handles Validating the user input into the username and password fields in the main login page
     * @author Anna Darwish
     */
    public EnterGameButton(String label, CredentialValidator userName, CredentialValidator passWord, SwitchToUserOptions mySwitch){
        super(label);
        userNameAccessor = userName;
        passWordAccessor = passWord;
        this.getStylesheets().add("default_launcher.css");
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (validateUserCredentials()){
                    mySwitch.switchPage();
                }

            }
        });
    }
    private boolean validateUserCredentials(){
        String currentUserName = userNameAccessor.currentFieldValue();
        String currentPassWord = passWordAccessor.currentFieldValue();
        return myResources.containsKey(currentUserName) &&
                myResources.getString(currentUserName).equals(currentPassWord);
    }
    private void changeColor(){
        this.setTextFill(Color.AQUAMARINE);
    }

}
