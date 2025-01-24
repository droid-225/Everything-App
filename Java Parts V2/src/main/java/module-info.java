module com.imp.java_parts_v2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires android.json;

    opens com.imp.java_parts_v2 to javafx.fxml;
    exports com.imp.java_parts_v2;
}