package com.example.cinematickets;

import com.example.cinematickets.models.InstaPay;
import com.example.cinematickets.models.PaymentContext;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InstaPaymentController implements PaymentPopupController{

    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;

    private double amount;
    private Stage stage;
    private boolean success = false;

    @Override
    public void setData(double amount, Stage stage) {
        this.amount = amount;
        this.stage = stage;
    }

    @FXML
    private void handlePay() {
        if (phoneField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert("Please fill all fields");
            return;
        }

        InstaPay insta = new InstaPay(phoneField.getText(), passwordField.getText());
        PaymentContext context = new PaymentContext();
        context.setPaymentStrategy(insta);
        context.executePayment(amount);

        success = true;
        showAlert("Payment Successful!");
        stage.close();
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
