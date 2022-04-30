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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ScheduleViewController implements Initializable {

    @FXML
    Parent current_root;

    @FXML
    Label appointments_label;

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    void information(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("doctor-information-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
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
    void current_patient(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("current-patient-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseManager db = new DatabaseManager();
            LocalDate localDate = LocalDate.now();
            String date = localDate.toString();
            String query = "select * from schedule where idemployee = \"" + LoginViewController.id + "\" and time like \"" + date + "%\"";
            db.resultSet = db.statement.executeQuery(query);
            String label = "";
            while(db.resultSet.next()){
                //System.out.println(db.resultSet.getString("time"));
                label += db.resultSet.getString("idpatient") + ", at " + (db.resultSet.getString("time")).substring(11);
                label += "\n";
            }
            appointments_label.setText(label);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
