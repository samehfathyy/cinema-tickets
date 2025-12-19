package com.example.cinematickets;

import javafx.stage.Stage;

public interface PaymentPopupController {
    void setData(double amount, Stage stage);
    boolean isSuccess();
}
