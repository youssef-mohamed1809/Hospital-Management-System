module com.hospital_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens com.hospital_management_system to javafx.fxml;
    exports com.hospital_management_system;

}