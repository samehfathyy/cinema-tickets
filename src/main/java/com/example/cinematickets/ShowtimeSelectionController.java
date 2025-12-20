package com.example.cinematickets;

import com.example.cinematickets.models.*;
import com.example.cinematickets.repos.CinemaRepository;
import com.example.cinematickets.repos.CinemaRepositoryProxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ShowtimeSelectionController {

    @FXML
    private VBox mainContainer;

    @FXML
    private Label movieTitleLabel;

    @FXML
    private Label movieDetailsLabel;

    @FXML
    private GridPane showtimesGrid;

    @FXML
    private Button backButton;

    private CinemaRepository repository;
    private Movie selectedMovie;

    @FXML
    public void initialize() {
        repository = new CinemaRepositoryProxy();
        backButton.setOnAction(e -> goBackToMovies());
    }

    public void setSelectedMovie(Movie movie) {
        this.selectedMovie = movie;
        selectedMovieInfo();
        loadShowtimes();
    }

    private void selectedMovieInfo() {
        if (selectedMovie != null) {
            movieTitleLabel.setText(selectedMovie.getName());
            movieDetailsLabel.setText(String.format("Genre: %s | Language: %s | Rating: %.1f/5.0",
                    selectedMovie.type.genre,
                    selectedMovie.type.language,
                    selectedMovie.avgRate != null ? selectedMovie.avgRate : 0.0));
        }
    }

    private void loadShowtimes() {
        if (selectedMovie == null) return;

        showtimesGrid.getChildren().clear();

        // Get showtimes for this movie
        List<ShowTime> showtimes = repository.getShowTimes().stream()
                .filter(st -> st.getMovieId() == selectedMovie.id)
                .collect(Collectors.toList());

        int row = 0;
        int col = 0;
        int maxCols = 2; // 2 showtimes per row

        for (ShowTime showtime : showtimes) {
            VBox showtimeCard = createShowtimeCard(showtime);
            showtimesGrid.add(showtimeCard, col, row);

            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }

        // If no showtimes available
        if (showtimes.isEmpty()) {
            Label noShowtimesLabel = new Label("No showtimes available for this movie at the moment.");
            noShowtimesLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #7F8C8D; -fx-font-style: italic;");
            showtimesGrid.add(noShowtimesLabel, 0, 0);
        }
    }

    private VBox createShowtimeCard(ShowTime showtime) {
        VBox card = new VBox();
        card.setSpacing(12);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #BDC3C7; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setPrefWidth(350);
        card.setPrefHeight(200);

        // Date and Time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy 'at' HH:mm");
        Label dateTimeLabel = new Label(showtime.getDatetime().format(formatter));
        dateTimeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        // Hall info
        Hall hall = repository.getHallById(showtime.getHallId());
        String hallName = hall != null ? hall.getName() : "Unknown Hall";
        Label hallLabel = new Label(hallName);
        hallLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495E;");

        // Price
        Label priceLabel = new Label("$" + String.format("%.2f", showtime.getPrice()));
        priceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #27AE60;");

        // Availability
        int availableSeats = showtime.getAvailableSeatsNumber();
        int totalSeats = hall != null ? hall.getCapacity() : 0;
        Label availabilityLabel = new Label("Available seats: " + availableSeats + "/" + totalSeats);
        availabilityLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " +
                (availableSeats == 0 ? "#E74C3C" : "#27AE60") + "; -fx-font-weight: bold;");

        // Status indicator
        Label statusLabel = new Label();
        if (availableSeats == 0) {
            statusLabel.setText("❌ FULL");
            statusLabel.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 4;");
        } else if (availableSeats < totalSeats * 0.2) {
            statusLabel.setText("⚠️ LIMITED");
            statusLabel.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 4;");
        } else {
            statusLabel.setText("✅ AVAILABLE");
            statusLabel.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 4;");
        }

        // Select button
        Button selectButton = new Button("Select Showtime");
        selectButton.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand;");
        selectButton.setPrefWidth(200);
        selectButton.setDisable(availableSeats == 0); // Disable if full

        if (availableSeats == 0) {
            selectButton.setText("Sold Out");
            selectButton.setStyle("-fx-background-color: #BDC3C7; -fx-text-fill: #7F8C8D; -fx-font-weight: bold; -fx-padding: 10 20;");
        } else {
            selectButton.setOnAction(e -> selectShowtime(showtime));
        }

        // Layout the card
        HBox statusAndPriceBox = new HBox(10);
        statusAndPriceBox.getChildren().addAll(statusLabel, priceLabel);
        statusAndPriceBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        card.getChildren().addAll(dateTimeLabel, hallLabel, statusAndPriceBox, availabilityLabel, selectButton);
        return card;
    }

    private void selectShowtime(ShowTime showtime) {
        try {
            // Navigate to seat selection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/MovieBooking.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            // Pass the selected showtime to the controller
            MovieBookingController controller = loader.getController();
            controller.setSelectedShowtime(showtime);

            Stage stage = (Stage) mainContainer.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Select Seats - " + selectedMovie.getName());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            // Show error alert
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load seat selection");
            alert.setContentText("An error occurred while loading the seat selection page: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void goBackToMovies() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/movie-selection.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            Stage stage = (Stage) mainContainer.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Select Movie");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to go back");
            alert.setContentText("An error occurred while returning to movie selection: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
