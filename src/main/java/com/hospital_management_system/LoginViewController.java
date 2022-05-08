package com.hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginViewController{
    public static String id = null;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label error_label;
    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    void login(ActionEvent event) throws SQLException, IOException {
        String username_input = username.getText();
        String password_input = password.getText();
        if(username_input == "" || password_input.equals("")){
            if(username_input == ""){
                error_label.setText("Username field is empty");
            }else if(password_input == ""){
                error_label.setText("Password field is empty");
            }
        }else{
            login_logic(username_input, password_input, error_label, event);
        }

    }

    void login_logic(String username, String password, Label label, ActionEvent event) throws SQLException, IOException {
        DatabaseManager db = new DatabaseManager();
        String query = "select * from employees";
        db.resultSet = db.statement.executeQuery(query);
        boolean loggedin = false;
        boolean wrong_password = false;
        while(db.resultSet.next()){
            if(username.equals(db.resultSet.getString("username"))){
                if(password.equals(db.resultSet.getString("password"))){
                    loggedin = true;
                    if(db.resultSet.getString("job").equals("Doctor")){
                        id = db.resultSet.getString("id");
                        loadDoctorScreen(event);
                    }else if(db.resultSet.getString("job").equals("Secretary")){
                        id = db.resultSet.getString("id");
                        loadSecretaryScreen(event);
                    }

                }else{
                    wrong_password = true;
                }
            }
        }
        if(!loggedin && wrong_password){
            error_label.setText("Wrong Password");
        }else if(!loggedin && !wrong_password){
            error_label.setText("Invalid Username");
        }


    }

    void loadSecretaryScreen(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("appointments-view.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.setTitle("Schedule");
        stage.getIcons().add(icon);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    void loadDoctorScreen(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("schedule-view.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Image icon = new Image(Main.class.getResource("icon.png").toExternalForm());
        stage.setTitle("Schedule");
        stage.getIcons().add(icon);
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
