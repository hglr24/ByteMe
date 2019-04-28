package popup;

import java.util.ResourceBundle;

/**
 * Class for displaying user login error
 * @author Anna Darwish and Harry Ross
 */
public class LoginErrorPopUp extends ErrorPopUp {

    private static final String DEFAULT_STYLE = "default_launcher.css";
    private static final String LOGIN_ERROR = "Login Error";
    private static final ResourceBundle ERROR_RESOURCE = ResourceBundle.getBundle("launcher_error");

    /**
     * Creates an ErrorBox with a title for the general error and a specific message)
     * @param errorKey key for resource error
     */
    public LoginErrorPopUp(String errorKey) {
        super(LOGIN_ERROR, ERROR_RESOURCE.getString(errorKey));
    }
}
