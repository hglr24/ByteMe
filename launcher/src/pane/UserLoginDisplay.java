package pane;

import controls.EnterGameButton;
import controls.HiddenField;
import controls.InformativeField;
import controls.PaneLabel;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import manager.SwitchToUserOptions;
import manager.SwitchToUserPage;

import java.util.ResourceBundle;
/**
 * This is the login display that a user sees in the lower right pane of the splash page - a user enters their credentials
 * here
 * @author Anna Darwish
 */
public class UserLoginDisplay extends VBox {
    private static final String LOGIN_RESOURCE = "launcher_display";
    private static final ResourceBundle myResources = ResourceBundle.getBundle(LOGIN_RESOURCE);

    private static final String LOGIN_KEY = "login_display";

    private static final String LOGIN_BUTTON_KEY = "login_button";
    private static final String LOGIN_BUTTON_TEXT = myResources.getString(LOGIN_BUTTON_KEY);

    private static final String LOGIN_LABEL = "login_label";

    private static final String STYLE = "default_launcher.css";
    private static final String DELIMITER = ",";

    public UserLoginDisplay(SwitchToUserPage mySwitch){
        String[] loginTextFields = myResources.getString(LOGIN_KEY).split(DELIMITER);
        this.setSpacing(40);
        InformativeField myUserNameField = new InformativeField(loginTextFields[0]);
        HiddenField myPasswordField = new HiddenField(loginTextFields[1]);
        setUpChildren(myUserNameField,myPasswordField, mySwitch);

    }
    private void setUpChildren(InformativeField userName, HiddenField passWord, SwitchToUserPage mySwitch){
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(new PaneLabel(LOGIN_LABEL));
        this.getChildren().add(userName);
        this.getChildren().add(passWord);
        this.getChildren().add(new EnterGameButton(LOGIN_BUTTON_TEXT, userName.accessValue(),
                passWord.accessValue(), mySwitch));
    }
}
