package controls;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class TitleLabel extends Label {
    private static final String WELCOME_RESOURCE = "launcher_display";
    private static final String FADE_DURATION_KEY = "fade_duration";
    private static final String STYLE = "welcome.css";

    private final String myWelcomeLabel;
    private final FadeTransition myFadeAnimation;
    /**
     * This is the larger title display users see among the various pages of the launcher environment
     * @author Anna Darwish
     */
    public TitleLabel(String resourceKey){
        ResourceBundle myResources = ResourceBundle.getBundle(WELCOME_RESOURCE);
        myWelcomeLabel = myResources.getString(resourceKey);

        double fadeDuration = Double.parseDouble(myResources.getString(FADE_DURATION_KEY));
        myFadeAnimation = new FadeTransition(Duration.millis(fadeDuration));
        //setUpDisplay();
        this.setText(myWelcomeLabel);
        this.getStylesheets().add(STYLE);
    }
    public TitleLabel(String resourceKey, String modifyText){
        this(resourceKey);
        addToLabel(modifyText);
    }
    /**
     * This allows for toggling the display, as it may dependent upon the user who is currently logged in
     */
    private void addToLabel(String addition){
        this.setText(this.getText() + " " + addition);
    }

    private void setUpDisplay(){
        this.setText(myWelcomeLabel);
        myFadeAnimation.setNode(this);
        myFadeAnimation.setFromValue(0.0);
        myFadeAnimation.setToValue(1.0);
        myFadeAnimation.setCycleCount(1);
        myFadeAnimation.setAutoReverse(false);
    }


}
