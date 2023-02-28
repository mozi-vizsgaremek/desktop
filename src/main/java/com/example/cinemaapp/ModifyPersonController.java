package com.example.cinemaapp;

import com.example.cinemaapp.rest.auth.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.sun.tools.javac.util.Constants.format;
import static jdk.internal.icu.text.UTF16.valueOf;

public class ModifyPersonController {

    // region Declarations
    @FXML
    private GridPane Properties;
    @FXML
    private ListView<String> listOfPeople;
    @FXML
    private TextField searchByName;
    @FXML
    private Button modifyID;
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
    private Button modifyRegistrationDate;
    @FXML
    private Button modifyHiringDate;
    @FXML
    private Button modifyRole;
    @FXML
    private Button modifyManagerID;



    // endregion

    Person newPerson = new Person(0,"asd","asd",
            "asd","asd",0, LocalDate.now(),
            LocalDate.now(),"asd",0);

    @FXML
    private void initialize() {
        listOfPeople.getItems().add(newPerson.toString());
        listOfPeople.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                setModifiyButtonsAvailable(true);
                addPersonData();
            }


        });
    }
    private void addPersonData() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        Label usernameLabel = new Label();
        usernameLabel.setText(newPerson.getUsername());
        Properties.add(usernameLabel,1,0);

        Label password = new Label();
        password.setText("*********");
        Properties.add(password,1,1);

        Label first_nameLabel = new Label();
        first_nameLabel.setText(newPerson.getFirst_name());
        Properties.add(first_nameLabel,1,2);

        Label last_nameLabel = new Label();
        last_nameLabel.setText(newPerson.getLast_name());
        Properties.add(last_nameLabel,1,3);

        Label hourly_wageLabel = new Label();
        hourly_wageLabel.setText(String.format("%d",newPerson.getHourly_wage()));
        Properties.add(hourly_wageLabel,1,4);

        Label reg_dateLabel = new Label();
        reg_dateLabel.setText(formatter.format(newPerson.getReg_date()));
        Properties.add(reg_dateLabel,1,5);

        Label hire_dateLabel = new Label();
        hire_dateLabel.setText(formatter.format(newPerson.getHire_date()));
        Properties.add(hire_dateLabel,1,6);

        Label roleLabel = new Label();
        roleLabel.setText(newPerson.getRole());
        Properties.add(roleLabel,1,7);

        Label managerIdLabel = new Label();
        managerIdLabel.setText(String.format("%d",newPerson.getManager_id()));
        Properties.add(managerIdLabel,1,8);


    }
    private void setModifiyButtonsAvailable(boolean bool) {
        modifyUsername.setDisable(!bool);
        modifyPassword.setDisable(!bool);
        modifyFirstName.setDisable(!bool);
        modifyLastName.setDisable(!bool);
        modifyHourlyWage.setDisable(!bool);
        modifyRegistrationDate.setDisable(!bool);
        modifyHiringDate.setDisable(!bool);
        modifyRole.setDisable(!bool);
        modifyManagerID.setDisable(!bool);
    }

    public void searchByNameChanged(InputMethodEvent inputMethodEvent) {
        /* String text = searchByName.getText().trim();
        if (listOfPeople.getItems().contains(text.trim())) {
            listOfPeople.getItems().clear();
            listOfPeople.getItems().add(text);
        } */

        //TODO: Search in the list by names
    }




    // region Modify buttons
    public void modifyUsername(ActionEvent actionEvent) {

        VBox vBox = new VBox();
        TextField tf = new TextField();
        Button btn = new Button();
        btn.setText("Módosít");
        vBox.getChildren().add(tf);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        btn.setOnAction(actionEvent1 -> {
            String text = tf.getText();

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

    public void modifyRegistrationDate(ActionEvent actionEvent) {

    }

    public void modifyHiringDate(ActionEvent actionEvent) {

    }

    public void modifyRole(ActionEvent actionEvent) {

    }

    public void modifyManagerID(ActionEvent actionEvent) {

    }

    // endregion



}
