package controls;
import javafx.scene.control.PasswordField;

public class HiddenField extends PasswordField {
    private String informationRequested;
    private static final String DEFAULT = "";
    private CredentialValidator myField = this::getTextEntered;
    public HiddenField(String information){

        informationRequested = information;
        this.setText(informationRequested);

        this.setOnMouseClicked(mouseEvent -> handleDisplay());
    }
    /**
     * Provides access to values of HiddenField, which hides the display of the String value that the user is typing,
     * in order to validate the user's credentials
     * @author Anna Darwish
     */
    public CredentialValidator accessValue(){
        return myField;
    }
    private void handleDisplay(){
        if (this.getText().equals(informationRequested)){
            this.setText(DEFAULT);
        }
    }
    public String getTextEntered(){
        return (this.getText());
    }

}
