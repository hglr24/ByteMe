module engine {
    requires xstream;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    opens engine.external to xstream;
    exports engine.external;

}