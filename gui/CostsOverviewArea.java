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
import ressources.Constants;

import java.util.List;

/**
 * The CostsOverviewArea class represents the area of the application that displays an overview of the shipping costs.
 *
 * <p>Includes a TableView that displays the shipping rules loaded from an external JSON file using the
 * {@link ShippingRuleLoader}. The table displays the type, cost, dimensions, and weight of each shipping rule. The
 * dimensions and weight are displayedin the current measurement system and currency set in the {@link SettingsManager}
 * and updated dynamically when the settings change.</p>
 *
 * @see ShippingRuleLoader
 * @see SettingsManager
 * @see ShippingRule
 * @see TableView
 * @see TableColumn
 * @see data.MeasurementSystem
 * @see data.Currency
 */
public class CostsOverviewArea extends VBox implements SettingsManager.SettingsListener {

    private final TableView<ShippingRule> tableView = new TableView<>();
    private final TableColumn<ShippingRule, String> dimensionsColumn = new TableColumn<>();
    private final TableColumn<ShippingRule, String> girthColumn = new TableColumn<>();
    private final TableColumn<ShippingRule, String> weightColumn = new TableColumn<>();
    private final MeasurementSystem baseSystem = MeasurementSystem.METRIC;

    /**
     * Constructs a new CostsOverviewArea with a heading and a TableView displaying the shipping rules.
     * TODO: Add constants or external style sheets for styling the ui components.
     */
    public CostsOverviewArea() {
        // heading
        final Label heading = new Label(Constants.COSTVIEW_AREA_LABEL);
        heading.setFont(Font.font(Constants.FONT_NAME, FontWeight.BOLD, 20));
        heading.setTextFill(Color.DARKSLATEBLUE);

        // TableView setup
        final TableColumn<ShippingRule, String> typeColumn =
                new TableColumn<>(Constants.COSTVIEW_TABLE_TYPE_COLUM_HEADER);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Initialize column for cost
        final TableColumn<ShippingRule, String> costColumn =
                new TableColumn<>(Constants.COSTVIEW_TABLE_COST_COLUM_HEADER);
        costColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%.2f " + SettingsManager.getCurrency().getSymbol(),
                        Double.valueOf(SettingsManager.getCurrency().convertFromEuro(rule.getValue().getCost())))));
        costColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");

        // Initialize column for dimensions with dynamic header
        this.dimensionsColumn.setText(Constants.COSTVIEW_TABLE_DIMENSIONS_COLUM_HEADER + " (" +
                SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + ")");
        this.dimensionsColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%.1f x %.1f x %.1f",
                        Double.valueOf(this.baseSystem.convertLength(rule.getValue().getMaxLength(),
                                SettingsManager.getMeasurementSystem(),
                                false)),
                        Double.valueOf(this.baseSystem.convertLength(rule.getValue().getMaxWidth(),
                                SettingsManager.getMeasurementSystem(),
                                false)),
                        Double.valueOf(this.baseSystem.convertLength(rule.getValue().getMaxHeight(),
                                SettingsManager.getMeasurementSystem(),
                                false)))));

        // Initialize column for girth with dynamic header (- if null)
        this.girthColumn.setText(Constants.COSTVIEW_TABLE_GIRTH_COLUMN_HEADER + " (" +
                SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + ")");
        this.girthColumn.setCellValueFactory(rule -> {
            if (rule.getValue().getMaxGirth() == null) {
                return new SimpleStringProperty("- - -");
            } else {
                return new SimpleStringProperty(String.format("%.1f %s",
                        Double.valueOf(this.baseSystem.convertLength(rule.getValue().getMaxGirth(),
                                SettingsManager.getMeasurementSystem(), false)),
                        SettingsManager.getMeasurementSystem().getLengthUnitSymbol()));
            }
        });

        // Initialize column for weight with dynamic header
        this.weightColumn.setText(Constants.COSTVIEW_TABLE_MAXWEIGHT_COLUMN_HEADER + " (" +
                SettingsManager.getMeasurementSystem().getWeightUnitSymbol() + ")");
        this.weightColumn.setCellValueFactory(rule ->
                new SimpleStringProperty(String.format("%.1f %s",
                        Double.valueOf(this.baseSystem.convertWeight(rule.getValue().getMaxWeight(),
                                SettingsManager.getMeasurementSystem(), false)),
                        SettingsManager.getMeasurementSystem().getWeightUnitSymbol())));

        // Add the columns to the table and set the column resize policy
        tableView.getColumns().addAll(typeColumn, costColumn, dimensionsColumn, girthColumn, weightColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        // load shipping rules into table
        this.loadShippingRulesIntoTable();


        // compose layout
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.getChildren().addAll(heading, tableView);

        // Register for settings updates
        SettingsManager.registerListener(this);
    }

    /**
     * Loads the shipping rules from an external JSON file using the {@link ShippingRuleLoader} and populates the
     * TableView.
     *
     * @see ShippingRuleLoader
     */
    private void loadShippingRulesIntoTable() {
        try {
            List<ShippingRule> rules = ShippingRuleLoader.loadShippingRules();
            this.tableView.setItems(FXCollections.observableArrayList(rules));
        } catch (ShippingRuleException e) {
            MessageHandler.handleMessage(Alert.AlertType.ERROR, Constants.SHIPPING_RULES_ERROR_LOADING,
                    e.getMessage() + "\n" + e.getCause());
        }
    }

    /**
     * Called when the settings are changed to update the column headers and refresh the table data.
     */
    @Override
    public void onSettingsChanged() {
        // Update column headers
        this.dimensionsColumn.setText(Constants.COSTVIEW_TABLE_DIMENSIONS_COLUM_HEADER + " (" +
                SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + ")");
        this.girthColumn.setText(Constants.COSTVIEW_TABLE_GIRTH_COLUMN_HEADER + " (" +
                SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + ")");
        this.weightColumn.setText(Constants.COSTVIEW_TABLE_MAXWEIGHT_COLUMN_HEADER + " (" +
                SettingsManager.getMeasurementSystem().getWeightUnitSymbol() + ")");

        // Refresh the table data
        this.tableView.refresh();
    }
}