package ui;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import ui.manager.ObjectManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Harry Ross
 */
public class TestEntity implements Propertable {

    private ObservableMap<String, String> myPropertyMap;
    private ObjectManager myObjectManager;

    private static final List<String> PROP_VAR_NAMES =
            Arrays.asList("X", "Y", "XScale", "YScale", "Facing", "Label", "Group", "Image");

    public TestEntity(String label, ObjectManager manager) {
        myObjectManager = manager;
        myPropertyMap = FXCollections.observableHashMap();
        for (String name : PROP_VAR_NAMES)
            myPropertyMap.put(name, null);
        myPropertyMap.put("X", "0.0");
        myPropertyMap.put("Y", "0.0");
        myPropertyMap.put("XScale", "1.0");
        myPropertyMap.put("YScale", "1.0");
        myPropertyMap.put("Label", label);

        myPropertyMap.put("Image", "sprite1.png"); //TODO This would not be here in real version
        addPropertyListeners();
    }

    private void addPropertyListeners() {
        myPropertyMap.addListener((MapChangeListener<String, String>) change ->
           propagateChanges(change.getKey(),  change.getValueRemoved(), change.getValueAdded()));
    }

    private void propagateChanges(String key, String oldVal, String newVal) { // At this point value is known valid or untaken label
        if (key.equals("Label")) // If we're changing the label, preserve old label for propagation purposes
            myObjectManager.propagate(oldVal, key, newVal);
        else if (key.equals("Image") || key.equals("Group")) // If we're changing the Image or Group, just do it
            myObjectManager.propagate(myPropertyMap.get("Label"), key, newVal);
    }

    public Map<String, String> getPropertyMap() {
        return myPropertyMap;
    }
}
