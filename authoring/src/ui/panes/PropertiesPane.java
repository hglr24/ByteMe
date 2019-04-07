package ui.panes;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.Propertable;
import ui.UIException;
import ui.control.ControlProperty;
import ui.manager.LabelManager;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * @author Harry Ross
 */
class PropertiesPane extends TitledPane {

    private Propertable myProp;
    private String myPropFile;
    private LabelManager myLabelManager;

    private static final String PROP_TYPE_EXT = " Properties";

    PropertiesPane(String propOf, Propertable prop, String propResource, LabelManager labelManager) throws UIException {
        myProp = prop;
        myPropFile = propResource;
        myLabelManager = labelManager;
        this.setText(propOf + PROP_TYPE_EXT);
        this.setContent(createPropertiesGrid());
    }

    private ScrollPane createPropertiesGrid() throws UIException {
        Platform.runLater(() -> this.requestFocus());
        GridPane gridlist = new GridPane();
        gridlist.getStyleClass().add("prop-grid");
        ScrollPane scrollpane = new ScrollPane(gridlist);
        ResourceBundle bundle = ResourceBundle.getBundle(myPropFile);
        ArrayList<String> types = Collections.list(bundle.getKeys());
        Collections.sort(types);
        for (String type : types) {
            try {
                String name = type.split("\\.")[1];
                String value = bundle.getString(type);
                gridlist.add(createProperty(name, value), 0, gridlist.getRowCount());
            } catch (IndexOutOfBoundsException e) {
                throw new UIException("Invalid properties file");
            }
        }
        return scrollpane;
    }

    private VBox createProperty(String name, String info) throws UIException {
        VBox newProp = new VBox();
        newProp.getStyleClass().add("prop-cell");
        Label propName = new Label(name);
        propName.getStyleClass().add("prop-label");
        String[] sep = info.split(":");
        try {
            Class<?> clazz = Class.forName(sep[0]);
            Constructor<?> constructor = (sep[1].equals("none")) ?
                    clazz.getConstructor() : clazz.getConstructor(String.class);
            ControlProperty instance = (sep[1].equals("none")) ?
                    (ControlProperty) constructor.newInstance() : (ControlProperty) constructor.newInstance(sep[1]);
            newProp.getChildren().addAll(propName, (Node) instance);
            instance.populateValue(name, myProp.getPropertyMap().get(name), myLabelManager);
            instance.setAction(myProp, name, sep[2]);
        } catch (Exception e) {
            throw new UIException("Error creating properties controls");
        }
        return newProp;
    }
}
