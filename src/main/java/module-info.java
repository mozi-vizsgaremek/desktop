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
}