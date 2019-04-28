package engine.external;

import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Hsingchih Tang
 * Stores Entity and Event data for a certain level in a Game
 * Distinct Levels of the same Game may store different data from each other
 */
public class Level implements Serializable {

    private String myLabel;
    private Double myWidth;
    private Double myHeight;
    private String myBackground;
    private String myMusic;

    private Collection<Entity> myEntities;
    private Collection<IEventEngine> myEvents;
    private Level myCheckPoint;


    /**
     * Initialized fields for storing the Events and Entities tied to this game Level
     * No duplicate Entity objects or Event objects allowed in the same Level
     */
    public Level() {
        myEntities = new HashSet<Entity>();
        myEvents = new HashSet<IEventEngine>();
    }

    /**
     * Attaches a new Entity object to this Game Level
     * @param entity new Entity to be added
     */
    public void addEntity(Entity entity) {
        myEntities.add(entity);
    }

    /**
     * Attaches a new Event object to this Game Level
     * @param event new Event to be added
     */
    public void addEvent(IEventEngine event){
        myEvents.add(event);
    }

    /**
     * Retrieves all Entities attached to a Game Level
     * @return HashSet of Entities stored for this Level
     */
    public Collection<Entity> getEntities() {
        return myEntities;
    }

    /**
     * Retrieves all Events attached to a Game Level
     * @return HashSet of Events stored for this Level
     */
    public Collection<IEventEngine> getEvents() {
        return myEvents;
    }

    public void setLabel(String label) {
        myLabel = label;
    }

    public void setBackground(String imageResource) {
        myBackground = imageResource;
    }

    public void setWidth(double width) {
        myWidth = width;
    }

    public void setHeight(double height) {
        myHeight = height;
    }

    public void setMusic(String musicResource) {
        myMusic = musicResource;
    }

    public String getLabel() {
        return myLabel;
    }

    public String getBackground() {
        return myBackground;
    }

    public String getMusic() {
        return myMusic;
    }

    public Double getWidth() {
        return myWidth;
    }

    public Double getHeight() {
        return myHeight;
    }

}
