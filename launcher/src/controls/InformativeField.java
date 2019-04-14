package controls;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class InformativeField extends TextField {
    private String informationRequested;
    private CredentialValidator myField = new CredentialValidator() {
        @Override
        public String currentFieldValue() {
            return getTextEntered();
        }
    };
    public InformativeField(String message){

        informationRequested = message;
        this.setText(informationRequested);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleDisplay();
            }
        });
    }
    /**
     * Provides access to values of InformativeField, which displays information concerning what the user should input
     * and displays whatever they have input so far, in order to validate their credentials
     * @author Anna Darwish
     */
    public CredentialValidator accessValue(){
        return myField;
    }
    private void handleDisplay(){
        if (this.getText().equals(informationRequested)){
            this.setText("");
        }
    }
    private String getTextEntered(){
        return new String(this.getText());
    }


}
