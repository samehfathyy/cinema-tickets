package com.example.cinematickets;

import com.example.cinematickets.models.User;
import com.example.cinematickets.repos.AuthRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private Button signUpBtn;

    @FXML
    private Button loginBtn;   // مهم جداً لأنه كان عامل مشكلة

    private AuthRepository auth = AuthRepository.getInstance();

    @FXML
    private void initialize() {
        signUpBtn.setOnAction(this::register);
        loginBtn.setOnAction(this::goToLogin);
    }

    private void register(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();


        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please fill all fields!");
            return;
        }

        User newUser = new User(username, password);

        boolean success = auth.registerNewUser(newUser);

        if (!success) {
            showAlert("Username already exists!");
            return;
        }

        showAlert("Account created successfully!");
        goToLogin(event);
    }

    private void goToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(msg);
        alert.show();
    }
}
