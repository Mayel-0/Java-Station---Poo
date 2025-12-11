module com.example.java_station {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens com.example.java_station to javafx.fxml;
    exports com.example.java_station;
}