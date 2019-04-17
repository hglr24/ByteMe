package manager;

import center.external.CenterView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import page.CreateNewGamePage;
import page.SplashPage;
import page.WelcomeUserPage;
import runner.external.Game;
import runner.external.GameCenterData;
import ui.main.MainGUI;

public class SceneManager {
    private static final String MY_STYLE = "default_launcher.css";
    private static final double STAGE_WIDTH = 700;
    private static final double STAGE_HEIGHT = 600;
    private Scene myScene;
    private SplashPage myInitialPage;
    private WelcomeUserPage myWelcomeUserPage;
    private CreateNewGamePage myNewGamePage;
    private SwitchToUserOptions switchToWelcomeUserPage = new SwitchToUserOptions() {
        @Override
        public void switchPage() {
            goToWelcomeUserPage();
        }
    };
    private SwitchToUserOptions switchToNewGamePage = new SwitchToUserOptions() {
        @Override
        public void switchPage() {
            goToUserOptions();
        }
    };
    private SwitchToAuthoring switchToAuthoring = new SwitchToAuthoring() {
        @Override
        public void switchScene() {
            goToAuthoring();
        }
    };
    private SwitchToUserOptions switchToGameCenter = new SwitchToUserOptions(){
        @Override
        public void switchPage() {
            goToGameCenter();
        }
    };
    private Stage myStage;
    /**
     * The SceneManager class distributes lambdas among the different scenes, depending upon which scene they need
     * to change to. As a result, different displays do not have to depend on one another and the stage can remain
     * private to this class. This also helps to reduce code duplication, as two different scenes can use the same
     * lambda to switch to the same page
     * @author Anna Darwish
     */
    public void render(Stage myStage){
        makePages();
        this.myStage = myStage;
        myScene = new Scene(myInitialPage,STAGE_WIDTH,STAGE_HEIGHT);
        myScene.getStylesheets().add(MY_STYLE);
        myStage.setScene(myScene);
        myStage.show();
    }
    private void makePages(){
        myWelcomeUserPage = new WelcomeUserPage(switchToNewGamePage,switchToGameCenter);
        myInitialPage = new SplashPage(switchToWelcomeUserPage);
        myNewGamePage = new CreateNewGamePage(switchToAuthoring,switchToGameCenter);
    }

    private void goToWelcomeUserPage(){
        myScene.setRoot(myWelcomeUserPage);
    }
    private void goToUserOptions(){
        myScene.setRoot(myNewGamePage);
    }
    private void goToAuthoring(){
        MainGUI myGUI = new MainGUI();
        myGUI.launch();
    }

    private void goToGameCenter(){
        CenterView myCenter = new CenterView();
        myStage.setScene(myCenter.getScene());
    }
}
