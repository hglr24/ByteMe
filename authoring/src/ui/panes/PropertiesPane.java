package ui.panes;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.Propertable;
import ui.PropertableType;
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
public class PropertiesPane extends TitledPane {

    private ObjectProperty<Propertable> myProp;
    private String myPropFile;
    private LabelManager myLabelManager;

    private static final String PROP_TYPE_EXT = " Properties";

    public PropertiesPane(PropertableType propType, ObjectProperty<Propertable> prop, LabelManager labelManager) throws UIException {
        myProp = prop;
        myPropFile = propType.getPropFile();
        myLabelManager = labelManager;
        this.setText(propType.toString() + PROP_TYPE_EXT);
        this.setContent(createPropertiesGrid());
        this.getStyleClass().add("prop-pane");
        addListener();
    }

    private void addListener() {
        myProp.addListener((ChangeListener<? super Propertable>) (change, oldVal, newVal) -> {
            try {
                this.setContent(createPropertiesGrid());
            } catch (UIException e) {
                e.displayUIException();
            }
        });
    }

    private Node createPropertiesGrid() throws UIException {
        Platform.runLater(this::requestFocus);
        GridPane gridlist = new GridPane();
        gridlist.getStyleClass().add("prop-grid");
        ScrollPane scrollpane = new ScrollPane(gridlist);
        if (myProp.getValue() != null)
            extractNewProperties(gridlist);
        else {
            return new Label("Create an Object Type to Start");
        }
        return scrollpane;
    }

    private void extractNewProperties(GridPane gridlist) throws UIException {
        ResourceBundle bundle = ResourceBundle.getBundle(myPropFile);
        ArrayList<String> types = Collections.list(bundle.getKeys());
        Collections.sort(types);
        for (String type : types) {
            try {
                String name = type.split("\\.")[1];
                String value = bundle.getString(type);
                gridlist.add(createProperty(myProp.getValue().getEnumClass(), name, value), 0, gridlist.getRowCount());
            } catch (IndexOutOfBoundsException e) {
                throw new UIException("Invalid properties file");
            }
        }
    }

    private VBox createProperty(Class<? extends Enum> enumClass, String name, String info) throws UIException {
        VBox newProp = new VBox();
        newProp.getStyleClass().add("prop-cell");
        Label propName = new Label(name);
        propName.getStyleClass().add("sub-label");
        String[] sep = info.split(":");
        try {
            Class<?> clazz = Class.forName(sep[0]);
            Constructor<?> constructor = (sep[1].equals("none")) ?
                    clazz.getConstructor() : clazz.getConstructor(String.class);
            ControlProperty instance = (sep[1].equals("none")) ?
                    (ControlProperty) constructor.newInstance() : (ControlProperty) constructor.newInstance(sep[1]);
            newProp.getChildren().addAll(propName, (Node) instance);
            instance.populateValue(myProp.getValue(), Enum.valueOf(enumClass, name.toUpperCase()),
                    myProp.getValue().getPropertyMap().get(Enum.valueOf(enumClass, name.toUpperCase())), myLabelManager);
            instance.setAction(myProp.getValue(), Enum.valueOf(enumClass, name.toUpperCase()), sep[2]);
        } catch (Exception e) {
            throw new UIException("Error creating properties controls");
        }
        return newProp;
    }
}
