package ui.panes;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.TestEntity;
import ui.UIException;
import ui.control.ControlProperty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

class PropertiesPane extends TitledPane {

    private TestEntity myEntity;
    private List<ControlProperty> myControls;

    private static final String PROP_TYPE_EXT = " Properties";
    private static final String PROP_TYPE_FILE = "object_properties_list";

    PropertiesPane(String propOf, TestEntity entity) throws UIException {
        myEntity = entity;
        myControls = new ArrayList<>();
        GridPane optionsPane = createPropertiesGrid();
        this.setText(propOf + PROP_TYPE_EXT);
        this.setContent(optionsPane);
    }

    private GridPane createPropertiesGrid() throws UIException {
        GridPane gridlist = new GridPane();
        ResourceBundle bundle = ResourceBundle.getBundle(PROP_TYPE_FILE);
        Enumeration propNames = bundle.getKeys();
        while (propNames.hasMoreElements()) {
            String name = (String)propNames.nextElement();
            String value = bundle.getString(name);
            gridlist.add(createProperty(name, value), 0, gridlist.getRowCount());
        }
        return gridlist;
    }

    private VBox createProperty(String name, String info) throws UIException {
        VBox newProp = new VBox();
        Label propName = new Label(name);
        propName.getStyleClass().add("prop-label");
        String[] sep = info.split(":");
        try {
            Class<?> clazz = Class.forName(sep[0]);
            Constructor<?> constructor = (sep[1].equals("none")) ? clazz.getConstructor() : clazz.getConstructor(String.class);
            ControlProperty instance = (sep[1].equals("none")) ? (ControlProperty) constructor.newInstance() : (ControlProperty) constructor.newInstance(sep[1]);
            newProp.getChildren().addAll(propName, (Node) instance);
            populateOptions(instance, name);
            setAction(instance, sep[2]);
            myControls.add(instance);
        } catch (Exception e) {
            throw new UIException("Error creating properties controls");
        }
        return newProp;
    }

    // Populates fields with pre-existing values from Entity
    private void populateOptions(ControlProperty control, String label) throws Exception {
        String methodName = "get" + label;
        try {
            Method m = TestEntity.class.getDeclaredMethod(methodName);
            control.populateValue(m.invoke(myEntity).toString());
        } catch (NoSuchMethodException e) {

        }
    }

    private void setAction(ControlProperty control, String actionMethod) throws UIException {
        control.setAction(myEntity, actionMethod);
    }
}
