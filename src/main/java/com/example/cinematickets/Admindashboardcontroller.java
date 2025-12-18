package com.example.cinematickets;

import com.example.cinematickets.repos.AuthRepository;
import com.example.cinematickets.repos.MainRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Admindashboardcontroller {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button manageShowTimesBtn;

    @FXML
    private Button manageMoviesBtn;

    @FXML
    private Button manageHallsBtn;

    @FXML
    private Button logoutBtn;

    private AuthRepository auth = AuthRepository.getInstance();
    private MainRepository repo = MainRepository.getInstance();

    @FXML
    public void initialize() {
        // Display welcome message
        String username = auth.getCurrentUser() != null ?
                auth.getCurrentUser().getUsername() : "Admin";
        welcomeLabel.setText("Welcome, " + username + "!");

        // Set button actions
        manageShowTimesBtn.setOnAction(e -> openShowTimeManagement());
        manageMoviesBtn.setOnAction(e -> openMovieManagement());
        manageHallsBtn.setOnAction(e -> openHallManagement());
        logoutBtn.setOnAction(e -> logout());
    }

    private void openShowTimeManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/admin-showtime-view.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) manageShowTimesBtn.getScene().getWindow();
            stage.setTitle("Manage ShowTimes");
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading ShowTime management: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void openMovieManagement() {
        // TODO: Implement movie management screen
        showAlert("Movie management coming soon!", Alert.AlertType.INFORMATION);
    }

    private void openHallManagement() {
        // TODO: Implement hall management screen
        showAlert("Hall management coming soon!", Alert.AlertType.INFORMATION);
    }

    private void logout() {
        auth.logout();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/login.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.show();
    }
}
