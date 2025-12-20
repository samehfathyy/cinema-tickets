package com.example.cinematickets;

import com.example.cinematickets.models.CartManager;
import com.example.cinematickets.models.Checkout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public class CartController {

    @FXML private ListView<String> cartList;
    @FXML private Label totalPriceLabel;
    @FXML private Button checkoutButton;

    @FXML
    public void initialize() {
        updateCartView();
    }

    public void updateCartView() {
        cartList.getItems().clear();
        for (MyTicketsController.CheckoutTicket t : CartManager.getCart()) {
            cartList.getItems().add(
                    t.getCheckout().getMovieName() +
                            " | " + t.getCheckout().getHallName() +
                            " | " + t.getShowtime() +
                            " | $" + t.getCheckout().getTotalAmount()
            );
        }
        totalPriceLabel.setText("Total: $" + CartManager.getTotalPrice());
    }

    @FXML
    private void goToCheckout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/Checkout.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            CheckoutController controller = loader.getController();
            // Pass full cart to checkout
            controller.setCartTickets(CartManager.getCart());

            Stage stage = (Stage) checkoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
