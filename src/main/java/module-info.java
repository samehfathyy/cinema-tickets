module com.example.cinematickets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.cinematickets to javafx.fxml;
    opens com.example.cinematickets.models to javafx.fxml;
    exports com.example.cinematickets;
    exports com.example.cinematickets.repos;
    opens com.example.cinematickets.repos to javafx.fxml;
}