module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;


    opens com.example.cinemaapp to javafx.fxml;
    exports com.example.cinemaapp;
}