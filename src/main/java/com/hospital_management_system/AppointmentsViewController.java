package com.hospital_management_system;

import com.hospital_management_system.DatabaseManager;
import com.hospital_management_system.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppointmentsViewController implements Initializable {
    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    Parent current_root;
    @FXML
    Label appointments;

    @FXML
    TilePane patients_id;

    @FXML
    TilePane available_doctors;


    void load_appointments() throws SQLException {
        DatabaseManager db = new DatabaseManager();
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();
        String query = "select * from schedule where time like \"" + date + "%\"";
        db.resultSet = db.statement.executeQuery(query);
        int count = 0;
        String patients[] = new String[100];
        for(int i = 0; i < 100; i++){
            patients[i] = null;
        }
        while(db.resultSet.next()){
            patients[count] = db.resultSet.getString("idpatient");
            count++;
        }

        RadioButton[] rbs = new RadioButton[count];
        for(int i = 0; i < count; i++){
            String rb_query = "select * from schedule where idpatient = \"" + patients[i] + "\"";

            rbs[i] = new RadioButton();
            rbs[i].setText(patients[i]);
            int finalI = i;

            db.resultSet = db.statement.executeQuery(rb_query);
            db.resultSet.next();
            if(db.resultSet.getBoolean("patient_present")){
                rbs[i].setSelected(true);
            }else{
                rbs[i].setSelected(false);
            }

            rbs[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        LocalDate localDate = LocalDate.now();
                        String date = localDate.toString();
                        if(rbs[finalI].isSelected()){
                            String update = "update schedule set patient_present = 1 where idpatient = \"" + patients[finalI] + "\" and time like \"" + date + "%\"";
                            db.statement.executeUpdate(update);
                        }else{
                            String update = "update schedule set patient_present = 0 where idpatient = \"" + patients[finalI] + "\"";
                            db.statement.executeUpdate(update);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            patients_id.getChildren().add(rbs[i]);
        }

        int day = localDate.getDayOfWeek().getValue();
        day--;
        String doctorid_query = "select * from employees where job = \"Doctor\"";
        db.resultSet = db.statement.executeQuery(doctorid_query);
        DatabaseManager availableDays = new DatabaseManager();
        DatabaseManager excusesdb = new DatabaseManager();

        while(db.resultSet.next()){
            String excuse_query = "select * from doctor_excuses where id = \"" + db.resultSet.getString("id") + "\"";
            excusesdb.resultSet = excusesdb.statement.executeQuery(excuse_query);
            boolean hasExcuse = false;
            while(excusesdb.resultSet.next()){
                if(excusesdb.resultSet.getString("date").equals(date)){
                    hasExcuse = true;
                }
            }

            String available = "select * from available_days where doctorid = \"" + db.resultSet.getString("id") + "\"";
            availableDays.resultSet = availableDays.statement.executeQuery(available);
            availableDays.resultSet.next();
            boolean[] available_days = {availableDays.resultSet.getBoolean("monday"), availableDays.resultSet.getBoolean("tuesday"),
                    availableDays.resultSet.getBoolean("wednesday"), availableDays.resultSet.getBoolean("thursday"),
                    availableDays.resultSet.getBoolean("friday"), availableDays.resultSet.getBoolean("saturday"),
                    availableDays.resultSet.getBoolean("sunday")};
            if(available_days[day]){
                DatabaseManager db2 = new DatabaseManager();
                String id = db.resultSet.getString("id");
                String q2 = "select * from employees where id = \"" + id + "\"";
                db2.resultSet = db2.statement.executeQuery(q2);
                db2.resultSet.next();
                CheckBox c = new CheckBox();
                c.setText("Dr. " + db2.resultSet.getString("name"));
                if(!hasExcuse) {
                    c.setSelected(true);
                }
                available_doctors.getChildren().add(c);
                c.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //Doctor Excuse
                        if(c.isSelected()){
                            try {
                                DatabaseManager doctor_excuses = new DatabaseManager();
                                String query = "delete from doctor_excuses where id = \"" + id + "\" and date = \"" + date + "\"";
                                doctor_excuses.statement.execute(query);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }else{
                            TextInputDialog excuse_reason = new TextInputDialog();
                            excuse_reason.setHeaderText("Write reason of absence");
                            excuse_reason.setTitle("Doctor Excuse");
                            //excuse_reason.show();
                            Optional<String> reason = excuse_reason.showAndWait();
                            AtomicBoolean isEmpty = new AtomicBoolean(true);
                            reason.ifPresent(string -> {
                                try {
                                    isEmpty.set(false);
                                    DatabaseManager doctor_excuses = new DatabaseManager();
                                    String query = "insert into doctor_excuses(id, date, reason) " +
                                            "values(\"" + id + "\",\"" + date + "\", \"" +
                                            string + "\")";
                                    doctor_excuses.statement.executeUpdate(query);
                                    //db.statement.executeUpdate(query);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            if(isEmpty.getOpaque()){
                                try {
                                    DatabaseManager doctor_excuses = new DatabaseManager();
                                    String query = "insert into doctor_excuses(id, date) " +
                                            "values(\"" + id + "\",\"" + date + "\")";
                                    doctor_excuses.statement.executeUpdate(query);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                            }


                        }
                    }
                });
            }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            load_appointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
