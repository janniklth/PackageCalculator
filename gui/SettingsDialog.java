package gui;

import control.SettingsManager;
import data.Currency;
import data.ErrorDisplayState;
import data.MeasurementSystem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The SettingsDialog class represents a modal dialog for changing application settings. It allows the user to change
 * the measurement units, currency, and error display settings with ComboBoxes.
 *
 * @see SettingsManager
 * @see MeasurementSystem
 * @see Currency
 * @see ErrorDisplayState
 * @see ComboBox
 */
public class SettingsDialog {

    /**
     * Displays the settings dialog for changing application settings like error messages and currency.
     */
    public static void show() {
        Stage settingsDialog = new Stage();
        settingsDialog.initModality(Modality.APPLICATION_MODAL);
        settingsDialog.setTitle("Settings");

        // Measurement units settings
        Label unitsLabel = new Label("Measurement units:");
        ComboBox<String> unitsComboBox = new ComboBox<>();
        for (MeasurementSystem unit : MeasurementSystem.values()) {
            unitsComboBox.getItems().add(unit.convertToDisplayString());
        }
        unitsComboBox.setValue(SettingsManager.getMeasurementSystem().convertToDisplayString());

        // Currency settings
        Label currencyLabel = new Label("Currency:");
        currencyLabel.setStyle("-fx-font-size: 14px;");
        ComboBox<String> currencyComboBox = new ComboBox<>();
        for (Currency currency : Currency.values()) {
            currencyComboBox.getItems().add(currency.convertToDisplayString());
        }
        currencyComboBox.setValue(SettingsManager.getCurrency().convertToDisplayString());

        // Error display settings
        Label errorLabel = new Label("Show error messages:");
        errorLabel.setStyle("-fx-font-size: 14px;");
        ComboBox<String> errorPopupsComboBox = new ComboBox<>();
        for (ErrorDisplayState state : ErrorDisplayState.values()) {
            errorPopupsComboBox.getItems().add(state.convertToDisplayString());
        }
        errorPopupsComboBox.setValue(SettingsManager.getErrorDisplayState().convertToDisplayString());

        // Apply button: Save settings to SettingsManager and close dialog
        Button applyButton = new Button("Apply");
        applyButton.setStyle("-fx-background-color: #0078D4; -fx-text-fill: white; -fx-font-size: 14px;");
        applyButton.setOnAction(e -> {
            String selectedUnit = unitsComboBox.getValue();
            SettingsManager.setMeasurementSystem(MeasurementSystem.fromDisplayString(selectedUnit));

            String selectedErrorDisplay = errorPopupsComboBox.getValue();
            SettingsManager.setShowErrorPopups(ErrorDisplayState.fromDisplayString(selectedErrorDisplay));

            String selectedCurrency = currencyComboBox.getValue();
            SettingsManager.setCurrency(Currency.fromDisplayString(selectedCurrency));

            settingsDialog.close();
        });

        // Cancel button: Close dialog without saving settings
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 14px;");
        cancelButton.setOnAction(e -> settingsDialog.close());

        // Layout with GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));
        gridPane.setAlignment(Pos.TOP_CENTER);

        // Uniform width for all ComboBoxes
        double comboBoxWidth = 200;

        // Layout for measurement unit settings
        VBox unitsBox = new VBox(5, unitsLabel, unitsComboBox);
        unitsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        unitsComboBox.setPrefWidth(comboBoxWidth);
        gridPane.add(unitsBox, 0, 0);

        // Layout for currency settings
        VBox currencyBox = new VBox(5, currencyLabel, currencyComboBox);
        currencyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        currencyComboBox.setPrefWidth(comboBoxWidth);
        gridPane.add(currencyBox, 0, 1);

        // Layout for error display settings
        VBox errorBox = new VBox(5, errorLabel, errorPopupsComboBox);
        errorLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        errorPopupsComboBox.setPrefWidth(comboBoxWidth);
        gridPane.add(errorBox, 0, 2);

        // Button layout
        HBox buttonBox = new HBox(20, cancelButton, applyButton);
        buttonBox.setAlignment(Pos.CENTER);
        applyButton.setPrefWidth(90);
        cancelButton.setPrefWidth(90);

        // Main layout
        VBox layout = new VBox(20, gridPane, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #ffffff;");

        // Display the dialog
        Scene dialogScene = new Scene(layout, 275, 310);
        settingsDialog.setScene(dialogScene);
        settingsDialog.setResizable(false);
        settingsDialog.showAndWait();
    }
}