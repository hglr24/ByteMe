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
    public CredentialValidator accessValue(){
        return myField;
    }
    private void handleDisplay(){
        if (this.getText().equals(informationRequested)){
            this.setText("");
        }
    }
    public String getTextEntered(){
        return new String(this.getText());
    }


}
