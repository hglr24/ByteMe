package ui.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps track of valid labels for Object Type, Group, etc. in Authoring Environment
 * @author Harry Ross
 */
public class LabelManager {

    private Map<String, ObservableList<String>> myLabelGroups;

    public LabelManager() {
        myLabelGroups = new HashMap<>();
        myLabelGroups.put("Label", FXCollections.observableArrayList(new ArrayList<>()));
        myLabelGroups.put("Group", FXCollections.observableArrayList(new ArrayList<>()));
    }

    public void addLabel(String labelGroup, String labelName) {
        if (myLabelGroups.containsKey(labelGroup) && !myLabelGroups.get(labelGroup).contains(labelName) && labelName != null) //Checks to make sure it isn't already here
            myLabelGroups.get(labelGroup).add(labelName);
    }

    public void removeLabel(String labelGroup, String labelName) {
        if (myLabelGroups.containsKey(labelGroup))
            myLabelGroups.get(labelGroup).remove(labelName);
    }

    public ObservableList<String> getLabels(String labelGroup) {
        return myLabelGroups.getOrDefault(labelGroup, null);
    }
}
