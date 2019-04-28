module runner {
    requires engine;
    requires data;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires org.junit.jupiter.api;
    exports runner.external;
    requires xstream;
    requires java.desktop;
    requires javafx.web;
    exports runner.internal to javafx.graphics;
    opens runner.external to xstream;
}