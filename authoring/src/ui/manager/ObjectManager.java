package ui.manager;

import engine.external.Entity;
import events.Event;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.EntityField;
import ui.LevelField;
import ui.Propertable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Harry Ross
 */
public class ObjectManager {

    private Set<AuthoringEntity> myEntities;
    private Map<String, ObservableList<Event>> myEventMap;
    private LabelManager myLabelManager;
    private ObservableList<AuthoringLevel> myLevels;
    private ObjectProperty<Propertable> myCurrentLevel;

    /**
     * Class that keeps track of every single instance of an Entity, across Levels, for the purposes of authoring environment
     * @author Harry Ross
     */
    public ObjectManager(ObjectProperty<Propertable> levelProperty) {
        myEntities = new HashSet<>();
        myLabelManager = new LabelManager();
        myEventMap = new HashMap<>();
        myLevels = FXCollections.observableArrayList(new ArrayList<>());
        myLabelManager.getLabels(EntityField.GROUP).addListener((ListChangeListener<? super String>) change -> groupRemoveAction(change));
        myCurrentLevel = levelProperty;
    }

    public void addLevel(AuthoringLevel level) {
        myLevels.add(level);
        myLabelManager.addLabel(LevelField.LABEL, level.getPropertyMap().get(LevelField.LABEL));
    }

    public void removeLevel(AuthoringLevel level) {
        myLevels.remove(level);
        myLabelManager.removeLabel(LevelField.LABEL, level.getPropertyMap().get(LevelField.LABEL));
    }

    /**
     * Use this for adding a new general type
     * @param entity
     */
    public void addEntityType(AuthoringEntity entity) {
        myEntities.add(entity);
        myLabelManager.addLabel(EntityField.LABEL, entity.getPropertyMap().get(EntityField.LABEL));
        myEventMap.put(entity.getPropertyMap().get(EntityField.LABEL), FXCollections.observableArrayList(new ArrayList<>()));
    }

    /**
     * Use this for instances that are added to a specific level
     * @param entity
     */
    public void addEntityInstance(AuthoringEntity entity) {
        myEntities.add(entity);
        ((AuthoringLevel) myCurrentLevel.getValue()).addEntity(entity);
    }

    //TODO remove entity??

    public void propagate(String objectLabel, Enum property, String newValue) {
        for (AuthoringEntity entity : myEntities) {
            if (entity.getPropertyMap().get(EntityField.LABEL).equals(objectLabel)) { // Match found
                entity.getPropertyMap().put(property, newValue);
                if (property.equals(EntityField.LABEL)) // TODO this may have solved group/entity label mix-up issue
                    myLabelManager.addLabel(EntityField.LABEL, newValue);
            }
        } //TODO propagate changed label into Event Map
        if (property.equals(EntityField.LABEL))
            myLabelManager.removeLabel(EntityField.LABEL, objectLabel); // Remove old label from LabelManager if a label was just propagated
    }

    public void flushCameraAssignment(Propertable propagator) {
        for(AuthoringEntity entity : ((AuthoringLevel) myCurrentLevel.getValue()).getEntities()) {
            if (!entity.equals(propagator))
                entity.getPropertyMap().put(EntityField.CAMERA, "false");
        }
    }

    private void groupRemoveAction(ListChangeListener.Change<? extends String> change) {
        change.next();
        String str = null;

        if (change.wasReplaced())
            str = change.getAddedSubList().get(0);

        if (change.wasRemoved())
            System.out.println("REMOVED");

        if (change.wasReplaced() || change.wasRemoved()) {
            for (AuthoringEntity entity : myEntities) {
                if (entity.getPropertyMap().get(EntityField.GROUP) != null &&
                        entity.getPropertyMap().get(EntityField.GROUP).equals(change.getRemoved().get(0)))
                    entity.getPropertyMap().put(EntityField.GROUP, str);
            }
        }
    }

    public void updateLevelLabel(String oldValue, String newValue) {
        myLabelManager.getLabels(LevelField.LABEL).add(myLabelManager.getLabels(LevelField.LABEL).indexOf(oldValue), newValue);
        myLabelManager.getLabels(LevelField.LABEL).remove(oldValue);
    }

    public LabelManager getLabelManager() {
        return myLabelManager;
    }

    public ObservableList<Event> getEvents(String objectType) {
        return myEventMap.get(objectType);
    }

    public ObservableList<AuthoringLevel> getLevels() {
        return myLevels;
    }
}
