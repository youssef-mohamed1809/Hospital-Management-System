package com.hospital_management_system;

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

public class InformationViewController implements Initializable {
    Parent root;
    Stage stage;
    Scene scene;
    @FXML
    Parent current_root;
    @FXML
    Label information_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseManager db = new DatabaseManager();
            String query = "select * from employees where id = \"" + LoginViewController.id + "\"";
            db.resultSet = db.statement.executeQuery(query);
            db.resultSet.next();
            String information = "Name: " + db.resultSet.getString("name") + "\n\n";
            information += "Job: " + db.resultSet.getString("job") + "\n\n";
            information += "ID: " + db.resultSet.getString("id") + "\n\n";
            information += "Username: " + db.resultSet.getString("username") + "\n\n";
            information += "Password: " + db.resultSet.getString("password");
            information_label.setText(information);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void doctors_list(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("doctors-list-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Doctor List");
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Login");
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void add_appointment_screen() throws IOException {
        root = FXMLLoader.load(getClass().getResource("add-appointment-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Add Appointment");
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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
        stage.setResizable(false);
        stage.show();
    }
}
