module com.example.temamvc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.temamvc to javafx.fxml;
    exports com.example.temamvc;

    opens com.example.temamvc.view to javafx.fxml;
    exports com.example.temamvc.view;
}