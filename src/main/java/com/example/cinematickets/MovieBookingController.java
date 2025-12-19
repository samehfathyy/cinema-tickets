package com.example.cinematickets;

import com.example.cinematickets.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class MovieBookingController {

    // this is the function you need to call to pass the ticket details to the checkout page
    // just change the checkout object parameters to be dynamic

    @FXML
    private Button checkoutButton;
    @FXML
    private void goToCheckout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/Checkout.fxml")
            );
            Scene scene = new Scene(loader.load(), 1000, 800);

            // IMPORTANT: get controller AFTER load()
            CheckoutController controller = loader.getController();

            // Create Checkout object
            Checkout checkout = new Checkout(
                    "Star Wars",
                    "Hall 1",
                    2,
                    List.of(4, 3),
                    100f
            );
            String showtime = "2025-10-20 20:00";

            // Pass data (this calls setCheckout method)  also pass the chosen showtime
            controller.setCheckout(checkout,showtime);

            Stage stage = (Stage) checkoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
