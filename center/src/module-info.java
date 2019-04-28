module center {
    requires data;
    requires runner;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.controls;
    requires xstream;
    requires voogasalad_util;

    exports center.external;
}