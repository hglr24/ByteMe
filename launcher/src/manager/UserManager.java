package manager;

import center.external.CenterView;
import controls.LogOutButton;
import data.external.GameCenterData;
import javafx.scene.Scene;
import javafx.stage.Stage;
import page.CreateNewGamePage;
import page.WelcomeUserPage;
import runner.external.Game;
import ui.main.MainGUI;
    class UserManager{

    private CreateNewGamePage myNewGamePage;
    private WelcomeUserPage myWelcomeUserPage;

    private SwitchToUserOptions myLogOut;
    private SwitchToUserOptions switchToCreatePage = this::goToCreatePage;
    private SwitchToUserPage switchToGameCenter = this::goToGameCenter;
    private SwitchToAuthoring switchToAuthoring = this::goToAuthoring;

    private String myUserName;
    private Scene myScene;

    UserManager(SwitchToUserOptions logOut, String userName){
        myLogOut = logOut;
        myUserName = userName;

    }
    void render(Scene scene){
        makePages();
        myScene = scene;
        myScene.setRoot(myWelcomeUserPage);
    }

    private void makePages(){
        myWelcomeUserPage = new WelcomeUserPage(switchToCreatePage,switchToGameCenter,myUserName,myLogOut);
        myNewGamePage = new CreateNewGamePage(myUserName);
    }

    private void goToCreatePage(){
        myScene.setRoot(myNewGamePage);
    }

    private void goToAuthoring(GameCenterData myData){
        MainGUI myGUI = new MainGUI(new Game(), myData);
        myGUI.launch(false);
    }

    private void goToGameCenter(String userName){
        CenterView myCenter = new CenterView(userName);
        Stage gameCenterStage = new Stage();
        gameCenterStage.setScene(myCenter.getScene());
        gameCenterStage.show();
    }
 }


//    private void goToAuthoring(GameCenterData myData){
//        MainGUI myGUI = new MainGUI(new Game(), myData);
//        myGUI.launch();
//    }
//


