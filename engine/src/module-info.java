module engine {
    requires xstream;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires org.junit.jupiter.api;
    requires data;
    requires voogasalad_util;

    opens engine.external to xstream,voogasalad_util;
    opens engine.external.actions to xstream,voogasalad_util;
    opens engine.external.component to xstream,voogasalad_util;
    opens engine.external.events to xstream,voogasalad_util;
    opens engine.internal.systems to voogasalad_util;

    exports engine.external;
    exports engine.external.component;
    exports engine.external.actions;
    exports engine.external.conditions;
    exports engine.external.events;
}
