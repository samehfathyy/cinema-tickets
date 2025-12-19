package com.example.cinematickets;

import com.example.cinematickets.models.Paypal;
import com.example.cinematickets.models.PaymentContext;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PaypalPaymentController implements PaymentPopupController {

    @FXML private TextField emailField;
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
        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert("Please fill all fields");
            return;
        }

        Paypal paypal = new Paypal(emailField.getText(), passwordField.getText());
        PaymentContext context = new PaymentContext();
        context.setPaymentStrategy(paypal);
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
