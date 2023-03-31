package com.example.cinemaapp;

import com.example.cinemaapp.rest.RetrofitSingleton;
import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.apache.commons.lang3.StringUtils;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ModifyPersonController {


    // region Declarations
    @FXML
    private GridPane Properties;
    @FXML
    private ListView<Person> listOfPeople;
    @FXML
    private TextField searchByName;
    @FXML
    private Button modifyUsername;
    @FXML
    private Button modifyPassword;
    @FXML
    private Button modifyFirstName;
    @FXML
    private Button modifyLastName;
    @FXML
    private Button modifyHourlyWage;
    @FXML
    private Button modifyRole;
    @FXML
    private Button modifyManagerID;
    @FXML
    private Button logoutButton;
    @FXML
    private Button backToMainMenuButton;
    private Map<String, Person> idMap = new HashMap<>();
    // endregion
    ArrayList<Person> lista = new ArrayList<>();


    @FXML
    private void initialize() throws IOException {
        addItemsToListOfPeople();
        addListenerToListView();
        addListenerToTextField();


    }

    private void addItemsToListOfPeople() throws IOException {
        UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
        var call = usersCRUD.getPeople(TokenManager.getAccessToken());
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                List<Person> personList = response.body();
                if (personList != null) {
                    // Create a new ObservableList to back the ListView
                    ObservableList<Person> observableList = FXCollections.observableArrayList();
                    observableList.addAll(personList);
                    // Set the items property of the ListView to the new ObservableList
                    listOfPeople.setItems(observableList);

                    for (var person: personList) {
                        idMap.put(person.id, person);
                    }

                }
                else {
                    System.out.println("The person list is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                System.out.println("The API call failed.");
            }



        });
    }
    private void addListenerToListView() {
        setModifiyButtonsAvailable(true);

        listOfPeople.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
                    @Override
                    public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
                        String selectedId = newValue.id;
                        // Get the corresponding Person object from the idMap using the ID as the key
                        Person selectedPerson = idMap.get(selectedId);
                        addPersonData(selectedPerson.id);
                    }

        });
    }

    private void addListenerToTextField() {

            ObservableList<Person> data = FXCollections.observableArrayList();

            FilteredList<Person> filteredData = new FilteredList<>(data, p -> true);

            searchByName.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    return person.toString().toLowerCase().contains(lowerCaseFilter);
                });
            });

            listOfPeople.setItems(filteredData);
    }
    //TODO: clear all labels before setting a new value
    private void addPersonData(String id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        var p = idMap.get(id);

        Label usernameLabel = new Label();
        usernameLabel.setText(idMap.get(id).username);
        Properties.add(usernameLabel,1,0);

        Label first_nameLabel = new Label();
        first_nameLabel.setText(idMap.get(id).firstName);
        Properties.add(first_nameLabel,1,2);

        Label last_nameLabel = new Label();
        last_nameLabel.setText(idMap.get(id).lastName);
        Properties.add(last_nameLabel,1,3);

        Label hourly_wageLabel = new Label();
        hourly_wageLabel.setText(String.format("%d",idMap.get(id).hourlyWage));
        Properties.add(hourly_wageLabel,1,4);

        Label reg_dateLabel = new Label();
        reg_dateLabel.setText(formatter.format(idMap.get(id).registrationDate));
        Properties.add(reg_dateLabel,1,5);

        if (p.hireDate != null) {
            Label hire_dateLabel = new Label();
            hire_dateLabel.setText(formatter.format(idMap.get(id).hireDate));
            Properties.add(hire_dateLabel, 1, 6);
        }

        Label roleLabel = new Label();
        roleLabel.setText(idMap.get(id).role);
        Properties.add(roleLabel,1,7);

        if (p.managerId != null) {
            Label managerIdLabel = new Label();
            managerIdLabel.setText(p.managerId);
            Properties.add(managerIdLabel, 1, 8);
        }

    }
    private void setModifiyButtonsAvailable(boolean bool) {
        modifyUsername.setDisable(!bool);
        modifyPassword.setDisable(!bool);
        modifyFirstName.setDisable(!bool);
        modifyLastName.setDisable(!bool);
        modifyHourlyWage.setDisable(!bool);
        modifyRole.setDisable(!bool);
        modifyManagerID.setDisable(!bool);
    }

    // region Modify buttons
    public void modifyUsername(ActionEvent actionEvent) {

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Modify");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        btn.setOnAction(actionEvent1 -> {
            String newText = tf.getText();
            //TODO: modify username with retrofit

           /* // Update the data with Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://example.com/api/")
                    .build();
            UsersCRUD userService = retrofit.create(UsersCRUD.class);
            User updatedUser = new Person(userId, newText); // create a new User object with the updated data
            Call<User> call = userService.updateItem(id, updatedUser);
            try {
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    // Update the data in the TableView or ListView with the updated User object
                    person.setName(newText);
                    // Close the window
                    ((Stage)btn.getScene().getWindow()).close();
                } else {
                    // Handle the error
                    System.out.println("Update failed: " + response.message());
                }
            } catch (IOException e) {
                // Handle the network error
                System.out.println("Network error: " + e.getMessage());
            }*/
        });
        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(vBox, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
    public void modifyPassword(ActionEvent actionEvent) {

    }
    public void modifyFirstName(ActionEvent actionEvent) {

    }
    public void modifyLastName(ActionEvent actionEvent) {

    }
    public void modifyHourlyWage(ActionEvent actionEvent) {

    }
    public void modifyRole(ActionEvent actionEvent) {

    }
    public void modifyManagerID(ActionEvent actionEvent) {

    }
    // endregion
    public void logoutFromModifyPerson(ActionEvent event) {
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

    public void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuView.fxml"));
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
