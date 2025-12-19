module com.example.cinematickets {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cinematickets to javafx.fxml;
    opens com.example.cinematickets.models to javafx.fxml;
    exports com.example.cinematickets;
}