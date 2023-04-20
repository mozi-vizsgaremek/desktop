package com.example.cinemaapp.Movie;

import com.example.cinemaapp.rest.RetrofitSingleton;
import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class CreateMovieController {
    private ListView<Movie> listOfMovies;
    public TextField titleTextField;
    public TextField subtitleTextField;
    public TextField descriptionTextField;
    public TextField durationMinsTextField;
    public Movie newMovie;
    public Button createButton;


    public CreateMovieController(ListView<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    public void createMovie() throws IOException {
        try {
            if (titleTextField.getText() == null || subtitleTextField.getText().isEmpty() ||
                    descriptionTextField.getText() == null || durationMinsTextField.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setContentText("You must not leave any field empty!");
                if (alert.showAndWait().get() == ButtonType.OK){
                    alert.hide();
                }
            } else {
                Movie newMovie = new Movie(titleTextField.getText(), subtitleTextField.getText(),
                        descriptionTextField.getText(),Integer.parseInt(durationMinsTextField.getText()));
                createNewMovie(newMovie);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setContentText("You must only write numbers in the durationMins field!");
            if (alert.showAndWait().get() == ButtonType.OK){
                alert.hide();
            }
        }
    }
    public void createNewMovie(Movie movie) throws IOException {
        MoviesCRUD moviesCRUD = RetrofitSingleton.getInstance().create(MoviesCRUD.class);
        Call<Movie> call = moviesCRUD.createMovie(TokenManager.getAccessToken(), movie);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Platform.runLater(() -> {
                        newMovie = response.body();
                        if (newMovie != null) {
                            listOfMovies.getItems().add(newMovie);
                        }
                        VBox vBox = new VBox();
                        Label label = new Label();
                        label.setText("Movie successfully created!");
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
            public void onFailure(Call<Movie> call, Throwable t) {
                System.out.println("Failure");
            }
        });
    }
}
