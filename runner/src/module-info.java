module runner {
    requires engine;
    requires data;
    requires javafx.graphics;
    exports runner.external;
    requires xstream;
    exports runner.internal to javafx.graphics;
}