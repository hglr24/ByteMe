package manager;
import javafx.scene.Scene;
import javafx.stage.Stage;
import page.NewUserPage;
import page.SplashPage;

public class SceneManager {
    private static final String MY_STYLE = "default_launcher.css";
    private static final double STAGE_WIDTH = 700;
    private static final double STAGE_HEIGHT = 600;

    private Scene myScene;

    private SplashPage myInitialPage;
    private NewUserPage myNewUserPage;

    private SwitchToUserOptions switchToInitialPage = this::goBackToInitialPage;
    private SwitchToUserPage switchToWelcomeUserPage = this::goToWelcomeUserPage;
    private SwitchToUserOptions switchToNewUserPage = this::goToNewUserPage;


    /**
     * The SceneManager class distributes lambdas among the different scenes, depending upon which scene they need
     * to change to. As a result, different displays do not have to depend on one another and the stage can remain
     * private to this class. This also helps to reduce code duplication, as two different scenes can use the same
     * lambda to switch to the same page
     * @author Anna Darwish
     */
    public void render(Stage myStage){
        makePages();
        myScene = new Scene(myInitialPage,STAGE_WIDTH,STAGE_HEIGHT);
        myScene.getStylesheets().add(MY_STYLE);
        myStage.setScene(myScene);
        myStage.show();
    }

    private void makePages(){
        myInitialPage = new SplashPage(switchToNewUserPage,switchToWelcomeUserPage);
        myNewUserPage = new NewUserPage(switchToInitialPage, switchToWelcomeUserPage);
    }

    private void goBackToInitialPage(){
        myScene.setRoot(myInitialPage);
        myInitialPage.refresh();
    }

    private void goToNewUserPage(){ myScene.setRoot(myNewUserPage);}

    private void goToWelcomeUserPage(String userName){
        UserManager myLoggedInManager = new UserManager(switchToInitialPage,userName);
        myLoggedInManager.render(myScene);
    }



}
