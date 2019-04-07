package ui_components;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Utilities {
    private static final String ERROR_MESSAGE = "error";

    public static String getValue(ResourceBundle bundle, String key) {
        try {
            return bundle.getString(key);
        }
        catch (MissingResourceException e) {
            return ERROR_MESSAGE;
        }
    }

}
