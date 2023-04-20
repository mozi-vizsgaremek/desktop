package com.example.cinemaapp.Menu;

import com.example.cinemaapp.Login.LoginController;
import com.example.cinemaapp.Auditorium.AuditoriumController;
import com.example.cinemaapp.Movie.ModifyMovieController;
import com.example.cinemaapp.Person.ModifyPersonController;
import com.example.cinemaapp.Person.Person;
import com.example.cinemaapp.Person.UsersCRUD;
import com.example.cinemaapp.rest.RetrofitSingleton;
import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainMenuController {
    public void logoutFromMainMenu(ActionEvent event) {
        ModifyPersonController.logoutAlert(event);
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

    public void goToModifyPerson(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ModifyPersonController.class.getResource("ModifyPersonView.fxml")));
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
    public void goToModifyMovie(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ModifyMovieController.class.getResource("ModifyMovieView.fxml")));
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
    public void goToAuditorium(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(AuditoriumController.class.getResource("AuditoriumView.fxml")));
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
