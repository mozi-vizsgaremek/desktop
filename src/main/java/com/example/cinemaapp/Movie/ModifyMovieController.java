package com.example.cinemaapp.Movie;

import com.example.cinemaapp.Login.LoginController;
import com.example.cinemaapp.Menu.MainMenuController;
import com.example.cinemaapp.Person.Person;
import com.example.cinemaapp.Person.UsersCRUD;
import com.example.cinemaapp.rest.RetrofitSingleton;
import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModifyMovieController {
    public TextField searchByName;
    public Label idLabel;
    public Label titleLabel;
    public Label subtitleLabel;
    public Label descriptionLabel;
    public Label durationMinsLabel;
    public Label thumbnailUrlLabel;
    public Label bannerUrlLabel;
    @FXML
    private ListView<Movie> listOfMovies;
    private final Map<String, Movie> idMap = new HashMap<>();


    @FXML
    private void initialize() throws IOException {
        //addItemsToListOfMovies();
        addListenerToListView();
    }

    /*private void addItemsToListOfMovies() throws IOException {
        UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
        var call = usersCRUD.getPeople(TokenManager.getAccessToken());
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
    }*/

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
            String selectedId = newValue.id;
            Movie selectedPerson = idMap.get(selectedId);
            clearMovieData();
            //addPersonData(selectedPerson.id);
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
    }
    /*private void addPersonData(String id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        var p = idMap.get(id);
        titleLabel.setText(idMap.get(id).id);
        first_nameLabel.setText(idMap.get(id).title);
        last_nameLabel.setText(idMap.get(id).subtitle);
        hourly_wageLabel.setText(idMap.get(id).description);
        reg_dateLabel.setText(idMap.get(id).durationMins);
        if (p.hireDate != null) {
            hire_dateLabel.setText(formatter.format(idMap.get(id).thumbnailUrl));
        }
        roleLabel.setText(idMap.get(id).role);
        if (p.managerId != null) {
            managerIdLabel.setText(p.managerId);
        }
    }*/
    public void deleteAuditorium(ActionEvent actionEvent) {

    }
    public void createNewAuditorium(ActionEvent actionEvent) {

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
