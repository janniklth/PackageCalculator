package gui;

import control.Calculator;
import control.MessageHandler;
import control.SettingsManager;
import data.MeasurementSystem;
import data.Packet;
import exceptions.ShippingRuleException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import ressources.Constants;

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
        Label heading = new Label(Constants.CALCULATOR_AREA_LABEL);
        heading.setFont(Font.font(Constants.FONT_NAME, FontWeight.BOLD, 20));
        heading.setTextFill(Color.DARKSLATEBLUE);

        // Input fields
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        lengthLabel = new Label(Constants.LENGTH_LABEL + " (" + SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + "):");
        widthLabel = new Label(Constants.WIDTH_LABEL + " (" + SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + "):");
        heightLabel = new Label(Constants.HEIGHT_LABEL + " (" + SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + "):");
        weightLabel = new Label(Constants.WEIGHT_LABEL + " (" + SettingsManager.getMeasurementSystem().getWeightUnitSymbol() + "):");

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

        shippingCostLabel = new Label("...");
        shippingCostLabel.setFont(Font.font(Constants.FONT_NAME, FontWeight.BOLD, 14));
        shippingCostLabel.setTextFill(Color.GREEN);

        form.add(new Label(Constants.RESULT_LABEL), 0, 4);
        form.add(shippingCostLabel, 1, 4);

        calcButton = new Button(Constants.CALCULATE_BUTTON);
        calcButton.setStyle("-fx-background-color: #0078D4; -fx-text-fill: white;");
        calcButton.setOnAction(ae -> calcShippingCosts());
        form.add(calcButton, 0, 5);

        // add a note next to the button to inform the user about the calculation and different orientations (not in the grid)
        Label note = new Label(Constants.CALCULATE_BUTTON_NOTE);
        note.setFont(Font.font(Constants.FONT_NAME, FontWeight.NORMAL, 12));
        form.add(note, 0, 6, 2, 1);

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

        // Validate user input
        if (!validateUserInput()) {
            return 0;
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
            showLoadingDotsAndResult(Double.toString(costs) + " " + SettingsManager.getCurrency().getSymbol(),
                    Color.GREEN);

        } catch (NumberFormatException e) {
            // Show an error message if non-numeric input is provided
            showLoadingDotsAndResult(Constants.CALCULATION_ERROR_RESULT, Color.RED);
            MessageHandler.handleMessage(Alert.AlertType.ERROR, Constants.INVALID_INPUT_TITLE, Constants.INVALID_INPUT + Constants.ENTER_VALID_NUMBER + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Show the error message if the packet dimensions or weight are invalid
            showLoadingDotsAndResult(Constants.CALCULATION_ERROR_RESULT, Color.RED);
            MessageHandler.handleMessage(Alert.AlertType.ERROR, Constants.INVALID_INPUT_TITLE, e.getMessage());
        } catch (ShippingRuleException e) {
            // Show the error message if the shipping rules could not be loaded
            showLoadingDotsAndResult(Constants.CALCULATION_ERROR_RESULT, Color.RED);
            MessageHandler.handleMessage(Alert.AlertType.ERROR, "ShippingRuleException", e.getMessage() + "\n\nCause: " + e.getCause());
            throw new RuntimeException(e);
        }
        return costs;
    }

    /**
     * Shows a loading animation with three black dots and then the result text in the specified color.
     * @param resultText the text to show after the loading dots
     * @param color the color to use for the result text
     * @see Color
     * @see Timeline
     * @see KeyFrame
     */
    private void showLoadingDotsAndResult(String resultText, Color color) {
        Timeline timeline = new Timeline();

        // Animation of loading dots
        for (int i = 0; i <= Constants.CALCULATION_ANIMATION_CYCLES; i++) {
            int count = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Constants.CALCULATION_ANIMATION_DELAY * i), e -> {
                shippingCostLabel.setText(Constants.CALUCLATE_ANIMATION_SYMBOL.repeat(count));
                shippingCostLabel.setTextFill(Color.BLACK);
            }));
        }

        // Show the result after the loading dots in the specified color
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Constants.CALCULATION_ANIMATION_DURATION), e -> {
            shippingCostLabel.setText(resultText);
            shippingCostLabel.setTextFill(color);
        }));

        timeline.play();
    }

    /**
     * Validates the user input and shows detailed error messages if any of the input fields are invalid. Only validation
     * of user input is done here, no validation of meaningful values is done here (max values, min values, etc.).
     */
    private boolean validateUserInput() {
        // Initialize error message and check flags
        StringBuilder errorMessage = new StringBuilder(Constants.INVALID_INPUT);
        boolean hasError = false;

        // Check if the length input field is empty
        if (lengthTextField.getText().isEmpty()) {
            errorMessage.append(Constants.LENGTH_FIELD_EMPTY);
            hasError = true;
        }

        // Check if the width input field is empty
        if (widthTextField.getText().isEmpty()) {
            errorMessage.append(Constants.WIDTH_FIELD_EMPTY);
            hasError = true;
        }

        // Check if the height input field is empty
        if (heightTextField.getText().isEmpty()) {
            errorMessage.append(Constants.HEIGHT_FIELD_EMPTY);
            hasError = true;
        }

        // Check if the weight input field is empty
        if (weightTextField.getText().isEmpty()) {
            errorMessage.append(Constants.WEIGHT_FIELD_EMPTY);
            hasError = true;
        }

        // Show error message if any check failed
        if (hasError) {
            showLoadingDotsAndResult(Constants.CALCULATION_ERROR_RESULT, Color.RED);
            MessageHandler.handleMessage(Alert.AlertType.ERROR, Constants.INVALID_INPUT_TITLE, errorMessage.toString().trim());
            return false;
        }
        return true;
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
        lengthLabel.setText(Constants.LENGTH_LABEL + " (" + SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + "):");
        widthLabel.setText(Constants.WIDTH_LABEL + " (" + SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + "):");
        heightLabel.setText(Constants.HEIGHT_LABEL + " (" + SettingsManager.getMeasurementSystem().getLengthUnitSymbol() + "):");
        weightLabel.setText(Constants.WEIGHT_LABEL + " (" + SettingsManager.getMeasurementSystem().getWeightUnitSymbol() + "):");
    }
}