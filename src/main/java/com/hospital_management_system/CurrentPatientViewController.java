package com.hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CurrentPatientViewController implements Initializable {
    Parent root;
    Stage stage;
    Scene scene;
    String patientid;
    @FXML
    Parent current_root;
    @FXML
    TextArea medical_history;
    @FXML
    TextArea diagnosis;
    @FXML
    TextArea treatment;
    @FXML
    Label name_label;
    @FXML
    Button finish_appointment;

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
    void schedule_screen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("schedule-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Schedule");
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void information(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("doctor-information-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("My Information");
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    String getCurrentPatientID() throws SQLException {
        DatabaseManager db = new DatabaseManager();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dateTimeFormatter.format(now);
        String minutes = time.substring(14, 16);
        int min = Integer.parseInt(minutes);
        time = time.substring(0, 13);
        String query = "select * from schedule where idemployee = \"" + LoginViewController.id + "\" and time like \"" + time + "%\"";
        db.resultSet = db.statement.executeQuery(query);
        String patientID = null;
        db.resultSet.next();


        try{
            System.out.println((Integer.parseInt((db.resultSet.getString("time")).substring(14, 16))));
            if((Integer.parseInt((db.resultSet.getString("time")).substring(14, 16))) >= 30 && min >= 30) {
                System.out.println("this patient");
                patientID = db.resultSet.getString("idpatient");
            } else{
                patientID = db.resultSet.getString("idpatient");
                System.out.println("that patient");
            }

        }catch(Exception e){
        }
        return patientID;


    }

    @FXML
    void finish_appointment_action(ActionEvent event) throws SQLException, IOException {
        DatabaseManager db = new DatabaseManager();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dateTimeFormatter.format(now);
        time = time.substring(0, 13);
        String query = "update schedule set diagnosis = \"" + diagnosis.getText() + "\", treatment = \"" + treatment.getText() + "\"" +
                " where time like \"" + time + "%\" and idpatient = \"" + patientid + "\"";
        db.statement.executeUpdate(query);

        query = "update patients set medical_history = \"" + medical_history.getText() + "\" where idpatients = \"" + patientid + "\"";
        db.statement.executeUpdate(query);
        Alert confirmation = new Alert(Alert.AlertType.NONE,
                "Appointment finished", ButtonType.OK);
        confirmation.show();
        schedule_screen(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String patientID = getCurrentPatientID();
            if(patientID == null){
                medical_history.setEditable(false);
                diagnosis.setEditable(false);
                treatment.setEditable(false);

                medical_history.setDisable(true);
                diagnosis.setDisable(true);
                treatment.setDisable(true);

                finish_appointment.setDisable(true);
                name_label.setText("No patient currently");

            }else{
                DatabaseManager db = new DatabaseManager();
                String query = "select * from patients where idpatients = \"" + patientID + "\"";
                patientid = patientID;
                db.resultSet = db.statement.executeQuery(query);
                db.resultSet.next();
                String name = db.resultSet.getString("full_name");
                int age = db.resultSet.getInt("age");
                String history = db.resultSet.getString("medical_history");
                name_label.setText(name + ", " + age + " years");
                if(history != null && !(history.equals(""))){
                    medical_history.setText(history);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
