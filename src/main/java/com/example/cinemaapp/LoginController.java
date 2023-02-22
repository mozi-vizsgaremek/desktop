package com.example.cinemaapp;

import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class LoginController{

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    public void login(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        if (usernameField.getText().isEmpty()) {
            showAlert("Form Error!",
                    "Please enter your email id");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert( "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = usernameField.getText();
        String password = passwordField.getText();

        boolean flag = TokenManager.login(emailId, password);

        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
            System.out.println();
        } else {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("ModifyPersonView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Main menu");
                stage.setScene(new Scene(root, 450, 450));
                stage.setMaximized(true);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide(); //Hiding the login page
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }



}
