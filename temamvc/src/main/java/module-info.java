module com.example.temamvc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.temamvc to javafx.fxml;
    exports com.example.temamvc;
}