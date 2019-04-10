module runner {
    requires engine;
    requires data;
    requires javafx.graphics;
    requires javafx.controls;
    exports runner.external;
    requires xstream;
    exports runner.internal to javafx.graphics;
    opens runner.external to xstream;

}