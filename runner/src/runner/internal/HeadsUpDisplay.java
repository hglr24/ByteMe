package runner.internal;

import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * Heads up Display dynamically displays and updates stats as level progresses
 * @author Louis Jensen
 */
public class HeadsUpDisplay extends Node {
    private Double myLives;
    private Double myScore;
    private Double myLevel;
    private Text myLabel;
    private double xPosition;
    private static final double DEFAULT_Y = 30.0;

    /**
     * Constructor for HeadsUpDisplay
     * @param width - width of screen
     */
    public HeadsUpDisplay(int width){
        myLives = 3.0;
        myLevel = 1.0;
        myScore = 0.0;
        xPosition = 20;
        Text text = new Text ("ByteMe   " +
                "   Level: " + myLevel.intValue() +
                "   Score: " + myScore.intValue() +
                "   Lives: " +myLives.intValue());
        text.setId("HeadsUpDisplay");
        myLabel = text;
        myLabel.setLayoutX(xPosition);
        myLabel.setLayoutY(DEFAULT_Y);
    }

    /**
     * Updates the HUD to new values
     * Called in game loop
     */
    public void updateLabel(){
        myLabel.setText("ByteMe   " +
                "   Level: " + myLevel.intValue() +
                "   Score: " + myScore.intValue() +
                "   Lives: " +myLives.intValue());
    }

    /**
     * Gets the X position of the HUD so it can stay constant on the screen
     * @return Double x position
     */
    public Double getX(){
        return xPosition;
    }

    /**
     * Gets the info to be added to screen
     * @return Text object with correct information
     */
    public Text getLabel(){
        return myLabel;
    }

    /**
     * Updates the value of Lives
     * @param lives - correct number of lives
     */
    public void updateLives(Double lives){
        myLives = lives;
    }

    /**
     * Updates the value of level
     * @param level - correct level number
     */
    public void updateLevel(Double level){
        myLevel = level;
    }

    /**
     * Updates the value of score
     * @param score - correct score
     */
    public void updateScore(Double score){
        myScore = score;
    }

}