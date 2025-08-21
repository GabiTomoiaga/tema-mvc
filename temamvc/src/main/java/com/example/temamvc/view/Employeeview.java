package com.example.temamvc.view;

import com.example.temamvc.model.Perfume;
import com.example.temamvc.model.Observer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class Employeeview implements Observer {

    @FXML private TableView<Perfume> perfumeTable;
    @FXML private TableColumn<Perfume, Integer> colId;
    @FXML private TableColumn<Perfume, String> colName;
    @FXML private TableColumn<Perfume, String> colBrand;
    @FXML private TableColumn<Perfume, Double> colPrice;
    @FXML private TableColumn<Perfume, String> colDescription;

    @FXML private TextField nameField;
    @FXML private TextField brandField;
    @FXML private TextField priceField;
    @FXML private TextField descriptionField;

    @FXML private TextField filterBrand;
    @FXML private ComboBox<String> filterPrice;
    @FXML private CheckBox filterAvailability;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button filterButton;
    @FXML private Button resetButton;

    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        colBrand.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBrand()));
        colPrice.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        colDescription.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));

        filterPrice.setItems(FXCollections.observableArrayList("<100", "100-300", ">300"));
    }

    // ✅ Implementare Observer
    @Override
    public void update() {
        // Va fi apelat de Observable (repository) pentru refresh
        // Poți apela controller.refreshTable() dacă ai referință la controller
        System.out.println("Update primit - UI trebuie să fie reîmprospătat.");
    }

    // ✅ Metode pentru Controller
    public Button getAddButton() { return addButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getDeleteButton() { return deleteButton; }
    public Button getFilterButton() { return filterButton; }
    public Button getResetButton() { return resetButton; }

    public void updateTable(List<Perfume> perfumes) {
        perfumeTable.setItems(FXCollections.observableArrayList(perfumes));
    }

    public Perfume getPerfumeInput() {
        try {
            String name = nameField.getText();
            String brand = brandField.getText();
            double price = Double.parseDouble(priceField.getText());
            String description = descriptionField.getText();

            Perfume selected = getSelectedPerfume();
            if (selected != null) {
                return new Perfume(selected.getId(), name, brand, price, description);
            } else {
                return new Perfume(null, name, brand, price, description);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Perfume getSelectedPerfume() {
        return perfumeTable.getSelectionModel().getSelectedItem();
    }

    public String getBrandFilter() {
        return filterBrand.getText();
    }

    public String getPriceFilter() {
        return filterPrice.getValue();
    }

    public boolean isOnlyAvailableChecked() {
        return filterAvailability.isSelected();
    }

    public void clearFilters() {
        filterBrand.clear();
        filterPrice.setValue(null);
        filterAvailability.setSelected(false);
    }

    public void showMessage(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    public void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
