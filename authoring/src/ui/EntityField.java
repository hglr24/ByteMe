package ui;

import engine.external.component.CameraComponent;
import engine.external.component.Component;
import engine.external.component.GroupComponent;
import engine.external.component.HealthComponent;
import engine.external.component.HeightComponent;
import engine.external.component.NameComponent;
import engine.external.component.SpriteComponent;
import engine.external.component.VisibilityComponent;
import engine.external.component.WidthComponent;
import engine.external.component.XAccelerationComponent;
import engine.external.component.XPositionComponent;
import engine.external.component.XVelocityComponent;
import engine.external.component.YAccelerationComponent;
import engine.external.component.YPositionComponent;
import engine.external.component.YVelocityComponent;
import engine.external.component.ZPositionComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Harry Ross
 */
public enum EntityField {

    LABEL (NameComponent.class, String.class, "Label", null, true, false, false),
    GROUP (GroupComponent.class, String.class, "Group", null, true, false, false),
    IMAGE (SpriteComponent.class, String.class, "Image", null, true, false, false),
    EVENTS (null, null, "Events", null, false, false, false), // This exists for the label only, consider removing?

    X (XPositionComponent.class, Double.class, "X", "0.0", false, true, true),
    Y (YPositionComponent.class, Double.class, "Y", "0.0", false, true, true),
    Z (ZPositionComponent.class, Double.class, "Z", "0.0", false, true, false),
    XSCALE (WidthComponent.class, Double.class, "XScale", "0.0", true, true, true),
    YSCALE (HeightComponent.class, Double.class, "YScale" , "0.0", true, true, true),

    FOCUS (CameraComponent.class, Boolean.class, "Focus", "false", false, true, false),
    VISIBLE (VisibilityComponent.class, Boolean.class, "Visible", "true", false, true, false),

    XSPEED (XVelocityComponent.class, Double.class, "XSpeed", "0.0", true, false, true),
    YSPEED (YVelocityComponent.class, Double.class, "YSpeed", "0.0", true, false, true),
    XACCELERATION (XAccelerationComponent.class, Double.class, "XAcceleration", "0.0", true, false, true),
    YACCELERATION (YAccelerationComponent.class, Double.class, "YAcceleration", "0.0", true, false, true),
    HEALTH (HealthComponent.class, Double.class, "Health", "0.0", true, false, true);

    private Class<? extends Component> myComponentClass;
    private Class<?> myComponentDataType;
    private String myLabel;
    private String myDefaultValue;
    private boolean myCommon;
    private boolean myDefault;
    private boolean myTextable; // Can it be added as an additional property in pane

    EntityField(Class<? extends Component> componentClass, Class<?> dataType, String label, String defaultValue, boolean common, boolean isDefault, boolean textable) {
        myComponentClass = componentClass;
        myComponentDataType = dataType;
        myLabel = label;
        myDefaultValue = defaultValue;
        myCommon = common;
        myDefault = isDefault;
        myTextable = textable;
    }

    public static List<String> getTextFieldList() {
        List<String> rtn = new ArrayList<>();
        for (EntityField field : EntityField.values()) {
            if (field.myTextable)
                rtn.add(field.name());
        }
        return rtn;
    }

    public static List<EntityField> getDefaultFields() {
        List<EntityField> defaultList = new ArrayList<>(Arrays.asList(EntityField.values()));
        defaultList.removeIf(field -> !field.myDefault);
        return defaultList;
    }

    public static List<EntityField> getCommonFields() {
        List<EntityField> commonList = new ArrayList<>(Arrays.asList(EntityField.values()));
        commonList.removeIf(field -> !field.myCommon);
        return commonList;
    }

    public String getDefaultValue() {
        return myDefaultValue;
    }

    public Class<? extends Component> getComponentClass() {
        return myComponentClass;
    }

    public Class<?> getComponentDataType() {
        return myComponentDataType;
    }

    public boolean isTextable() {
        return myTextable;
    }

    public boolean isDefault() {
        return myDefault;
    }

    public String getLabel() {
        return myLabel;
    }
}
