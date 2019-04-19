package ui.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.EntityField;
import ui.LevelField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps track of valid labels for Object Type, Group, etc. in Authoring Environment
 * @author Harry Ross
 */
public class LabelManager {

    private Map<Enum, ObservableList<String>> myLabelGroups;

    public LabelManager() {
        myLabelGroups = new HashMap<>();
        myLabelGroups.put(EntityField.LABEL, FXCollections.observableArrayList(new ArrayList<>()));
        myLabelGroups.put(EntityField.GROUP, FXCollections.observableArrayList(new ArrayList<>()));
        myLabelGroups.put(LevelField.LABEL, FXCollections.observableArrayList(new ArrayList<>()));
    }

    public void addLabel(Enum labelGroup, String labelName) {
        if (myLabelGroups.containsKey(labelGroup) &&
                !myLabelGroups.get(labelGroup).contains(labelName) && labelName != null) //Checks to make sure it isn't already here
            myLabelGroups.get(labelGroup).add(labelName);
    }

    public void removeLabel(Enum labelGroup, String labelName) {
        if (myLabelGroups.containsKey(labelGroup))
            myLabelGroups.get(labelGroup).remove(labelName);
    }

    public ObservableList<String> getLabels(Enum labelGroup) {
        return myLabelGroups.getOrDefault(labelGroup, null);
    }
}
