package com.example.cinematickets;

import com.example.cinematickets.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class CheckoutController {

    @FXML private ListView<String> seatsList;
    @FXML private Label ticketsPrice;
    @FXML private Label totalPrice;
    @FXML private RadioButton cashRadio;
    @FXML private RadioButton visaRadio;
    @FXML private RadioButton paypalRadio;
    @FXML private Label confirmationMessage;
    @FXML private Label movieNameLabel;
    @FXML private Label hallNameLabel;
    @FXML private Label seatsCountLabel;
    @FXML private Label showtimeLabel;

    private ToggleGroup paymentGroup;
    private Checkout checkout;
    private String showtime;

    @FXML
    public void initialize() {
        paymentGroup = new ToggleGroup();
        cashRadio.setToggleGroup(paymentGroup);
        visaRadio.setToggleGroup(paymentGroup);
        paypalRadio.setToggleGroup(paymentGroup);
        cashRadio.setSelected(true);
    }

    // Called by previous screen
    public void setCheckout(Checkout checkout, String showtime) {
        this.checkout = checkout;
        this.showtime = showtime;

        movieNameLabel.setText(checkout.getMovieName());
        movieNameLabel.setStyle( "-fx-font-weight: bold; -fx-text-fill: #2c3e50");
        hallNameLabel.setText(checkout.getHallName());
        seatsCountLabel.setText("Seats: " + checkout.getNumberOfSeats());

        showtimeLabel.setText(showtime);
        showtimeLabel.setStyle("-fx-font-size: 16px;");

        totalPrice.setText("$" + checkout.getTotalAmount());
        ticketsPrice.setText("$" + checkout.getTotalAmount());

        seatsList.getItems().clear();
        for (Integer seat : checkout.getSeatsNumbers()) {
            seatsList.getItems().add("Seat " + seat);
        }
    }

    @FXML
    private void handlePay() {
        PaymentStrategy paymentStrategy;
        PaymentContext payment = new PaymentContext();

        RadioButton selected = (RadioButton) paymentGroup.getSelectedToggle();
        String method = selected.getText();

        switch (method) {
            case "Visa": paymentStrategy = new Visa(); break;
            case "PayPal": paymentStrategy = new Paypal(); break;
            default: paymentStrategy = new Cash();
        }

        payment.setPaymentStrategy(paymentStrategy);
        String result = payment.executePayment(checkout.getTotalAmount());

        confirmationMessage.setText("Payment Successful! " + result);

        TicketManager.addTicket(new MyTicketsController.CheckoutTicket(
                checkout,
                showtimeLabel.getText()
        ));
    }

    @FXML
    private void goToMyTickets() {
        try {
            // Load MyTickets FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/MyTickets.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            // pass tickets dynamically
            MyTicketsController controller = loader.getController();
            controller.setTickets(TicketManager.getTickets());


            // Show the new scene
            Stage stage = (Stage) movieNameLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
