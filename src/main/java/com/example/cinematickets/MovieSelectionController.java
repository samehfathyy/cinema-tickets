package com.example.cinematickets;

import com.example.cinematickets.models.Movie;
import com.example.cinematickets.repos.CinemaRepository;
import com.example.cinematickets.repos.CinemaRepositoryProxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MovieSelectionController {

    @FXML
    private VBox mainContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane moviesGrid;

    private CinemaRepository repository;

    @FXML
    public void initialize() {
        repository = new CinemaRepositoryProxy();
        loadMovies();
    }

    private void loadMovies() {
        List<Movie> movies = repository.getMovies();
        moviesGrid.getChildren().clear();

        int row = 0;
        int col = 0;
        int maxCols = 3; // 3 movies per row

        for (Movie movie : movies) {
            VBox movieCard = createMovieCard(movie);
            moviesGrid.add(movieCard, col, row);

            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createMovieCard(Movie movie) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #BDC3C7; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setPrefWidth(280);
        card.setPrefHeight(200);

        // Movie title
        Label titleLabel = new Label(movie.getName());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2C3E50; -fx-wrap-text: true;");

        // Movie details
        Label genreLabel = new Label("Genre: " + movie.type.genre);
        genreLabel.setStyle("-fx-text-fill: #7F8C8D;");

        Label languageLabel = new Label("Language: " + movie.type.language);
        languageLabel.setStyle("-fx-text-fill: #7F8C8D;");

        // Rating
        Label ratingLabel = new Label("Rating: " + String.format("%.1f", movie.avgRate != null ? movie.avgRate : 0.0) + "/5.0");
        ratingLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #F39C12;");

        // Select button
        Button selectButton = new Button("Select Movie");
        selectButton.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-cursor: hand;");
        selectButton.setPrefWidth(200);
        selectButton.setOnAction(e -> selectMovie(movie));

        card.getChildren().addAll(titleLabel, genreLabel, languageLabel, ratingLabel, selectButton);
        return card;
    }

    private void selectMovie(Movie movie) {
        try {
            // Navigate to showtime selection for this movie
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinematickets/ShowtimeSelection.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            // Pass the selected movie to the controller
            ShowtimeSelectionController controller = loader.getController();
            controller.setSelectedMovie(movie);

            Stage stage = (Stage) mainContainer.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Select Showtime - " + movie.getName());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            // Show error alert
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load showtimes");
            alert.setContentText("An error occurred while loading the showtimes page: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
