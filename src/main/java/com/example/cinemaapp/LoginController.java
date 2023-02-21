package com.example.cinemaapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField usernameFieldRegister;
    @FXML
    private PasswordField passwordFieldRegister;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailFieldReg;


    @Deprecated
    public void login(ActionEvent event) throws SQLException, ClassNotFoundException {

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

        LoginDB LoginDB = new LoginDB();
        boolean flag = LoginDB.validate(emailId, password);

        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
            System.out.println();
        } else {
            infoBox("Login Successful!", null, "Failed");
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
    @Deprecated
    public void register(ActionEvent event) {

        if (usernameFieldRegister.getText().isEmpty()) {
            showAlert("Form Error!",
                    "Please enter a username");
            return;
        }
        if (passwordFieldRegister.getText().isEmpty()) {
            showAlert( "Form Error!",
                    "Please enter a password");
            return;
        }
        if (emailFieldReg.getText().isEmpty()) {
            showAlert( "Form Error!",
                    "Please enter your email");
            return;
        }

        String usernameRegister = usernameFieldRegister.getText();
        String passwordRegister = passwordFieldRegister.getText();
        String emailRegister = emailFieldReg.getText();







    }
}