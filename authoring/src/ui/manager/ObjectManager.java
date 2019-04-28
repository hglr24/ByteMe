package ui.manager;

import data.external.GameCenterData;
import engine.external.events.Event;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.EntityField;
import ui.LevelField;
import ui.Propertable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    private ObservableMap<AuthoringEntity, String> myEntityTypeMap;
    private GameCenterData myGameCenterData;

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
        myEntityTypeMap = FXCollections.observableHashMap();
        myGameCenterData = new GameCenterData();
    }

    /**
     * Add a level to ObjectManager
     * @param level AuthoringLevel to be added
     */
    public void addLevel(AuthoringLevel level) {
        myLevels.add(level);
        myLabelManager.addLabel(LevelField.LABEL, level.getPropertyMap().get(LevelField.LABEL));
    }

    /**
     * Remove a level from ObjectManager
     * @param label AuthoringLevel to be removed
     */
    public void removeLevel(String label) {
        for (AuthoringLevel level : myLevels) {
            if (level.getPropertyMap().get(LevelField.LABEL).equals(label)) {
                myLevels.remove(level);
                myLabelManager.removeLabel(LevelField.LABEL, level.getPropertyMap().get(LevelField.LABEL));
                return;
            }
        }
    }

    public void removeAllLevels() {
        myLevels = FXCollections.observableArrayList(new ArrayList<>());
    }

    /**
     * Use this for adding a new general type
     * @param entity AuthoringEntity whose type is to be added
     */
    public void addEntityType(AuthoringEntity entity, String backingString) {
        myEntities.add(entity);
        myLabelManager.addLabel(EntityField.LABEL, entity.getPropertyMap().get(EntityField.LABEL));
        myEventMap.put(entity.getPropertyMap().get(EntityField.LABEL), FXCollections.observableArrayList(new ArrayList<>()));
        myEntityTypeMap.put(entity, backingString);
    }

    /**
     * Use this for instances that are added to a specific level
     * @param entity AuthoringEntity instance to add
     */
    public void addEntityInstance(AuthoringEntity entity) {
        myEntities.add(entity);
        ((AuthoringLevel) myCurrentLevel.getValue()).addEntity(entity);
    }

    /**
     * Use to remove all instances of an entity type from ObjectManager, removes Instances, Events, Label for complete deletion
     * @param entity AuthoringEntity with label corresponding to the type being deleted
     */
    public void removeEntityType(AuthoringEntity entity) {
        myEntities.removeIf(authEntity ->
                authEntity.getPropertyMap().get(EntityField.LABEL).equals(entity.getPropertyMap().get(EntityField.LABEL)));
        for (AuthoringLevel level : myLevels)
            level.getEntities().removeIf(authEntity ->
                    authEntity.getPropertyMap().get(EntityField.LABEL).equals(entity.getPropertyMap().get(EntityField.LABEL)));

        myLabelManager.removeLabel(EntityField.LABEL, entity.getPropertyMap().get(EntityField.LABEL));
        myEventMap.remove(entity.getPropertyMap().get(EntityField.LABEL));
        myEntityTypeMap.remove(entity);
    }

    /**
     * Use to remove a single instance of an AuthoringEntity from the authoring environment
     * @param entity Instance to be removed
     */
    public void removeEntityInstance(AuthoringEntity entity) {
        myEntities.remove(entity);
        ((AuthoringLevel) myCurrentLevel.getValue()).getEntities().remove(entity);
    }

    public void propagate(String objectLabel, Enum property, String newValue) {
        for (AuthoringEntity entity : myEntities) {
            if (entity.getPropertyMap().get(EntityField.LABEL).equals(objectLabel)) { // Match found
                entity.getPropertyMap().put(property, newValue);
                if (property.equals(EntityField.LABEL))
                    myLabelManager.addLabel(EntityField.LABEL, newValue);
            }
        }
        if (property.equals(EntityField.LABEL)) {
            myEventMap.putIfAbsent(newValue, myEventMap.get(objectLabel));
            myEventMap.remove(objectLabel);
            myLabelManager.removeLabel(EntityField.LABEL, objectLabel); // Remove old label from LabelManager if a label was just propagated
        }
    }

    public void flushFocusAssignment(Propertable propagator) {
        for(AuthoringEntity entity : ((AuthoringLevel) myCurrentLevel.getValue()).getEntities()) {
            if (!entity.equals(propagator))
                entity.getPropertyMap().put(EntityField.FOCUS, "false");
        }
    }

    private void groupRemoveAction(ListChangeListener.Change<? extends String> change) {
        change.next();
        String str = null;

        if (change.wasReplaced())
            str = change.getAddedSubList().get(0);

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

    public void setGameCenterData(GameCenterData data) {
        myGameCenterData = data;
    }

    public GameCenterData getGameCenterData() {
        return myGameCenterData;
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

    public ObservableMap<AuthoringEntity, String> getTypeMap() {
        return myEntityTypeMap;
    }
}
