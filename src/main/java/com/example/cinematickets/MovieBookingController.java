package com.example.cinematickets;

import com.example.cinematickets.models.CartManager;
import com.example.cinematickets.models.Checkout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

public class MovieBookingController {

    @FXML
    private Button checkoutButton;

    @FXML
    private void goToCart() {
        try {
            // Add ticket to cart
            Checkout checkout = new Checkout(
                    "Star Wars",
                    "Hall 1",
                    2,
                    List.of(4, 3),
                    100f
            );
            String showtime = "2025-10-20 20:00";
            CartManager.addToCart(new MyTicketsController.CheckoutTicket(checkout, showtime));

            // Load Cart.fxml
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/Cart.fxml")
            );
            Scene scene = new Scene(loader.load(), 1000, 800);

            // Controller automatically reads from CartManager
            // CartController controller = loader.getController(); // no need to call setCart()

            Stage stage = (Stage) checkoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
