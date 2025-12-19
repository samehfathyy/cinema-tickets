package com.example.cinematickets;

import com.example.cinematickets.models.CartManager;
import com.example.cinematickets.models.TicketManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutController {

    @FXML private Label ticketsPrice;
    @FXML private Label totalPrice;
    @FXML private RadioButton instaRadio;
    @FXML private RadioButton visaRadio;
    @FXML private RadioButton paypalRadio;
    @FXML private Label confirmationMessage;

    @FXML private VBox movieDetailsContainer;

    private ToggleGroup paymentGroup;
    private Map<String, String> paymentFXMLMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Setup payment toggle group
        paymentGroup = new ToggleGroup();
        instaRadio.setToggleGroup(paymentGroup);
        visaRadio.setToggleGroup(paymentGroup);
        paypalRadio.setToggleGroup(paymentGroup);
        instaRadio.setSelected(true);

        // Map payment names to FXML paths
        paymentFXMLMap.put("Visa", "/com/example/cinematickets/VisaPayment.fxml");
        paymentFXMLMap.put("PayPal", "/com/example/cinematickets/PaypalPayment.fxml");
        paymentFXMLMap.put("InstaPay", "/com/example/cinematickets/InstaPayment.fxml");
    }

    /** Populate the checkout page with all tickets in the cart */
    public void setCartTickets(List<MyTicketsController.CheckoutTicket> tickets) {
        movieDetailsContainer.getChildren().clear();
        float total = 0;

        if (tickets.isEmpty()) return;

        // For each ticket, create a card showing movie info and selected seats
        for (MyTicketsController.CheckoutTicket t : tickets) {
            VBox movieCard = new VBox(5);
            movieCard.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 10;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

            Label movieName = new Label("🎬 Movie: " + t.getCheckout().getMovieName());
            Label hall = new Label("🏟 Hall: " + t.getCheckout().getHallName());
            Label showtime = new Label("🕒 Showtime: " + t.getShowtime());
            Label seats = new Label("💺 Seats: " + t.getCheckout().getSeatsNumbers().toString());

            movieCard.getChildren().addAll(movieName, hall, showtime, seats);
            movieDetailsContainer.getChildren().add(movieCard);

            total += t.getCheckout().getTotalAmount();
        }

        ticketsPrice.setText("Tickets: $" + total);
        totalPrice.setText("Total: $" + total);
    }

    @FXML
    private void handlePay() {
        List<MyTicketsController.CheckoutTicket> cart = CartManager.getCart();
        if (cart.isEmpty()) {
            confirmationMessage.setText("Cart is empty!");
            return;
        }

        RadioButton selected = (RadioButton) paymentGroup.getSelectedToggle();
        String method = selected.getText();
        String fxmlPath = paymentFXMLMap.get(method);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load(), 500, 300);
            popupStage.setScene(scene);

            // Use the interface directly, no reflection
            PaymentPopupController controller = loader.getController();
            controller.setData(CartManager.getTotalPrice(), popupStage);

            // Wait for user to complete payment
            popupStage.showAndWait();

            if (!controller.isSuccess()) {
                confirmationMessage.setText("Payment failed or cancelled!");
                return;
            }

            // Add all tickets to TicketManager and clear cart
            cart.forEach(TicketManager::addTicket);
            CartManager.clearCart();

            // Clear UI
            movieDetailsContainer.getChildren().clear();
            ticketsPrice.setText("$0");
            totalPrice.setText("$0");
            confirmationMessage.setText("Payment Successful for all items!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToMyTickets() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/MyTickets.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            MyTicketsController controller = loader.getController();
            controller.setTickets(TicketManager.getTickets());

            Stage stage = (Stage) movieDetailsContainer.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
