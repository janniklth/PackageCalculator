package gui;

import control.Helper;
import control.ShippingRuleLoader;
import data.ShippingRule;
import exceptions.ShippingRuleException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class CostsOverviewArea extends VBox {

    private final TableView<ShippingRule> tableView = new TableView<>();

    public CostsOverviewArea() {
        // Überschrift
        Label heading = new Label("Costs Overview");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        heading.setTextFill(Color.DARKSLATEBLUE);

        // Tabelle einrichten
        TableColumn<ShippingRule, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<ShippingRule, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%.2f €", rule.getValue().getCost())));
        costColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ShippingRule, String> dimensionsColumn = new TableColumn<>("Dimensions (mm)");
        dimensionsColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%d x %d x %d",
                        rule.getValue().getMaxLength(),
                        rule.getValue().getMaxWidth(),
                        rule.getValue().getMaxHeight())));

        TableColumn<ShippingRule, String> weightColumn = new TableColumn<>("Max Weight (g)");
        weightColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(rule.getValue().getMaxWeight() + " g"));

        tableView.getColumns().addAll(typeColumn, costColumn, dimensionsColumn, weightColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Daten laden
        loadShippingRulesIntoTable();

        // Layout
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.getChildren().addAll(heading, tableView);
    }

    private void loadShippingRulesIntoTable() {
        try {
            List<ShippingRule> rules = ShippingRuleLoader.loadShippingRules();
            tableView.setItems(FXCollections.observableArrayList(rules));
        } catch (ShippingRuleException e) {
            Helper.showAlert(Alert.AlertType.ERROR, "Error loading rules", e.getMessage());
        }
    }
}
