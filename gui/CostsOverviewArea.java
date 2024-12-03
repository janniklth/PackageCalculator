package gui;

import control.MessageHandler;
import control.SettingsManager;
import control.ShippingRuleLoader;
import data.MeasurementSystem;
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

public class CostsOverviewArea extends VBox implements SettingsManager.SettingsListener {

    private final TableView<ShippingRule> tableView = new TableView<>();
    private final TableColumn<ShippingRule, String> dimensionsColumn = new TableColumn<>();
    private final TableColumn<ShippingRule, String> weightColumn = new TableColumn<>();
    private final MeasurementSystem baseSystem = MeasurementSystem.METRIC;

    public CostsOverviewArea() {
        // heading
        Label heading = new Label("Costs Overview");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        heading.setTextFill(Color.DARKSLATEBLUE);

        // TableView setup
        TableColumn<ShippingRule, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<ShippingRule, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%.2f " + SettingsManager.getCurrency().getSymbol(),
                        SettingsManager.getCurrency().convertFromEuro(rule.getValue().getCost()))));
        costColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");

        // Initialize columns with dynamic headers
        dimensionsColumn.setText("Dimensions (" + SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + ")");
        dimensionsColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%.1f x %.1f x %.1f",
                        baseSystem.convertLength(rule.getValue().getMaxLength(), SettingsManager.getMeasurementSystem(),
                                false),
                        baseSystem.convertLength(rule.getValue().getMaxWidth(), SettingsManager.getMeasurementSystem(),
                                false),
                        baseSystem.convertLength(rule.getValue().getMaxHeight(), SettingsManager.getMeasurementSystem(),
                                false))));

        weightColumn.setText("Max Weight (" + SettingsManager.getMeasurementSystem().getWeightUnitSymbol() + ")");
        weightColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%.1f %s",
                        baseSystem.convertWeight(rule.getValue().getMaxWeight(), SettingsManager.getMeasurementSystem(), false),
                        SettingsManager.getMeasurementSystem().getWeightUnitSymbol())));

        tableView.getColumns().addAll(typeColumn, costColumn, dimensionsColumn, weightColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // load shipping rules into table
        loadShippingRulesIntoTable();

        // compose layout
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.getChildren().addAll(heading, tableView);

        // Register for settings updates
        SettingsManager.registerListener(this);
    }

    private void loadShippingRulesIntoTable() {
        try {
            List<ShippingRule> rules = ShippingRuleLoader.loadShippingRules();
            tableView.setItems(FXCollections.observableArrayList(rules));
        } catch (ShippingRuleException e) {
            MessageHandler.handleMessage(Alert.AlertType.ERROR, "Error loading rules", e.getMessage());
        }
    }

    @Override
    public void onSettingsChanged() {
        // Update column headers
        dimensionsColumn.setText("Dimensions (" + SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + ")");
        weightColumn.setText("Max Weight (" + SettingsManager.getMeasurementSystem().getWeightUnitSymbol() + ")");

        // Refresh the table data
        tableView.refresh();
    }
}