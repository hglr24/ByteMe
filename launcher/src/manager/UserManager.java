package manager;

import center.external.CenterView;
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
    private SwitchToUserOptions switchToWelcomeUserPage = this::goToWelcomePage;
    private SwitchToUserOptions switchToCreatePage = this::goToCreatePage;
    private SwitchToUserOptions switchToGameCenter = this::goToGameCenter;
    private SwitchToNewGameAuthoring switchToNewGame = this::goToAuthoring;
    private SwitchToAuthoring switchToOldGame = this::goToOldAuthoringGame;

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
        myNewGamePage = new CreateNewGamePage(switchToWelcomeUserPage,switchToOldGame,switchToNewGame,myUserName,myLogOut);

    }

    private void goToWelcomePage(){ myScene.setRoot(myWelcomeUserPage);}

    private void goToCreatePage(){
        myScene.setRoot(myNewGamePage);
    }

    private void goToAuthoring(GameCenterData newGameData){
        MainGUI myGUI = new MainGUI(newGameData);
        myGUI.launch(false);
    }

    private void goToOldAuthoringGame(Game oldGame, GameCenterData myData){
        MainGUI myGUI = new MainGUI(oldGame, myData);
        myGUI.launch(true);
    }

    private void goToGameCenter(){
        CenterView myCenter = new CenterView(myUserName);
        Stage gameCenterStage = new Stage();
        gameCenterStage.setScene(myCenter.getScene());
        gameCenterStage.show();
    }
 }


