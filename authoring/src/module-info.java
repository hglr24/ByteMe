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

    exports authoring.external;
    exports ui;
    exports ui.panes;
    exports ui.main;
}
