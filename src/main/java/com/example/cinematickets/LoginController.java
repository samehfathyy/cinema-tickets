package com.example.cinematickets;

import com.example.cinematickets.repos.AuthRepository;
import com.example.cinematickets.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    public Button signupBtn;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button goToSignUpBtn;

    private AuthRepository auth = AuthRepository.getInstance();

    @FXML
    public void initialize() {
        loginBtn.setOnAction(e -> login());
        goToSignUpBtn.setOnAction(e -> openSignUp());
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean success = auth.login(username, password);

        if (!success) {
            showAlert("Invalid username or password!",Alert.AlertType.ERROR);
            return;
        }

        User currentUser = auth.getCurrentUser();

        showAlert("Logged in successfully!", Alert.AlertType.INFORMATION);
        // Check if user is admin and navigate accordingly
        if (currentUser.isAdmin()) {
            openAdminDashboard();
        } else {
            // TODO: go to main screen (User dashboard)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/MovieBooking.fxml"));
                Scene checkoutScene = new Scene(loader.load(), 1000, 800);

                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.setScene(checkoutScene);
                stage.setTitle("Checkout Page");
                stage.show();
            }catch (Exception e) {
                e.printStackTrace();
                showAlert("Error loading movies page: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    private void openAdminDashboard() { //navigating to admin dashboard
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/admin-dashboard.fxml")
            );
            Scene scene = new Scene(loader.load(), 1000, 800);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setTitle("Admin Dashboard");
            stage.setScene(scene);
            stage.setFullScreen(true); // Make it fullscreen for better admin experience
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading admin dashboard: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void openSignUp() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/signup.fxml")
            );
            Scene scene = new Scene(loader.load(), 1000, 800);
            Stage stage = (Stage) usernameField.getScene().getWindow();
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
