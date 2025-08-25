module com.example.temamvc {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens com.example.temamvc to javafx.fxml;
    exports com.example.temamvc;

    opens com.example.temamvc.view to javafx.fxml;
    exports com.example.temamvc.view;
}