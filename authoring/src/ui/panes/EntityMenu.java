package ui.panes;

import javafx.geometry.Pos;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityMenu extends TitledPane {
    private VBox myVBox;
    private Map<String, VBox> myMap;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 600;

    public EntityMenu(String title){
        this.setText(title);
        this.setCollapsible(false);
        myVBox = new VBox();
        myMap = new HashMap<>();
        this.setContent(myVBox);
    }

    public void addDropDown(String title){
        TitledPane newTitled = new TitledPane();
        newTitled.setAlignment(Pos.CENTER);
        newTitled.setText(title);
        VBox vBox = new VBox();
        newTitled.setContent(vBox);
        myMap.put(title, vBox);
        myVBox.getChildren().add(newTitled);
    }

    public void addToDropDown(String category, List<Pane> contentToAdd){
        VBox vBox = myMap.get(category);
        vBox.getChildren().addAll(contentToAdd);
    }
}
