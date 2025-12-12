package com.example.cinematickets;

import com.example.cinematickets.repos.AuthRepository;
import javafx.fxml.FXML;
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
            showAlert("Invalid username or password!");
            return;
        }

        showAlert("Logged in successfully!");
        // TODO: go to main screen (User dashboard)
    }

    private void openSignUp() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/signup.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}
