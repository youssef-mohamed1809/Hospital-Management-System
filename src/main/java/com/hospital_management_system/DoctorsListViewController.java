package com.hospital_management_system;

import com.hospital_management_system.DatabaseManager;
import com.hospital_management_system.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorsListViewController implements Initializable {
    @FXML
    Label doctors;

    @FXML
    Parent current_root;

    Parent root;
    Stage stage;
    Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseManager db = new DatabaseManager();
            String query = "select * from employees where job = \"Doctor\"";
            db.resultSet = db.statement.executeQuery(query);
            String doctors_list = "";
            while(db.resultSet.next()){
                String doctor_name = db.resultSet.getString("name");
                String doctor_id = db.resultSet.getString("id");
                doctors_list = "Dr. " + doctor_name + ", ID: " + doctor_id;
            }
            doctors.setText(doctors_list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void add_patient_screen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("add-patient-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        stage.setTitle("Add Patient");
        scene = new Scene(root);
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void add_appointment_screen() throws IOException {
        root = FXMLLoader.load(getClass().getResource("add-appointment-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void information(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("information-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
}
