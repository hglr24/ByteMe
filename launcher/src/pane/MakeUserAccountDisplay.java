package pane;

import controls.EnterGameButton;
import controls.HiddenField;
import controls.InformativeField;
import controls.PaneLabel;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import manager.SwitchToUserOptions;

import java.util.ResourceBundle;


public class MakeUserAccountDisplay extends VBox{
    private static final String LOGIN_RESOURCE = "launcher_display";
    private static final ResourceBundle myResources = ResourceBundle.getBundle(LOGIN_RESOURCE);

    private static final String LOGIN_KEY = "login_display";

    private static final String LOGIN_BUTTON_KEY = "login_button";
    private static final String LOGIN_BUTTON_TEXT = myResources.getString(LOGIN_BUTTON_KEY);

    private static final String LOGIN_LABEL = "login_label";

    private static final String STYLE = "default_launcher.css";
    private static final String DELIMITER = ",";
    public MakeUserAccountDisplay(SwitchToUserOptions switchToNewUserPage){

            this.getStylesheets().add(STYLE);
            String[] loginTextFields = myResources.getString(LOGIN_KEY).split(DELIMITER);
            this.setSpacing(40);
            InformativeField myUserNameField = new InformativeField(loginTextFields[0]);
            HiddenField myPasswordField = new HiddenField(loginTextFields[1]);

        }

    }



