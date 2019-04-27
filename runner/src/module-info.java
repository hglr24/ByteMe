module runner {
    requires engine;
    requires data;
    requires javafx.graphics;
    requires javafx.media;
    exports runner.external;
    requires xstream;
    exports runner.internal to javafx.graphics;
    opens runner.external to xstream;
}