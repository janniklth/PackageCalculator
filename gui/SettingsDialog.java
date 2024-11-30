package gui;

import control.SettingsManager;
import data.MeasurementUnit;
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
 * The SettingsDialog class represents a modal dialog for changing application settings.
 */
public class SettingsDialog {

    /**
     * Displays the settings dialog for changing application settings like error messages and currency.
     */
    public static void show() {
        Stage settingsDialog = new Stage();
        settingsDialog.initModality(Modality.APPLICATION_MODAL);
        settingsDialog.setTitle("Settings");

        // Create UI components
        Label unitsLabel = new Label("Measurement units:");
        ComboBox<String> unitsComboBox = new ComboBox<>();
        for (MeasurementUnit unit : MeasurementUnit.values()) {
            unitsComboBox.getItems().add(unit.convertToDisplayString());
        }
        unitsComboBox.setValue(SettingsManager.getMeasurementUnit().convertToDisplayString());


        Label errorLabel = new Label("Show error messages:");
        errorLabel.setStyle("-fx-font-size: 14px;");
        ComboBox<String> errorPopupsComboBox = new ComboBox<>();
        errorPopupsComboBox.getItems().addAll("Popup + log messages", "only log messages");
        errorPopupsComboBox.setValue(SettingsManager.isShowErrorPopupsEnabled() ? "Popup + log messages" : "only log messages");

        Label currencyLabel = new Label("Currency:");
        currencyLabel.setStyle("-fx-font-size: 14px;");
        ComboBox<String> currencyComboBox = new ComboBox<>();
        currencyComboBox.getItems().addAll("EUR (€)", "USD ($)", "GBP (£)");
        currencyComboBox.setValue(SettingsManager.getCurrency());

        // Apply button
        Button applyButton = new Button("Apply");
        applyButton.setStyle("-fx-background-color: #0078D4; -fx-text-fill: white; -fx-font-size: 14px;");
        applyButton.setOnAction(e -> {
            String selectedUnit = unitsComboBox.getValue();
            SettingsManager.setMeasurementUnit(MeasurementUnit.fromDisplayString(selectedUnit));

            String selectedErrorDisplay = errorPopupsComboBox.getValue();
            SettingsManager.setShowErrorPopups(selectedErrorDisplay.equals("Popup + log messages"));

            String selectedCurrency = currencyComboBox.getValue();
            SettingsManager.setCurrency(selectedCurrency);

            settingsDialog.close();
        });

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 14px;");
        cancelButton.setOnAction(e -> settingsDialog.close());

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(15));
        gridPane.add(unitsLabel, 0, 0);
        gridPane.add(unitsComboBox, 1, 0);
        gridPane.add(errorLabel, 0, 1);
        gridPane.add(errorPopupsComboBox, 1, 1);
        gridPane.add(currencyLabel, 0, 2);
        gridPane.add(currencyComboBox, 1, 2);

        HBox buttonBox = new HBox(10, applyButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        VBox layout = new VBox(15, gridPane, buttonBox);
        layout.setPadding(new Insets(15));

        // Set scene and show dialog
        Scene dialogScene = new Scene(layout, 400, 220);
        settingsDialog.setScene(dialogScene);
        settingsDialog.setResizable(false);
        settingsDialog.showAndWait();
    }
}
