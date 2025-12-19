package com.example.cinematickets;

import com.example.cinematickets.models.Movie;
import com.example.cinematickets.repos.MainRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdminManageMoviesController {

    @FXML private TextField nameField;
    @FXML private TextField languageField;
    @FXML private TextField genreField;
    @FXML private Button backBtn;
    @FXML private TableView<Movie> moviesTable;
    @FXML private TableColumn<Movie, Integer> idCol;
    @FXML private TableColumn<Movie, String> nameCol;
    @FXML private TableColumn<Movie, String> languageCol;
    @FXML private TableColumn<Movie, String> genreCol;

    private final MainRepository repo = MainRepository.getInstance();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().id).asObject());
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        languageCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().type.language));
        genreCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().type.genre));
        backBtn.setOnAction(e -> goBackToDashboard());

        refreshTable();
    }

    @FXML
    private void addMovie() {
        repo.addMovie(
                nameField.getText(),
                languageField.getText(),
                genreField.getText()
        );
        refreshTable();
    }

    @FXML
    private void deleteMovie() {
        Movie selected = moviesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            repo.deleteMovieById(selected.id);
            refreshTable();
        }
    }

    private void refreshTable() {
        moviesTable.setItems(FXCollections.observableArrayList(repo.getMovies()));
    }
    private void goBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/admin-dashboard.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
           // showAlert("Error returning to dashboard", Alert.AlertType.ERROR);
        }
    }
}
