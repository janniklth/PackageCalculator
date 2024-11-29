package gui;

import control.Helper;
import control.ShippingRuleLoader;
import data.ShippingRule;
import exceptions.ShippingRuleException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * The CostsOverviewArea class provides a dedicated area within the user interface for viewing shipping costs.
 * It extends {@link GridPane} and displays a table with various shipping rules, including their dimensions and costs.
 *
 * @see javafx.scene.layout.GridPane
 */
public class CostsOverviewArea extends GridPane {

    private TableView<ShippingRule> tableView;

    /**
     * Constructs a CostsOverviewArea instance, initializing the table layout and loading the shipping rules.
     * @see ShippingRule
     * @see ShippingRuleLoader
     * @see TableView
     */
    public CostsOverviewArea() {
        // Create a label for the heading
        Label heading = new Label("Costs Overview");
        heading.setFont(Font.font("System", FontWeight.BOLD, 14));

        // Create a TableView to display the shipping rules
        tableView = new TableView<>();

        // Create columns for the table
        TableColumn<ShippingRule, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<ShippingRule, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%.2f €", rule.getValue().getCost())));
        costColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item);
                    setFont(Font.font("System", FontWeight.BOLD, 12));
                } else {
                    setText(null);
                }
            }
        });

        TableColumn<ShippingRule, String> maxLengthColumn = new TableColumn<>("Max Length");
        maxLengthColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(rule.getValue().getMaxLength() + " mm"));

        TableColumn<ShippingRule, String> maxWidthColumn = new TableColumn<>("Max Width");
        maxWidthColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(rule.getValue().getMaxWidth() + " mm"));

        TableColumn<ShippingRule, String> maxHeightColumn = new TableColumn<>("Max Height");
        maxHeightColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(rule.getValue().getMaxHeight() + " mm"));

        TableColumn<ShippingRule, String> maxWeightColumn = new TableColumn<>("Max Weight");
        maxWeightColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(rule.getValue().getMaxWeight() + " g"));

        TableColumn<ShippingRule, String> maxGirthColumn = new TableColumn<>("Max Girth");
        maxGirthColumn.setCellValueFactory(rule -> {
            Integer maxGirth = rule.getValue().getMaxGirth();
            return new SimpleStringProperty(maxGirth == null ? "-" : maxGirth + " mm");
        });

        // Add columns to the TableView
        tableView.getColumns().addAll(typeColumn, costColumn, maxLengthColumn, maxWidthColumn, maxHeightColumn, maxWeightColumn, maxGirthColumn);

        // Make table fill available width
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Load data into the table
        loadShippingRulesIntoTable();

        // Add a refresh button
        Button refreshButton = new Button("Aktualisieren");
        refreshButton.setOnAction(event -> loadShippingRulesIntoTable());

        // Create a VBox layout and add the heading, table, and button
        VBox layout = new VBox(10, heading, tableView);
        layout.setPrefSize(800, 600);

        // Add the layout to the GridPane
        this.add(layout, 0, 0);
    }

    /**
     * Loads shipping rules using the {@ShippingRuleLoader} and populates the table with them.
     * @see ShippingRuleLoader
     * @see ShippingRule
     */
    private void loadShippingRulesIntoTable() {
        try {
            List<ShippingRule> rules = ShippingRuleLoader.loadShippingRules();
            tableView.setItems(FXCollections.observableArrayList(rules));
        } catch (ShippingRuleException e) {
            e.printStackTrace();
            // Handle exception (e.g., show an error message in the UI)
            Helper.showAlert(Alert.AlertType.ERROR, "Error updating the shipping costs table", e.getMessage());
        }
    }
}
