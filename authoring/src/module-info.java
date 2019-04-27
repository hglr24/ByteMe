module authoring {
    requires runner;
    requires engine;
    requires data;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires xstream;
    requires java.desktop;
    requires voogasalad_util;
    opens events to voogasalad_util;
    opens ui.control to voogasalad_util;
    exports ui;
    exports ui.panes;
    exports ui.main;
}
