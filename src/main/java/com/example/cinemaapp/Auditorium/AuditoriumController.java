package com.example.cinemaapp.Auditorium;

import com.example.cinemaapp.Menu.MainMenuController;
import com.example.cinemaapp.Person.ModifyPersonController;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AuditoriumController {


    // region Declarations
    public Label idLabel;
    public Label nameLabel;
    public Label seatsLabel;
    public Button deleteButton;
    public Button createButton;
    public TextField searchByName;
    @FXML
    public ListView<Auditorium> listOfAuditoriums;
    // endregion
    @FXML
    private void initialize() throws IOException {
        addItemsToListOfAuditoriums();
        addListenerToListView();
    }
    public void addItemsToListOfAuditoriums() throws IOException {
        AuditoriumCRUD auditoriumCRUD = RetrofitSingleton.getInstance().create(AuditoriumCRUD.class);
        var call = auditoriumCRUD.getAuditoriums(TokenManager.getAccessToken());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Auditorium>> call, Response<List<Auditorium>> response) {
                List<Auditorium> auditoriumList = response.body();
                if (auditoriumList != null) {
                    ObservableList<Auditorium> observableList = FXCollections.observableArrayList();
                    observableList.addAll(auditoriumList);
                    listOfAuditoriums.getItems().clear();
                    listOfAuditoriums.setItems(observableList);
                    addListenerToTextField();
                } else {
                    System.out.println("The auditorium list is null.");
                }
            }
            @Override
            public void onFailure(Call<List<Auditorium>> call, Throwable t) {
                System.out.println("The API call failed.");
            }
        });
    }

    private void addListenerToListView() {
        listOfAuditoriums.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Auditorium selectedAuditorium = listOfAuditoriums.getSelectionModel().getSelectedItem();
            clearAuditoriumData();
            addPersonData(selectedAuditorium);
        });
    }
    private void addPersonData(Auditorium selectedAuditorium) {
        if (selectedAuditorium != null) {
            idLabel.setText(selectedAuditorium.id);
            nameLabel.setText(selectedAuditorium.name);
            seatsLabel.setText(selectedAuditorium.seats.toString());
        }


    }
    private void clearAuditoriumData() {
        idLabel.setText("");
        nameLabel.setText("");
        seatsLabel.setText("");
    }
    private void addListenerToTextField() {
        ObservableList<Auditorium> data = FXCollections.observableArrayList(listOfAuditoriums.getItems());
        FilteredList<Auditorium> filteredData = new FilteredList<>(data);
        searchByName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(auditorium -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return auditorium.toString().toLowerCase().contains(newValue.toLowerCase());
            });
        });
        listOfAuditoriums.setItems(filteredData);
    }
    public void createNewAuditorium() {
        try {
            CreateAuditoriumController controller2 = new CreateAuditoriumController(listOfAuditoriums);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAuditoriumView.fxml"));
            loader.setController(controller2);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Creating auditorium");
            stage.setScene(new Scene(root, 1024, 768));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void deleteAuditorium() throws IOException {
        AuditoriumCRUD auditoriumCRUD = RetrofitSingleton.getInstance().create(AuditoriumCRUD.class);
        String auditoriumId = listOfAuditoriums.getSelectionModel().getSelectedItem().id;
        Auditorium deletedAuditorium = listOfAuditoriums.getSelectionModel().getSelectedItem();
        Call<Void> call = auditoriumCRUD.deleteAuditorium(TokenManager.getAccessToken(), auditoriumId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Platform.runLater(() -> {
                    listOfAuditoriums.getItems().remove(deletedAuditorium);
                    System.out.println(listOfAuditoriums.getItems().remove(deletedAuditorium));
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
                        listOfAuditoriums.refresh();
                    });
                });
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("The API call failed.");
            }
        });
    }

    public void logout(ActionEvent event) {
        ModifyPersonController.logoutAlert(event);
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
