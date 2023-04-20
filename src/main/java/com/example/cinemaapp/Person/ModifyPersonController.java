package com.example.cinemaapp.Person;

import com.example.cinemaapp.Login.LoginController;
import com.example.cinemaapp.Menu.MainMenuController;
import com.example.cinemaapp.rest.RetrofitSingleton;
import com.example.cinemaapp.rest.auth.TokenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class ModifyPersonController {
    // region Declarations
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
    public Label reg_dateLabel;
    public Label passwordLabel;
    public Label usernameLabel;
    public Label first_nameLabel;
    public Label last_nameLabel;
    public Label hourly_wageLabel;
    public Label hire_dateLabel;
    public Label roleLabel;
    public Label managerIdLabel;
    private final Map<String, Person> idMap = new HashMap<>();
    // endregion
    @FXML
    private void initialize() throws IOException {
        addItemsToListOfPeople();
        addListenerToListView();
    }
    private void addItemsToListOfPeople() throws IOException {
        UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
        var call = usersCRUD.getPeople(TokenManager.getAccessToken());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                List<Person> personList = response.body();
                if (personList != null) {
                    ObservableList<Person> observableList = FXCollections.observableArrayList();
                    observableList.addAll(personList);
                    listOfPeople.setItems(observableList);
                    for (var person : personList) {
                        idMap.put(person.id, person);
                    }
                    addListenerToTextField();
                } else {
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

        listOfPeople.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedId = newValue.id;
                Person selectedPerson = idMap.get(selectedId);
                clearPersonData();
                addPersonData(selectedPerson);
                setModifiyButtonsAvailable();
            }

        });
    }
    private void addListenerToTextField() {
            var data = FXCollections.observableArrayList(listOfPeople.getItems());
            FilteredList<Person> filteredData = new FilteredList<>(data, p -> true);
            searchByName.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return person.toString().toLowerCase().contains(lowerCaseFilter);
                });
                listOfPeople.setItems(filteredData);
            });
    }
    private void addPersonData(Person person) {
        if (person != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

            usernameLabel.setText(person.username);
            first_nameLabel.setText(person.firstName);
            last_nameLabel.setText(person.lastName);
            hourly_wageLabel.setText(String.format("%d",person.hourlyWage));
            reg_dateLabel.setText(formatter.format(person.registrationDate));
            if (person.hireDate != null) {
                hire_dateLabel.setText(formatter.format(person.hireDate));
            }
            roleLabel.setText(person.role);
            if (person.managerId != null) {
                managerIdLabel.setText(person.managerId);
            }
        }

    }
    private void clearPersonData() {
        usernameLabel.setText("");
        first_nameLabel.setText("");
        last_nameLabel.setText("");
        hourly_wageLabel.setText("");
        reg_dateLabel.setText("");
        hire_dateLabel.setText("");
        roleLabel.setText("");
        managerIdLabel.setText("");
    }
    private void setModifiyButtonsAvailable() {
        modifyUsername.setDisable(false);
        modifyPassword.setDisable(false);
        modifyFirstName.setDisable(false);
        modifyLastName.setDisable(false);
        modifyHourlyWage.setDisable(false);
        modifyRole.setDisable(false);
        modifyManagerID.setDisable(false);
    }

    // region Modify buttons

    private void modifyHelper(Person selectedPerson, Stage stage, UsersCRUD usersCRUD) {
        selectedPerson.managerId = null; //I have to do this otherwise it will crash
        Call<Person> call;
        try {
            call = usersCRUD.updatePerson(TokenManager.getAccessToken(), selectedPerson.id, selectedPerson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                System.out.println("The API call failed.");
            }
        });
        stage.close();
        listOfPeople.refresh();
        addPersonData(selectedPerson);
    }
    public void modifyUsername(){

        Person selectedPerson = listOfPeople.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Modify");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(vBox, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        btn.setOnAction(actionEvent1 -> {
            selectedPerson.username = tf.getText();
            vBox.getChildren().remove(btn);
            vBox.getChildren().remove(tf);
            Label label = new Label();
            label.setText("Modification was successful");
            vBox.getChildren().add(label);
            stage.setOnCloseRequest(event -> {
                UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
                selectedPerson.username = tf.getText();
                modifyHelper(selectedPerson, stage, usersCRUD);
            });
        });
    }
    public void modifyPassword() {

        Person selectedPerson = listOfPeople.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Modify");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(vBox, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        btn.setOnAction(actionEvent1 -> {
            selectedPerson.password = tf.getText();
            vBox.getChildren().remove(btn);
            vBox.getChildren().remove(tf);
            Label label = new Label();
            label.setText("Modification was successful");
            vBox.getChildren().add(label);
            stage.setOnCloseRequest(event -> {
                UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
                selectedPerson.password = tf.getText();
                modifyHelper(selectedPerson, stage, usersCRUD);
            });
        });
    }
    public void modifyFirstName() {
        Person selectedPerson = listOfPeople.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Modify");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(vBox, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        btn.setOnAction(actionEvent1 -> {
            selectedPerson.firstName = tf.getText();
            vBox.getChildren().remove(btn);
            vBox.getChildren().remove(tf);
            Label label = new Label();
            label.setText("Modification was successful");
            vBox.getChildren().add(label);
            stage.setOnCloseRequest(event -> {
                UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
                selectedPerson.firstName = tf.getText();
                modifyHelper(selectedPerson, stage, usersCRUD);
            });
        });
    }
    public void modifyLastName() {
        Person selectedPerson = listOfPeople.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Modify");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(vBox, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        btn.setOnAction(actionEvent1 -> {
            selectedPerson.lastName = tf.getText();
            vBox.getChildren().remove(btn);
            vBox.getChildren().remove(tf);
            Label label = new Label();
            label.setText("Modification was successful");
            vBox.getChildren().add(label);
            stage.setOnCloseRequest(event -> {
                UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
                selectedPerson.lastName = tf.getText();
                modifyHelper(selectedPerson, stage, usersCRUD);
            });
        });
    }
    public void modifyHourlyWage() {
        Person selectedPerson = listOfPeople.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Modify");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(vBox, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        btn.setOnAction(actionEvent1 -> {
            try {
                selectedPerson.hourlyWage = Integer.parseInt(tf.getText());
                vBox.getChildren().remove(btn);
                vBox.getChildren().remove(tf);
                Label label = new Label();
                label.setText("Modification was successful");
                vBox.getChildren().add(label);
                stage.setOnCloseRequest(event -> {
                    UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
                    selectedPerson.hourlyWage = Integer.parseInt(tf.getText());
                    modifyHelper(selectedPerson, stage, usersCRUD);
                });
            }
            catch (NumberFormatException e) {
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Button button = new Button();
                button.setText("OK");
                button.setOnAction(actionEvent2 -> dialogStage.close());
                VBox vbox = new VBox(new Text("This field only accepts numbers!"), button);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(15));
                dialogStage.setScene(new Scene(vbox));
                dialogStage.show();
            }
        });
    }
    public void modifyRole() {
        Person selectedPerson = listOfPeople.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Modify");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(vBox, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        btn.setOnAction(actionEvent1 -> {
            selectedPerson.role = tf.getText();
            vBox.getChildren().remove(btn);
            vBox.getChildren().remove(tf);
            Label label = new Label();
            label.setText("Modification was successful");
            vBox.getChildren().add(label);
            stage.setOnCloseRequest(event -> {
                UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
                selectedPerson.role = tf.getText();
                modifyHelper(selectedPerson, stage, usersCRUD);
            });
        });
    }
    public void modifyManagerID() {
        Person selectedPerson = listOfPeople.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Modify");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(vBox, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        btn.setOnAction(actionEvent1 -> {
                selectedPerson.managerId = tf.getText();
                vBox.getChildren().remove(btn);
                vBox.getChildren().remove(tf);
                Label label = new Label();
                label.setText("Modification was successful");
                vBox.getChildren().add(label);
                stage.setOnCloseRequest(event -> {
                    UsersCRUD usersCRUD = RetrofitSingleton.getInstance().create(UsersCRUD.class);
                    modifyHelper(selectedPerson, stage, usersCRUD);
                    selectedPerson.managerId = tf.getText();
                });
        });
    }
    // endregion
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
