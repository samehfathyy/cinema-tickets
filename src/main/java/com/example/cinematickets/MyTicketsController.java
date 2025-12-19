package com.example.cinematickets;

import com.example.cinematickets.models.Checkout;
import com.example.cinematickets.models.TicketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class MyTicketsController {

    @FXML private TableView<CheckoutTicket> ticketsTable;
    @FXML private TableColumn<CheckoutTicket, String> movieColumn;
    @FXML private TableColumn<CheckoutTicket, String> hallColumn;
    @FXML private TableColumn<CheckoutTicket, String> showtimeColumn;
    @FXML private TableColumn<CheckoutTicket, String> seatsColumn;
    @FXML private TableColumn<CheckoutTicket, Float> totalColumn;
    @FXML private TableColumn<CheckoutTicket, String> expiredColumn;

    private ObservableList<CheckoutTicket> ticketsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        movieColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCheckout().getMovieName()));
        hallColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCheckout().getHallName()));
        seatsColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCheckout().getSeatsNumbers().toString()));
        totalColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCheckout().getTotalAmount()));
        showtimeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getShowtime()));
        expiredColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().isExpired() ? "Yes" : "No"));

        ticketsList.addAll(TicketManager.getTickets());
        ticketsTable.setItems(ticketsList);


    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/movie-selection.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);
            Stage stage = (Stage) ticketsTable.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTickets(List<CheckoutTicket> tickets) {
        ticketsList.clear();
        ticketsList.addAll(tickets);
    }

    // Helper class to combine Checkout + showtime
    public static class CheckoutTicket {
        private Checkout checkout;
        private String showtime;

        public CheckoutTicket(Checkout checkout, String showtime) {
            this.checkout = checkout;
            this.showtime = showtime;
        }

        public Checkout getCheckout() { return checkout; }
        public String getShowtime() { return showtime; }

        public boolean isExpired() {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
                LocalDateTime showDateTime = LocalDateTime.parse(showtime, formatter);
                return showDateTime.isBefore(LocalDateTime.now());
            } catch (Exception e) {
                return false; // if parsing fails
            }
        }
    }
}
