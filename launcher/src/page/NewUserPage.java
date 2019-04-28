package page;


import controls.InformativeField;
import controls.TitleLabel;
import data.external.DataManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import manager.SwitchToUserPage;

public class NewUserPage extends VBox {
    private static final String MY_STYLE = "new-user-vbox";
    private static final String NEW_USER_KEY = "new_user";
    private static final String USERNAME = "Enter New UserName";
    private static final String PASSWORD = "Enter Password";
    private static final String REENTER_PASSWORD = "Re-enter Password";
    private InformativeField userName = new InformativeField(USERNAME);
    private InformativeField passWord = new InformativeField(PASSWORD);
    private InformativeField reenter = new InformativeField(REENTER_PASSWORD);
    private DataManager myDataManager  = new DataManager();
    /**
     * This page will prompt the user either enter the authoring environment to create games or go to the game center so
     * they can play games
     * @author Anna Darwish
     */
    public NewUserPage(SwitchToUserPage enterGame){
        this.getStyleClass().add(MY_STYLE);
        this.getChildren().add(new TitleLabel(NEW_USER_KEY));
        this.getChildren().add(userName);
        this.getChildren().add(passWord);
        this.getChildren().add(reenter);
        Button createAccount = new Button("Create Account");
        this.getChildren().add(createAccount);
        createAccount.setOnMouseClicked(mouseEvent -> {
            if (validCredentials()){
                System.out.println("USER CREATED ACCOUNT SUCCESSFULLY");
                enterGame.switchUserPage(userName.getTextEntered());
            }
            else{
                displayUserNameInUseError();
            }
        });
    }

    private boolean validCredentials() {
        if (equalPassWord() && adequatePassword()){
            return validUserName();
        }
        else {
            return displayInvalidPasswordError("Bad Password");
        }

    }

    private boolean equalPassWord(){
        return passWord.getText().equals(reenter.getText());
    }

    private boolean adequatePassword(){
        return passWord.getText().length() >= 8;

    }

    private boolean validUserName(){
        return myDataManager.createUser(userName.getTextEntered(),passWord.getTextEntered());
//        myDataManager.validateUser(userName,passWord);
//        myDataManager.updatePassword(userName, passWord);//, return true if successful, false otherwise
       // return true;
    }
    private boolean displayInvalidPasswordError(String message){
        Popup myUserNameError = new Popup();
        myUserNameError.getContent().add(new Label(message));
        return false;
    }

    private boolean displayUserNameInUseError(){
        Popup myUserNameError = new Popup();
        myUserNameError.getContent().add(new Label("UserName in Use"));
        return false;
    }
}
