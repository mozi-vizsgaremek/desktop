module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires com.google.gson;
    requires org.apache.commons.lang3;

    opens com.example.cinemaapp to javafx.fxml;
    exports com.example.cinemaapp;
    exports com.example.cinemaapp.rest;
    opens com.example.cinemaapp.rest to javafx.fxml;
    exports com.example.cinemaapp.rest.auth;
    opens com.example.cinemaapp.rest.auth to javafx.fxml;
    exports com.example.cinemaapp.Login;
    opens com.example.cinemaapp.Login to javafx.fxml;
    exports com.example.cinemaapp.Menu;
    opens com.example.cinemaapp.Menu to javafx.fxml;
    exports com.example.cinemaapp.Person;
    opens com.example.cinemaapp.Person to javafx.fxml;
    exports com.example.cinemaapp.Movie;
    opens com.example.cinemaapp.Movie to javafx.fxml;
    exports com.example.cinemaapp.Auditorium;
    opens com.example.cinemaapp.Auditorium to javafx.fxml;
}