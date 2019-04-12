module runner {
    requires engine;
    requires data;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.media;
    exports runner.external;
    requires xstream;
    exports runner.internal to javafx.graphics;
    opens runner.external to xstream;

    opens events to xstream;
    opens conditions to xstream;
    opens actions to xstream;
}