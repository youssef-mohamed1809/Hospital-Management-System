package com.hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorInformationViewController implements Initializable {

    @FXML
    Parent current_root;

    @FXML
    Label information_label;

    Parent root;
    Stage stage;
    Scene scene;


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
    void schedule_screen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("schedule-view.fxml"));
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
}
