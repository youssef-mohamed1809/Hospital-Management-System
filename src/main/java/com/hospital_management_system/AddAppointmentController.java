package com.hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    DatePicker datePicker;

    @FXML
    TextField patientid;

    @FXML
    TextField doctorid;

    @FXML
    TextField time_input;

    @FXML
    Parent current_root;

    @FXML
    Label error_label;

    @FXML
    Button save_appointment;

    @FXML
    Label time_error;

    @FXML
    void add_appointment(ActionEvent event) throws SQLException, IOException {
        DatabaseManager db = new DatabaseManager();
        LocalDate date = datePicker.getValue();
        String date_string = date.toString();
        String patient_id = patientid.getText();
        String doctor_id = doctorid.getText();
        String time = time_input.getText();
        String date_saved = date_string + " " + time;
        String query = "select * from schedule where idemployee = \"" + doctor_id + "\" and time = \"" + date_saved
                + "\"";
        db.resultSet = db.statement.executeQuery(query);
        db.resultSet.next();
        if(!db.resultSet.next()){

            String add = "insert into schedule(idpatient, idemployee, time) " +
                    "values(\"" + patient_id + "\",\"" + doctor_id + "\", \"" + date_saved +"\")";
            db.statement.executeUpdate(add);
            Alert confirmation = new Alert(Alert.AlertType.NONE,
                    "Appointment added successfully",ButtonType.OK);
            confirmation.show();
            appointments(event);
        }else{
            error_label.setText("Doctor is busy at that time");
        }
    }

    @FXML
    void check_time() throws SQLException {
        DatabaseManager db = new DatabaseManager();
        LocalDate date = datePicker.getValue();
        String date_string = date.toString();
        String doctor_id = doctorid.getText();
        String time = time_input.getText();
        String date_saved = date_string + " " + time;
        String query = "select * from schedule where idemployee = \"" + doctor_id + "\" and time = \"" + date_saved
                + "\"";
        db.resultSet = db.statement.executeQuery(query);
        if(db.resultSet.next()){
            time_error.setTextFill(Color.RED);
            time_error.setText("An appointment is already booked at that time");
            save_appointment.setDisable(true);
        }else{
            save_appointment.setDisable(false);
            time_error.setTextFill(Color.GREEN);
            time_error.setText("Doctor is available at that time");
        }
    }

    @FXML
    void check_availability() throws SQLException {
        DatabaseManager db = new DatabaseManager();
        String doctor_id = doctorid.getText();
        LocalDate date = datePicker.getValue();

        int day = date.getDayOfWeek().getValue();
        day--;

        String query = "select * from available_days where doctorid = \"" + doctor_id + "\"";
        db.resultSet = db.statement.executeQuery(query);
        db.resultSet.next();

        boolean[] available_days = {db.resultSet.getBoolean("monday"), db.resultSet.getBoolean("tuesday"),
                db.resultSet.getBoolean("wednesday"), db.resultSet.getBoolean("thursday"),
                db.resultSet.getBoolean("friday"), db.resultSet.getBoolean("saturday"),
                db.resultSet.getBoolean("sunday")};

        if(!available_days[day]){
            error_label.setText("Doctor is not available at that day");
        }else{
            error_label.setText("");
        }


    }

    @FXML
    void add_patient_screen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("add-patient-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        stage.setTitle("Add Patient");
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        scene = new Scene(root);
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
    void appointments(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("appointments-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void doctors_list(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("doctors-list-view.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        save_appointment.setDisable(true);
    }
}
