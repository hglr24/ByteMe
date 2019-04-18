module engine {
    requires xstream;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires org.junit.jupiter.api;
//    requires voogasalad_util;
    opens engine.external to xstream;
    opens engine.external.actions to xstream;
    opens engine.external.component to xstream;
    opens engine.external.events to xstream;
    exports engine.external;
    exports engine.external.component;
    exports engine.external.actions;
    exports engine.external.conditions;
    exports engine.external.events;
}
