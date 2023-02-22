package com.example.cinemaapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

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

        Label label1 = new Label();
        label1.setText(String.format("%d",newPerson.getId()));
        Properties.add(label1,1,0);
        Label label2 = new Label();
        label2.setText(newPerson.getUsername());
        Properties.add(label2,1,1);
        Label label3 = new Label();
        label3.setText(newPerson.getPassword());
        Properties.add(label3,1,2);
        Label label4 = new Label();
        label4.setText(newPerson.getFirst_name());
        Properties.add(label4,1,3);
        Label label5 = new Label();
        label5.setText(newPerson.getLast_name());
        Properties.add(label5,1,4);
        Label label6 = new Label();
        label6.setText(String.format("%d",newPerson.getHourly_wage()));
        Properties.add(label6,1,5);
        Label label7 = new Label();
        label7.setText(formatter.format(newPerson.getReg_date()));
        Properties.add(label7,1,6);
        Label label8 = new Label();
        label8.setText(formatter.format(newPerson.getHire_date()));
        Properties.add(label8,1,7);
        Label label9 = new Label();
        label9.setText(newPerson.getRole());
        Properties.add(label9,1,8);
        Label label10 = new Label();
        label10.setText(String.format("%d",newPerson.getManager_id()));
        Properties.add(label10,1,9);


    }
    private void setModifiyButtonsAvailable(boolean bool) {
        modifyID.setDisable(!bool);
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
        String text = searchByName.getText().trim();
    }




    // region Modify buttons

    public void modifyID(ActionEvent actionEvent) {
        StackPane stackPane = new StackPane();
        TextField tf = new TextField();
        Button btn = new Button();
        stackPane.getChildren().add(tf);
        stackPane.getChildren().add(btn);
        btn.setOnAction(actionEvent1 -> {
            String text = tf.getText();

        });


        Stage stage = new Stage();
        stage.setTitle("Modifying...");
        stage.setScene(new Scene(stackPane, 230, 100));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();

    }
    public void modifyUsername(ActionEvent actionEvent) {
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
