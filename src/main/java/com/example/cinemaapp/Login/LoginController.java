package com.example.cinemaapp.Login;

import com.example.cinemaapp.Menu.MainMenuController;
import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class LoginController{

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    public void login(ActionEvent event) throws IOException {

        if (usernameField.getText().isEmpty()) {
            showAlert("Empty username field",
                    "Please enter your username");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert( "Empty password field",
                    "Please enter a password");
            return;
        }
        String usernameId = usernameField.getText();
        String password = passwordField.getText();
        int flag = TokenManager.login(usernameId, password);
        //documentation in TokenManager.java starting at line 25
        if (flag == 1) {
            infoBox("You entered wrong username or password!",
                    "Error", "Failed");
        } else {
            if (flag == 2) {
                infoBox("You are not an admin!",
                        "Error", "Failed");
            } else {
                if (flag == 3) {
                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(MainMenuController.class.getResource("MainMenuView.fxml")));
                        Stage stage = new Stage();
                        stage.setTitle("Main menu");
                        stage.setMaximized(true);
                        stage.setScene(new Scene(root, 800, 600));
                        stage.setOnCloseRequest(CloseEvent -> {
                            CloseEvent.consume();
                            exit(stage);
                        });
                        stage.show();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Do you really want to exit the application?");
        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
            System.exit(0);
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
