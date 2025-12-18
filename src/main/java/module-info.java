module com.example.testjavastation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testjavastation to javafx.fxml;
    exports com.example.testjavastation;
}