package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerManagerApp extends Application {

    // Requirement 2: Create ObservableList<Customer>
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private final ObservableList<String> provinces = FXCollections.observableArrayList(
            "Lusaka", "Northern", "Chipata", "Southern",
            "Central", "NorthWestern", "Copperbelt",
            "Western", "Muchinga", "Luapula"
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // --- 1. Setup Input Form ---
        TextField nameField = new TextField();
        nameField.setPromptText("Customer Name");

        // Requirement 1: Province List (ComboBox)
        ComboBox<String> provinceBox = new ComboBox<>(provinces);
        provinceBox.setPromptText("Select Province");

        Button addButton = new Button("Add Customer");
        Button deleteButton = new Button("Delete Selected");

        HBox inputForm = new HBox(10, new Label("Name:"), nameField, new Label("Province:"), provinceBox, addButton);
        inputForm.setAlignment(Pos.CENTER_LEFT);
        inputForm.setPadding(new Insets(10));

        // --- 2. Setup TableView ---
        // Requirement 3: TableView with name and province columns
        TableView<Customer> tableView = new TableView<>();

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<Customer, String> provCol = new TableColumn<>("Province");
        provCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        provCol.setPrefWidth(200);

        tableView.getColumns().addAll(nameCol, provCol);
        tableView.setItems(customerList);
        tableView.setPlaceholder(new Label("No customers added yet."));

        // --- Event Handlers ---

        // Requirement 4: Validate input, then add
        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String province = provinceBox.getValue();

            if (name.isEmpty() || province == null) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a name and select a province.");
                return;
            }

            // Add to list
            customerList.add(new Customer(name, province));

            // Clear inputs
            nameField.clear();
            provinceBox.getSelectionModel().clearSelection();
            nameField.requestFocus(); // Requirement 6: Keyboard access
        });

        // Requirement 5: Confirm deletion
        deleteButton.setOnAction(e -> {
            Customer selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to delete.");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Customer");
            alert.setContentText("Are you sure you want to delete " + selected.getName() + "?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    customerList.remove(selected);
                }
            });
        });

        // --- Layout Assembly ---
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(inputForm, tableView, deleteButton);
        VBox.setVgrow(tableView, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("Customer Manager Lab");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper for alerts
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}