package gui;

import control.Calculator;
import control.MessageHandler;
import control.SettingsManager;
import data.MeasurementSystem;
import data.Packet;
import exceptions.ShippingRuleException;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The CalculatorArea class represents the area of the application where the user can input the package dimensions and
 * weight to calculate the shipping costs.
 *
 * <p>This class extends the VBox class and implements the SettingsManager.SettingsListener interface to listen for
 * changes in the settings. It supports converting the input values to the appropriate units based on the current
 * measurement system setting.</p>
 */
public class CalculatorArea extends VBox implements SettingsManager.SettingsListener {

    private Label lengthLabel, widthLabel, heightLabel, weightLabel;
    private TextField lengthTextField, widthTextField, heightTextField, weightTextField;
    private Label shippingCostLabel;
    private Button calcButton;

    /**
     * Constructor for the CalculatorArea. Initializes the input fields and the calculate button.
     */
    public CalculatorArea() {
        // Heading
        Label heading = new Label("Shipping Cost Calculator");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        heading.setTextFill(Color.DARKSLATEBLUE);

        // Input fields
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        lengthLabel = new Label("Length (mm):");
        widthLabel = new Label("Width (mm):");
        heightLabel = new Label("Height (mm):");
        weightLabel = new Label("Weight (g):");

        lengthTextField = new TextField();
        widthTextField = new TextField();
        heightTextField = new TextField();
        weightTextField = new TextField();

        form.add(lengthLabel, 0, 0);
        form.add(lengthTextField, 1, 0);
        form.add(widthLabel, 0, 1);
        form.add(widthTextField, 1, 1);
        form.add(heightLabel, 0, 2);
        form.add(heightTextField, 1, 2);
        form.add(weightLabel, 0, 3);
        form.add(weightTextField, 1, 3);

        shippingCostLabel = new Label("?");
        shippingCostLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        shippingCostLabel.setTextFill(Color.GREEN);

        form.add(new Label("Shipping Costs:"), 0, 4);
        form.add(shippingCostLabel, 1, 4);

        calcButton = new Button("Calculate");
        calcButton.setStyle("-fx-background-color: #0078D4; -fx-text-fill: white;");
        calcButton.setOnAction(ae -> calcShippingCosts());
        form.add(calcButton, 0, 5);

        // Compose layout
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.getChildren().addAll(heading, form);

        // Register for settings updates
        SettingsManager.registerListener(this);
        updateLabels();
    }

    /**
     * Calculates the shipping costs based on the user's input.
     * <p>
     * This method retrieves the dimensions and weight entered by the user, creates a Packet object,
     * and calculates the shipping costs using the Calculator class. The result is returned and also displayed in the
     * shippingCostLabel.
     *
     * @return the calculated shipping costs
     * @throws IllegalArgumentException if any input field contains invalid or wrong formatted data
     */
    private double calcShippingCosts() {
        double costs = 0.0;

        // Check if any text field is empty
        if (lengthTextField.getText().isEmpty() || widthTextField.getText().isEmpty() ||
                heightTextField.getText().isEmpty() || weightTextField.getText().isEmpty()) {

            MessageHandler.handleMessage(Alert.AlertType.ERROR, "Input Error", "All fields must be filled out.");
            return costs;
        }

        // Try to parse user input values and calculate shipping cost and catch any exceptions
        try {
            int length = Integer.parseInt(lengthTextField.getText());
            int width = Integer.parseInt(widthTextField.getText());
            int height = Integer.parseInt(heightTextField.getText());
            int weight = Integer.parseInt(weightTextField.getText());

            // Create packet and calculate shipping costs
            Packet packet = new Packet(length, width, height, weight);
            costs = Calculator.calcShippingCosts(packet);

            // Show result
            shippingCostLabel.setText(Double.toString(costs) + " " + SettingsManager.getCurrency().getSymbol());

        } catch (NumberFormatException e) {
            // Show an error message if non-numeric input is provided
            MessageHandler.handleMessage(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid integer numbers in all fields.");
            shippingCostLabel.setText("?");
        } catch (IllegalArgumentException e) {
            // Show the error message if the packet dimensions or weight are invalid
            MessageHandler.handleMessage(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
            shippingCostLabel.setText("?");
        } catch (ShippingRuleException e) {
            // Show the error message if the shipping rules could not be loaded
            MessageHandler.handleMessage(Alert.AlertType.ERROR, "ShippingRuleException", e.getMessage() + "\n\nCause: " + e.getCause());
            throw new RuntimeException(e);
        }
        return costs;
    }


    /**
     * Called when the settings have changed.
     */
    @Override
    public void onSettingsChanged() {
        // update the labels to reflect the current measurement system setting
        updateLabels();

        // reset the calculation result to prevent confusion when changing the measurement system or currency
        shippingCostLabel.setText("?");
    }

    /**
     * Updates the labels to reflect the current measurement system setting.
     *
     * @see SettingsManager#getMeasurementSystem()
     * @see MeasurementSystem
     */
    private void updateLabels() {
        MeasurementSystem unit = SettingsManager.getMeasurementSystem();
        String lengthUnit = unit == MeasurementSystem.IMPERIAL ? "inches" : "mm";
        String weightUnit = unit == MeasurementSystem.IMPERIAL ? "lbs" : "g";

        lengthLabel.setText("Length (" + lengthUnit + "):");
        widthLabel.setText("Width (" + lengthUnit + "):");
        heightLabel.setText("Height (" + lengthUnit + "):");
        weightLabel.setText("Weight (" + weightUnit + "):");
    }
}