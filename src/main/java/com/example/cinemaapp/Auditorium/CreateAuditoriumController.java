package com.example.cinemaapp.Auditorium;

import com.example.cinemaapp.Person.Person;
import com.example.cinemaapp.Person.UsersCRUD;
import com.example.cinemaapp.rest.RetrofitSingleton;
import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

public class CreateAuditoriumController {
    // region Declarations
    public TextField nameTextField;
    public TextField seatsTextField;
    public Button createButton;
    public Auditorium newAuditorium;
    private ListView<Auditorium> listOfAuditoriums;

    // endregion
    public CreateAuditoriumController(ListView<Auditorium> listOfAuditoriums) {
        this.listOfAuditoriums = listOfAuditoriums;
    }

    public void createAuditorium() throws IOException {
        try {
            if (nameTextField.getText() == null || nameTextField.getText().isEmpty() ||
                    seatsTextField.getText() == null || seatsTextField.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setContentText("You must not leave any field empty!");
                if (alert.showAndWait().get() == ButtonType.OK){
                    alert.hide();
                }
            } else {
                Auditorium newAuditorium = new Auditorium(nameTextField.getText(),
                        Integer.parseInt(seatsTextField.getText()));
                createNewAuditorium(newAuditorium);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setContentText("You must only write numbers in the seats field!");
            if (alert.showAndWait().get() == ButtonType.OK){
                alert.hide();
            }
        }
    }
    private void createNewAuditorium(Auditorium auditorium) throws IOException {
        AuditoriumCRUD auditoriumCRUD = RetrofitSingleton.getInstance().create(AuditoriumCRUD.class);
        Call<Auditorium> call = auditoriumCRUD.createAuditorium(TokenManager.getAccessToken(), auditorium);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Auditorium> call, Response<Auditorium> response) {
                if (response.isSuccessful()) {
                    Platform.runLater(() -> {
                        newAuditorium = response.body();
                        if (newAuditorium != null) {
                            listOfAuditoriums.getItems().add(newAuditorium);
                        }
                        VBox vBox = new VBox();
                        Label label = new Label();
                        label.setText("Auditorium successfully created!");
                        vBox.getChildren().add(label);
                        Button btn = new Button();
                        btn.setText("Okay");
                        vBox.getChildren().add(btn);
                        vBox.setAlignment(Pos.CENTER);
                        Stage stage = new Stage();
                        stage.setTitle("Success");
                        stage.setScene(new Scene(vBox, 230, 100));
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.show();
                        btn.setOnAction(actionEvent -> {
                            Stage createStage = (Stage) createButton.getScene().getWindow();
                            createStage.close();
                            stage.close();
                        });
                    });
                }
            }
            @Override
            public void onFailure(Call<Auditorium> call, Throwable t) {
                System.out.println("Failure");
            }
        });
    }
}
