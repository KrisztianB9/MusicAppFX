module ro.umfst.oop.musicapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens ro.umfst.oop.musicapp to javafx.fxml;
    exports ro.umfst.oop.musicapp;
}