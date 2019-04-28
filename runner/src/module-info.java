module runner {
    requires engine;
    requires data;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires org.junit.jupiter.api;
    exports runner.external;
    requires xstream;
    exports runner.internal to javafx.graphics;
    opens runner.external to xstream;
}