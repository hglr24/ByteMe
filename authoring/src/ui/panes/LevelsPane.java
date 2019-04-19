package ui.panes;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import ui.LevelField;
import ui.Propertable;
import ui.manager.ObjectManager;

public class LevelsPane extends TitledPane {

    private ObjectManager myObjectManager;
    private GridPane myLevelsList;

    private static final String LEVELS_PANE_TITLE = "Levels";

    public LevelsPane(ObjectManager manager, ObjectProperty<Propertable> currentLevel) {
        myObjectManager = manager;
        myLevelsList = new GridPane();

        populateLevelList();
        this.setText(LEVELS_PANE_TITLE);
        this.setContent(new ScrollPane(myLevelsList));
        this.getStyleClass().add("prop-pane");
        myObjectManager.getLabelManager().getLabels(LevelField.LABEL)
                .addListener((ListChangeListener<? super String>) change -> populateLevelList());
    }

    private void populateLevelList() {
        for (int i = 0; i < myObjectManager.getLevels().size(); i++) {
            Label newLabel = new Label(myObjectManager.getLevels().get(i).getPropertyMap().get(LevelField.LABEL));
            myLevelsList.add(newLabel, 0, i);
        }
    }
}
