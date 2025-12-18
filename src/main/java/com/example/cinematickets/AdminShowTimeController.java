package com.example.cinematickets;

import com.example.cinematickets.models.*;
import com.example.cinematickets.repos.MainRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class AdminShowTimeController {
    @FXML
    private ComboBox<Movie> movieComboBox;

    @FXML
    private ComboBox<Hall> hallComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;

    @FXML
    private TextField priceField;  //Add price input field

    @FXML
    private Button addShowTimeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<ShowTime> showTimesTable;

    @FXML
    private TableColumn<ShowTime, Integer> idColumn;

    @FXML
    private TableColumn<ShowTime, String> movieColumn;

    @FXML
    private TableColumn<ShowTime, String> hallColumn;

    @FXML
    private TableColumn<ShowTime, LocalDateTime> dateTimeColumn;

    @FXML
    private TableColumn<ShowTime, Boolean> activeColumn;

    @FXML
    private TableColumn<ShowTime, Float> priceColumn;  //Add price column

    @FXML
    private Label statusLabel;

    private MainRepository repository;

    @FXML
    public void initialize() {
        repository = MainRepository.getInstance();

        // Setup hour spinner (0-23)
        SpinnerValueFactory<Integer> hourValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12);
        hourSpinner.setValueFactory(hourValueFactory);

        // Setup minute spinner (0-59)
        SpinnerValueFactory<Integer> minuteValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 15);
        minuteSpinner.setValueFactory(minuteValueFactory);

        // Setup table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));  // NEW: Add price column

        // Custom cell factories for movie and hall names
        movieColumn.setCellValueFactory(cellData -> {
            int movieId = cellData.getValue().getMovieId();
            Movie movie = repository.getMovieById(movieId);
            return new javafx.beans.property.SimpleStringProperty(
                    movie != null ? movie.getName() : "Unknown"
            );
        });

        hallColumn.setCellValueFactory(cellData -> {
            int hallId = cellData.getValue().getHallId();
            Hall hall = repository.getHallById(hallId);
            return new javafx.beans.property.SimpleStringProperty(
                    hall != null ? hall.getName() : "Unknown"
            );
        });

        // Load initial data
        loadMovies();
        loadHalls();
        loadShowTimes();

        // Set default date to today
        datePicker.setValue(LocalDate.now());

        // Set default price
        if (priceField != null) {
            priceField.setText("50.0");  // Default price
        }

        // Setup back button
        backButton.setOnAction(e -> goBackToDashboard());

        // Setup delete button
        if (deleteButton != null) {
            deleteButton.setOnAction(e -> handleDeleteShowTime());
        }

        // Enable/disable delete button based on selection
        showTimesTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (deleteButton != null) {
                        deleteButton.setDisable(newSelection == null);
                    }
                }
        );

        // Initially disable delete button
        if (deleteButton != null) {
            deleteButton.setDisable(true);
        }

    }

    private void loadMovies() { //fetch the movies from the repo
        List<Movie> movies = repository.getMovies();
        ObservableList<Movie> movieList = FXCollections.observableArrayList(movies);
        movieComboBox.setItems(movieList);

        // Custom display for combo box
        movieComboBox.setCellFactory(param -> new ListCell<Movie>() {
            @Override
            protected void updateItem(Movie item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        movieComboBox.setButtonCell(new ListCell<Movie>() {
            @Override
            protected void updateItem(Movie item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        if (!movies.isEmpty()) {
            movieComboBox.getSelectionModel().selectFirst();
        }
    }

    private void loadHalls() { //fetch the halls from the repo
        List<Hall> halls = repository.getHalls();
        ObservableList<Hall> hallList = FXCollections.observableArrayList(halls);
        hallComboBox.setItems(hallList);

        // Custom display for combo box
        hallComboBox.setCellFactory(param -> new ListCell<Hall>() {
            @Override
            protected void updateItem(Hall item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (Capacity: " + item.getCapacity() + ")");
                }
            }
        });

        hallComboBox.setButtonCell(new ListCell<Hall>() {
            @Override
            protected void updateItem(Hall item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (Capacity: " + item.getCapacity() + ")");
                }
            }
        });

        if (!halls.isEmpty()) {
            hallComboBox.getSelectionModel().selectFirst();
        }
    }

    private void loadShowTimes() { //fill the showtime table
        List<ShowTime> showTimes = repository.getShowTimes();
        ObservableList<ShowTime> showTimeList = FXCollections.observableArrayList(showTimes);
        showTimesTable.setItems(showTimeList);
    }

    @FXML //main function to add a new showtime
    private void handleAddShowTime() {
        try {
            // Validate inputs
            Movie selectedMovie = movieComboBox.getSelectionModel().getSelectedItem();
            Hall selectedHall = hallComboBox.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = datePicker.getValue();

            if (selectedMovie == null) {
                showStatus("Please select a movie", false);
                return;
            }

            if (selectedHall == null) {
                showStatus("Please select a hall", false);
                return;
            }

            if (selectedDate == null) {
                showStatus("Please select a date", false);
                return;
            }

            // Parse and validate price
            Float price;
            try {
                String priceText = priceField != null ? priceField.getText() : "50.0";
                price = Float.parseFloat(priceText);
                if (price <= 0) {
                    showStatus("Price must be greater than 0", false);
                    return;
                }
            } catch (NumberFormatException e) {
                showStatus("Please enter a valid price", false);
                return;
            }

            // Create LocalDateTime
            int hour = hourSpinner.getValue();
            int minute = minuteSpinner.getValue();
            LocalTime time = LocalTime.of(hour, minute);
            LocalDateTime dateTime = LocalDateTime.of(selectedDate, time);

            // Check if datetime is in the past
            if (dateTime.isBefore(LocalDateTime.now())) {
                showStatus("Cannot create showtime in the past", false);
                return;
            }

            // FIXED: Add showtime with correct parameters
            // Method signature: addShowTime(LocalDateTime date, Float price, int movieId, int hallId)
            boolean success = repository.addShowTime(
                    dateTime,              // LocalDateTime
                    price,                 // Float price
                    selectedMovie.id,      // int movieId (NOT float!)
                    selectedHall.getId()   // int hallId
            );

            if (success) {
                showStatus("ShowTime added successfully!", true);
                loadShowTimes();
                clearInputs();
            } else {
                showStatus("Failed to add showtime. Check movie/hall exists.", false);
            }

        } catch (Exception e) {
            showStatus("Error: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteShowTime() {
        // Get selected showtime
        ShowTime selectedShowTime = showTimesTable.getSelectionModel().getSelectedItem();

        if (selectedShowTime == null) {
            showStatus("Please select a showtime to delete", false);
            return;
        }

        // Get movie and hall names for the confirmation dialog
        Movie movie = repository.getMovieById(selectedShowTime.getMovieId());
        Hall hall = repository.getHallById(selectedShowTime.getHallId());
        String movieName = movie != null ? movie.getName() : "Unknown Movie";
        String hallName = hall != null ? hall.getName() : "Unknown Hall";

        // Confirmation dialog
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete ShowTime");
        confirmAlert.setHeaderText("Are you sure you want to delete this showtime?");
        confirmAlert.setContentText(
                "Movie: " + movieName + "\n" +
                        "Hall: " + hallName + "\n" +
                        "Date & Time: " + selectedShowTime.getDatetime() + "\n" +
                        "Price: $" + selectedShowTime.getPrice() + "\n\n" +
                        "This action cannot be undone!"
        );

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed, proceed with deletion
            boolean success = repository.deleteShowTimeById(selectedShowTime.getId());

            if (success) {
                showStatus("ShowTime deleted successfully!", true);
                loadShowTimes();  // Refresh the table
            } else {
                showStatus("Failed to delete showtime", false);
            }
        }
    }

    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setStyle(isSuccess ?
                "-fx-text-fill: green; -fx-font-weight: bold;" :
                "-fx-text-fill: red; -fx-font-weight: bold;");

        // Clear status after 3 seconds
        javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(javafx.util.Duration.seconds(3));
        pause.setOnFinished(e -> statusLabel.setText(""));
        pause.play();
    }

    private void clearInputs() {
        //reset the form after adding
        datePicker.setValue(LocalDate.now());
        hourSpinner.getValueFactory().setValue(12);
        minuteSpinner.getValueFactory().setValue(0);
        if (priceField != null) {
            priceField.setText("50.0");
        }
        // Keep movie and hall selection as is
    }

    @FXML
    private void handleRefresh() {
        //reload the data on refresh
        loadMovies();
        loadHalls();
        loadShowTimes();
        showStatus("Data refreshed", true);
    }

    private void goBackToDashboard() {
        //navigate back to dashboard
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cinematickets/admin-dashboard.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("Admin Dashboard");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showStatus("Error returning to dashboard", false);
        }
    }

}