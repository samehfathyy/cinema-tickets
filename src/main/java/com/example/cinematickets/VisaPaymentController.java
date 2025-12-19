package com.example.cinematickets;

import com.example.cinematickets.models.PaymentContext;
import com.example.cinematickets.models.Visa;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VisaPaymentController implements PaymentPopupController{

    @FXML private TextField nameField;
    @FXML private TextField cardNumberField;
    @FXML private PasswordField cvvField;
    @FXML private TextField expiryField;

    private double amount;
    private Stage stage;
    private boolean success = false; // track payment success

    @Override
    public void setData(double amount, Stage stage) {
        this.amount = amount;
        this.stage = stage;
    }

    @FXML
    private void handlePay() {
        if (nameField.getText().isEmpty() ||
                cardNumberField.getText().isEmpty() ||
                cvvField.getText().isEmpty() ||
                expiryField.getText().isEmpty()) {

            showAlert("Please fill all fields");
            return;
        }

        Visa visa = new Visa(
                nameField.getText(),
                cardNumberField.getText(),
                cvvField.getText(),
                expiryField.getText()
        );

        PaymentContext context = new PaymentContext();
        context.setPaymentStrategy(visa);
        context.executePayment(amount);

        success = true;
        showAlert("Payment Successful!");
        stage.close(); // close popup
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
