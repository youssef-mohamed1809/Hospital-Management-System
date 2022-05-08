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
import java.util.ResourceBundle;
import java.util.UUID;

public class AddPatientController implements Initializable {
    Parent root;
    Stage stage;
    Scene scene;
    @FXML
    Parent current_root;
    @FXML
    TextField id_field;
    @FXML
    TextField name;
    @FXML
    TextField address;
    @FXML
    TextField age;
    @FXML
    TextField job;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    @FXML
    Label error_label;
    @FXML
    TextField phone;
    String id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id = UUID.randomUUID().toString();
        id = id.substring(0, 8);
        id_field.setText("Patient ID: " + id);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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
    void information(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("information-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("My Information");
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
    void appointments(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("appointments-view.fxml"));
        stage = (Stage)current_root.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Appointments");
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void add_patient(ActionEvent event){
        if(name.getText().equals("") || age.getText().equals("") || job.getText().equals("") || address.getText().equals("") ||phone.getText().equals("") || (!male.isSelected() && !female.isSelected())){
            error_label.setText("Please make sure to fill all data correctly");
        }else{
            try{
                String patient_name = name.getText();
                int patient_age = Integer.parseInt(age.getText());
                String patient_job = job.getText();
                String patient_address = address.getText();
                String patient_phone = phone.getText();
                String gender;
                if(male.isSelected()){
                    gender = "Male";
                }else{
                    gender = "Female";
                }
                error_label.setText("");
                DatabaseManager db = new DatabaseManager();
                String insert_patient = "insert into patients(idpatients, full_name, age, gender, phone, address, job) ";
                insert_patient += "values (\'" + id + "\', \'" + patient_name + "\', " + patient_age + ", \'" + gender + "\', \'" + patient_phone + "\', \'" +
                    patient_address + "\', \'" + patient_job + "\')";
                db.statement.executeUpdate(insert_patient);
                Alert confirmation = new Alert(Alert.AlertType.NONE,
                        "Patient added successfully",ButtonType.OK);
                confirmation.show();
                appointments(event);

            }catch (Exception e){
                error_label.setText("Please make sure to fill all data correctly");
            }
        }
    }
}
