package com.example.cinematickets;

import com.example.cinematickets.models.Hall;
import com.example.cinematickets.repos.MainRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class AdminManageHallsController {

    @FXML private TextField nameField;
    @FXML private TextField capacityField;

    @FXML private TableView<Hall> hallsTable;
    @FXML private TableColumn<Hall, Integer> idCol;
    @FXML private TableColumn<Hall, String> nameCol;
    @FXML private TableColumn<Hall, Integer> capacityCol;

    @FXML private Button backBtn;

    private final MainRepository repo = MainRepository.getInstance();
    private final ObservableList<Hall> hallsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        capacityCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCapacity()).asObject());

        refreshTable();

        backBtn.setOnAction(e -> goBack());
    }

    @FXML
    private void addHall() {
        String name = nameField.getText();
        String capText = capacityField.getText();

        if (name.isBlank() || capText.isBlank()) {
            showAlert("Please fill all fields", Alert.AlertType.WARNING);
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(capText);
        } catch (NumberFormatException e) {
            showAlert("Capacity must be a number", Alert.AlertType.WARNING);
            return;
        }

        repo.addHall(name, capacity);
        refreshTable();

        nameField.clear();
        capacityField.clear();
    }

    @FXML
    private void deleteHall() {
        Hall selected = hallsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Please select a hall to delete", Alert.AlertType.WARNING);
            return;
        }

        repo.deleteHallById(selected.getId());
        refreshTable();
    }

    private void refreshTable() {
        hallsList.setAll(repo.getHalls());
        hallsTable.setItems(hallsList);
    }

    private void goBack() {
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
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.show();
    }
}
