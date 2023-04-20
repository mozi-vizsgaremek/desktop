package com.example.cinemaapp.Movie;

import com.example.cinemaapp.Login.LoginController;
import com.example.cinemaapp.Menu.MainMenuController;
import com.example.cinemaapp.rest.RetrofitSingleton;
import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ModifyMovieController {
    public TextField searchByName;
    public Label idLabel;
    public Label titleLabel;
    public Label subtitleLabel;
    public Label descriptionLabel;
    public Label durationMinsLabel;
    public Label thumbnailUrlLabel;
    public Label bannerUrlLabel;
    public Button uploadThumbnailUrl;
    public Button uploadBannerUrl;
    public Button copyThumbnail;
    public Button copyBanner;
    @FXML
    private ListView<Movie> listOfMovies;
    private final Map<String, Movie> idMap = new HashMap<>();


    @FXML
    private void initialize() throws IOException {
        addItemsToListOfMovies();
        addListenerToListView();

    }

    private void addItemsToListOfMovies() throws IOException {
        MoviesCRUD moviesCRUD = RetrofitSingleton.getInstance().create(MoviesCRUD.class);
        var call = moviesCRUD.getMovies(TokenManager.getAccessToken());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> personList = response.body();
                if (personList != null) {
                    ObservableList<Movie> observableList = FXCollections.observableArrayList();
                    observableList.addAll(personList);
                    listOfMovies.setItems(observableList);
                    for (var person : personList) {
                        idMap.put(person.id, person);
                    }
                    addListenerToTextField();
                } else {
                    System.out.println("The person list is null.");
                }
            }
            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                System.out.println("The API call failed.");
            }
        });
    }

    private void addListenerToTextField() {
        var data = FXCollections.observableArrayList(listOfMovies.getItems());
        FilteredList<Movie> filteredData = new FilteredList<>(data, p -> true);
        searchByName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return person.toString().toLowerCase().contains(lowerCaseFilter);
            });
            listOfMovies.setItems(filteredData);
        });
    }
    private void addListenerToListView() {
        listOfMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Movie selectedMovie = listOfMovies.getSelectionModel().getSelectedItem();
            clearMovieData();
            addMovieData(selectedMovie);
        });
    }
    private void clearMovieData() {
        idLabel.setText("");
        titleLabel.setText("");
        subtitleLabel.setText("");
        descriptionLabel.setText("");
        durationMinsLabel.setText("");
        thumbnailUrlLabel.setText("");
        bannerUrlLabel.setText("");
        copyThumbnail.setDisable(true);
        copyBanner.setDisable(true);
    }
    private void addMovieData(Movie movie) {
        if (movie != null) {
            idLabel.setText(movie.id);
            titleLabel.setText(movie.title);
            subtitleLabel.setText(movie.subtitle);
            descriptionLabel.setText(movie.description);
            durationMinsLabel.setText(String.valueOf(movie.durationMins));
            uploadThumbnailUrl.setDisable(false);
            uploadBannerUrl.setDisable(false);
            if (movie.thumbnailUrl != null) {
                thumbnailUrlLabel.setText(movie.thumbnailUrl);
                copyThumbnail.setDisable(false);
                copyThumbnail.setOnMouseClicked(mouseEvent -> {
                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(thumbnailUrlLabel.getText());
                    clipboard.setContent(content);
                });
            }
            if (movie.bannerUrl != null) {
                bannerUrlLabel.setText(movie.bannerUrl);
                copyBanner.setDisable(false);
                copyBanner.setOnMouseClicked(mouseEvent -> {
                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(bannerUrlLabel.getText());
                    clipboard.setContent(content);
                });
            }
        }
    }
    public void uploadThumbnail() throws IOException {
        MovieImageDto movieImageDto = new MovieImageDto();
        Movie selectedMovie = listOfMovies.getSelectionModel().getSelectedItem();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String path = selectedFile.getPath();
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(path));
                movieImageDto.thumbnail = Base64.getEncoder().encodeToString(imageBytes);
                movieImageDto.banner = null;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        instanceHelper(movieImageDto, selectedMovie);
    }
    public void uploadBanner() throws IOException {
        MovieImageDto movieImageDto = new MovieImageDto();
        Movie selectedMovie = listOfMovies.getSelectionModel().getSelectedItem();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String path = selectedFile.getPath();
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(path));
                movieImageDto.thumbnail = null;
                movieImageDto.banner = Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        instanceHelper(movieImageDto, selectedMovie);
    }

    private void instanceHelper(MovieImageDto movieImageDto, Movie selectedMovie) throws IOException {
        MoviesCRUD moviesCRUD = RetrofitSingleton.getInstance().create(MoviesCRUD.class);
        var call = moviesCRUD.addImage(TokenManager.getAccessToken(), movieImageDto, selectedMovie.id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<MovieUrlDto> call, Response<MovieUrlDto> response) {
                if (response.isSuccessful()) {
                    Platform.runLater(() -> {
                        VBox vBox = new VBox();
                        Label label = new Label();
                        label.setText("Image successfully uploaded!");
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
                            stage.close();
                            listOfMovies.getSelectionModel().getSelectedItem();
                            if (movieImageDto.banner != null) {
                                selectedMovie.bannerUrl = response.body().bannerUrl;
                            }
                            if (movieImageDto.thumbnail != null) {
                                selectedMovie.thumbnailUrl = response.body().thumbnailUrl;
                            }
                            listOfMovies.refresh();
                            clearMovieData();
                            addMovieData(selectedMovie);
                        });
                    });
                }
            }
            @Override
            public void onFailure(Call<MovieUrlDto> call, Throwable throwable) {}
        });
    }
    public void deleteMovie() throws IOException {
        MoviesCRUD moviesCRUD = RetrofitSingleton.getInstance().create(MoviesCRUD.class);
        String movieId = listOfMovies.getSelectionModel().getSelectedItem().id;
        Call<Void> call = moviesCRUD.deleteMovie(TokenManager.getAccessToken(), movieId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Platform.runLater(() -> {
                    Movie deletedMovie = listOfMovies.getSelectionModel().getSelectedItem();
                    listOfMovies.getItems().remove(deletedMovie);
                    VBox vBox = new VBox();
                    Label label = new Label();
                    label.setText("Auditorium successfully deleted!");
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
                        stage.close();
                        listOfMovies.refresh();
                    });
                });
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("The API call failed.");
            }
        });
    }
    public void createNewMovie() {
        try {
            CreateMovieController controller2 = new CreateMovieController(listOfMovies);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateMovieView.fxml"));
            loader.setController(controller2);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Creating movie");
            stage.setScene(new Scene(root, 1024, 768));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to log out!");
        alert.setContentText("Do you really want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK){
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(LoginController.class.getResource("LoginView.fxml")));
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
            System.exit(0);
        }
    }
    public void backToMainMenu(ActionEvent event) throws IOException {
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



}
