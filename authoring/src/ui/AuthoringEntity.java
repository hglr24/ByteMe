package ui;

import engine.external.Entity;
import engine.external.events.Event;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ui.manager.ObjectManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harry Ross
 */
public class AuthoringEntity implements Propertable {

    private ObservableMap<Enum, String> myPropertyMap;
    private ObjectManager myObjectManager;
    private List<String> myInteractionListing = new ArrayList<>();
    private String myBackingString;

    private AuthoringEntity() { // Initialize default property map
        myPropertyMap = FXCollections.observableHashMap();
        for (EntityField defaultField : EntityField.getDefaultFields()) {
            myPropertyMap.put(defaultField, defaultField.getDefaultValue());
        }
    }

    public AuthoringEntity(String label, ObjectManager manager) { // Create new type of AuthoringEntity from scratch
        this();
        myObjectManager = manager;
        myPropertyMap.put(EntityField.LABEL, label); //TODO see if this can be removed
        addPropertyListeners();
        myObjectManager.addEntityType(this, "");
    }

    public AuthoringEntity(Entity basis, String backingString, ObjectManager manager) { // Create new AuthoringEntity type from Entity
        this();
        myObjectManager = manager;
        myBackingString = backingString;

        for (EntityField field : EntityField.values())
            if (basis.hasComponents(field.getComponentClass()))
                myPropertyMap.put(field, String.valueOf(basis.getComponent(field.getComponentClass()).getValue()));

        addPropertyListeners();
        myObjectManager.addEntityType(this, backingString);
    }

    public AuthoringEntity(AuthoringEntity copyBasis) { // Create new AuthoringEntity instance from pre-existing type
        this();
        myBackingString = copyBasis.myBackingString;
        myObjectManager = copyBasis.myObjectManager;
        for (EntityField commonField : EntityField.getCommonFields()) {
            if (copyBasis.myPropertyMap.containsKey(commonField))
                myPropertyMap.put(commonField, copyBasis.myPropertyMap.get(commonField));
        }
        addPropertyListeners();
        myObjectManager.addEntityInstance(this);
    }

    private void addPropertyListeners() {
        myPropertyMap.addListener((MapChangeListener<Enum, String>) change ->
           propagateChanges(change.getKey(),  change.getValueRemoved(), change.getValueAdded()));
    }

    private void propagateChanges(Enum key, String oldVal, String newVal) { // At this point value is assumed valid or untaken label
        if (key.equals(EntityField.LABEL)) // If we're changing the label, preserve old label for propagation purposes
            myObjectManager.propagate(oldVal, key, newVal);
        else if (key.equals(EntityField.IMAGE) || key.equals(EntityField.GROUP)) // If we're changing the Image or Group, just do it
            myObjectManager.propagate(myPropertyMap.get(EntityField.LABEL), key, newVal);
        else if (key.equals(EntityField.FOCUS))
            myObjectManager.flushFocusAssignment(this);
        else if (!((EntityField) key).isDefault()) {
            myObjectManager.propagate(myPropertyMap.get(EntityField.LABEL), key, newVal);
        }
    }

    public ObservableMap<Enum, String> getPropertyMap() {
        return myPropertyMap;
    }

    @Override
    public Class<? extends Enum> getEnumClass() {
        return EntityField.class;
    }

    public ObservableList<Event> getEvents() {
        return myObjectManager.getEvents(this.myPropertyMap.get(EntityField.LABEL));
    }

    public List<String> getInteractionListing(){ return myInteractionListing;}

    public ObjectManager getObjectManager() {
        return myObjectManager;
    }

}
