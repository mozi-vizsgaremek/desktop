package com.example.cinemaapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button goToModifyPersonButton;
    @FXML
    private Button goToModifyMoviesButton;
    @FXML
    private Button goToModifyAuditoriumsButton;
    @FXML
    private Button logoutFromMainMenuButton;




    @FXML
    private void initialize() {

    }




    public void logoutFromMainMenu(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("You're about to log out!");
            alert.setContentText("Do you really want to log out?");
            if (alert.showAndWait().get() == ButtonType.OK){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("User Login");
                    stage.setScene(new Scene(root, 800, 600));
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    public void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Do you really want to exit the application?");

        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

    public void goToModifyPerson(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ModifyPersonView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Modify a person");
        stage.setMaximized(true);
        stage.setScene(new Scene(root, 800, 600));
        stage.setOnCloseRequest(CloseEvent -> {
            CloseEvent.consume();
            exit(stage);
        });
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    public void goToModifyMovies(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ModifyMoviesView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Modify a movie");
        stage.setMaximized(true);
        stage.setScene(new Scene(root, 800, 600));
        stage.setOnCloseRequest(CloseEvent -> {
            CloseEvent.consume();
            exit(stage);
        });
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    public void goToModifyAuditoriums(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ModifyAuditoriumsView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Modify an auditorium");
        stage.setMaximized(true);
        stage.setScene(new Scene(root, 800, 600));
        stage.setOnCloseRequest(CloseEvent -> {
            CloseEvent.consume();
            exit(stage);
        });
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
