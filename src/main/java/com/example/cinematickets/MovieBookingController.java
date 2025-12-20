package com.example.cinematickets;

import com.example.cinematickets.models.*;
import com.example.cinematickets.repos.CinemaRepository;
import com.example.cinematickets.repos.CinemaRepositoryProxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MovieBookingController {

    @FXML
    private Button checkoutButton;

    @FXML
    private GridPane seatGrid;

    @FXML
    private Label movieInfoLabel;

    @FXML
    private Label hallInfoLabel;

    @FXML
    private Label showtimeInfoLabel;

    @FXML
    private Label selectedSeatsLabel;

    @FXML
    private Label availableSeatsLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button backButton;

    private CinemaRepository repository;
    private ShowTime selectedShowTime;
    private List<Integer> selectedSeatNumbers;
    private List<Button> seatButtons;

    @FXML
    public void initialize() {
        repository = new CinemaRepositoryProxy();
        selectedSeatNumbers = new ArrayList<>();
        seatButtons = new ArrayList<>();

        // Initially disable checkout and hide seat grid
        checkoutButton.setDisable(true);
        seatGrid.setVisible(false);

        backButton.setOnAction(e -> goBackToShowtimes());
    }

    public void setSelectedShowtime(ShowTime showtime) {
        this.selectedShowTime = showtime;
        // Clear any previous selections when entering a new showtime
        selectedSeatNumbers.clear();
        CartManager.clearCart();
        displaySeats();
        updateInfoLabels();
        seatGrid.setVisible(true);
    }

    private void displaySeats() {
        if (selectedShowTime == null) return;

        seatGrid.getChildren().clear();
        seatButtons.clear();

        List<Seat> seats = selectedShowTime.getSeats();
        Hall hall = repository.getHallById(selectedShowTime.getHallId());
        int capacity = hall != null ? hall.getCapacity() : seats.size();

        // Create a grid layout for seats (assuming 10 seats per row)
        int seatsPerRow = 10;
        int row = 0;
        int col = 0;

        for (Seat seat : seats) {
            Button seatButton = createSeatButton(seat);
            seatGrid.add(seatButton, col, row);
            seatButtons.add(seatButton);

            col++;
            if (col >= seatsPerRow) {
                col = 0;
                row++;
            }
        }

        updateSelectedSeatsLabel();
        updateAvailableSeatsLabel();
    }

    private Button createSeatButton(Seat seat) {
        Button button = new Button(String.valueOf(seat.getSeatNumber()));
        button.setPrefSize(40, 40);
        button.setStyle(getSeatButtonStyle(seat));

        button.setOnAction(event -> toggleSeatSelection(seat, button));

        return button;
    }

    private String getSeatButtonStyle(Seat seat) {
        if (seat.isBooked()) {
            return "-fx-background-color: #FF4444; -fx-text-fill: white; -fx-font-weight: bold;";
        } else if (selectedSeatNumbers.contains(seat.getSeatNumber())) {
            return "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;";
        } else {
            return "-fx-background-color: #CCCCCC; -fx-text-fill: black;";
        }
    }

    private void toggleSeatSelection(Seat seat, Button button) {
        if (seat.isBooked()) {
            return; // Can't select booked seats
        }

        int seatNumber = seat.getSeatNumber();
        if (selectedSeatNumbers.contains(seatNumber)) {
            selectedSeatNumbers.remove(Integer.valueOf(seatNumber));
        } else {
            selectedSeatNumbers.add(seatNumber);
        }

        updateSeatButtonStyles();
        updateSelectedSeatsLabel();
        checkoutButton.setDisable(selectedSeatNumbers.isEmpty());
    }

    private void updateSeatButtonStyles() {
        for (Button button : seatButtons) {
            int seatNumber = Integer.parseInt(button.getText());
            Seat seat = selectedShowTime.getSeats().stream()
                    .filter(s -> s.getSeatNumber() == seatNumber)
                    .findFirst().orElse(null);

            if (seat != null) {
                button.setStyle(getSeatButtonStyle(seat));
            }
        }
    }

    private void updateInfoLabels() {
        if (selectedShowTime == null) return;

        Movie movie = repository.getMovieById(selectedShowTime.getMovieId());
        Hall hall = repository.getHallById(selectedShowTime.getHallId());

        movieInfoLabel.setText("Movie: " + (movie != null ? movie.getName() : "Unknown"));
        hallInfoLabel.setText("Hall: " + (hall != null ? hall.getName() : "Unknown"));
        showtimeInfoLabel.setText("Showtime: " + selectedShowTime.getDatetime().toString());
    }

    private void updateSelectedSeatsLabel() {
        if (selectedSeatNumbers.isEmpty()) {
            selectedSeatsLabel.setText("No seats selected");
        } else {
            selectedSeatsLabel.setText("Selected seats: " + selectedSeatNumbers.toString());
        }
        updateTotalPriceLabel();
    }

    private void updateAvailableSeatsLabel() {
        if (selectedShowTime == null) return;

        int available = selectedShowTime.getAvailableSeatsNumber();
        availableSeatsLabel.setText("Available seats: " + available);

        if (available == 0) {
            availableSeatsLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else {
            availableSeatsLabel.setStyle("-fx-text-fill: green;");
        }
    }

    private void updateTotalPriceLabel() {
        if (selectedShowTime == null || selectedSeatNumbers.isEmpty()) {
            totalPriceLabel.setText("Total: $0.00");
        } else {
            float pricePerSeat = selectedShowTime.getPrice();
            float totalPrice = selectedSeatNumbers.size() * pricePerSeat;
            totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));
        }
    }

    private void goBackToShowtimes() {
        try {
            // Clear any pending checkout and selected seats when going back
            CartManager.clearCart();
            selectedSeatNumbers.clear();
            updateSeatButtonStyles();
            updateSelectedSeatsLabel();
            updateTotalPriceLabel();
            checkoutButton.setDisable(true);

            Movie movie = repository.getMovieById(selectedShowTime.getMovieId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/ShowtimeSelection.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            ShowtimeSelectionController controller = loader.getController();
            controller.setSelectedMovie(movie);

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Select Showtime - " + movie.getName());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToCheckout() {
        if (selectedShowTime == null || selectedSeatNumbers.isEmpty()) {
            return;
        }

        try {
            // Create checkout data without booking seats yet
            String movieName = repository.getMovieById(selectedShowTime.getMovieId()).getName();
            String hallName = repository.getHallById(selectedShowTime.getHallId()).getName();
            float totalAmount = selectedSeatNumbers.size() * selectedShowTime.getPrice();
            String showtime = selectedShowTime.getDatetime().toString();

            Checkout checkout = new Checkout(movieName, hallName, selectedSeatNumbers.size(),
                                           new ArrayList<>(selectedSeatNumbers), totalAmount);

            // Add to cart for checkout display
            CartManager.addToCart(new MyTicketsController.CheckoutTicket(checkout, showtime));

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/Checkout.fxml")
            );
            Scene scene = new Scene(loader.load(), 1000, 800);

            // Pass booking data to controller for later booking
            CheckoutController controller = loader.getController();
            controller.setPendingBooking(selectedShowTime.getId(), selectedSeatNumbers);


            Stage stage = (Stage) checkoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToCart() {
        try {
            
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
