package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import ui.manager.ObjectManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Harry Ross
 */
public class TestLevel implements Propertable {

    private ObservableMap<String, String> myPropertyMap;
    private ObjectManager myObjectManager;

    private static final List<String> PROP_VAR_NAMES =
            Arrays.asList("Label", "Height", "Width", "Background", "Music");
    private static final Double DEFAULT_ROOM_SIZE = 500.0;

    public TestLevel(String label, ObjectManager manager) {
        myObjectManager = manager;
        myPropertyMap = FXCollections.observableHashMap();
        for (String name : PROP_VAR_NAMES)
            myPropertyMap.put(name, null);
        myPropertyMap.put("Label", label);
        myPropertyMap.put("Height", DEFAULT_ROOM_SIZE.toString());
        myPropertyMap.put("Width", DEFAULT_ROOM_SIZE.toString());
        addPropertyListener();
    }

    public Map<String, String> getPropertyMap() {
        return myPropertyMap;
    }

    private void addPropertyListener() {
        // This may be useful someday but not yet
    }
}
