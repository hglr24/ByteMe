package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import ui.manager.ObjectManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Harry Ross
 */
public class AuthoringLevel implements Propertable {

    private ObservableMap<Enum, String> myPropertyMap;
    private List<AuthoringEntity> myEntities;

    private static final List<? extends Enum> PROP_VAR_NAMES = Arrays.asList(LevelField.values());
    private static final Integer DEFAULT_ROOM_SIZE = 1200;

    public AuthoringLevel(String label) {
        myEntities = new ArrayList<>();
        myPropertyMap = FXCollections.observableHashMap();
        for (Enum name : PROP_VAR_NAMES)
            myPropertyMap.put(name, null);
        myPropertyMap.put(LevelField.LABEL, label);
        myPropertyMap.put(LevelField.HEIGHT, DEFAULT_ROOM_SIZE.toString());
        myPropertyMap.put(LevelField.WIDTH, DEFAULT_ROOM_SIZE.toString());
    }

    public ObservableMap<Enum, String> getPropertyMap() {
        return myPropertyMap;
    }

    @Override
    public Class<? extends Enum> getEnumClass() {
        return LevelField.class;
    }

    public void addEntity(AuthoringEntity newEntity) {
        myEntities.add(newEntity);
    }

    public void removeEntity(AuthoringEntity oldEntity) {
        myEntities.remove(oldEntity);
    }

    public List<AuthoringEntity> getEntities() {
        return myEntities;
    }
}
