package gui;

import control.Calculator;
import control.Helper;
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
 * The CalculatorArea class provides a graphical user interface for calculating shipping costs based
 * on the dimensions and weight of a package. It extends the GridPane layout to organize the input fields,
 * labels, and buttons in a grid.
 *
 * <p>CalculatorArea relies on the {@link Calculator} class for performing the shipping cost calculations
 * and uses the {@link Helper} class to display alerts when user input is invalid.</p>
 *
 * @see Calculator
 */
public class CalculatorArea extends VBox {

	// Input fields for package dimensions and weight
	TextField lengthTextField = new TextField();
	TextField widthTextField = new TextField();
	TextField heightTextField = new TextField();
	TextField weightTextField = new TextField();

	// Label to display the calculated shipping cost
	Label shippingCostLabel = new Label("?");

	// Button to trigger the shipping cost calculation
	Button calcButton = new Button("Calculate");

	/**
	 * Calculates the shipping costs based on the user's input.
	 *
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

			Helper.showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled out.");
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
			shippingCostLabel.setText(Double.toString(costs));

		} catch (NumberFormatException e) {
			// Show an error message if non-numeric input is provided
			Helper.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid integer numbers in all fields.");
			shippingCostLabel.setText("?");
		} catch (IllegalArgumentException e) {
			// Show the error message if the packet dimensions or weight are invalid
			Helper.showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
			shippingCostLabel.setText("?");
		} catch (ShippingRuleException e) {
			// Show the error message if the shipping rules could not be loaded
			Helper.showAlert(Alert.AlertType.ERROR, "ShippingRuleException", e.getMessage() + "\n\nCause: " + e.getCause());
			throw new RuntimeException(e);
		}

		return costs;
	}

	/**
	 * Constructor for the CalculatorArea class.
	 *
	 * This constructor sets up the layout of the input fields, labels, and button. It also sets the
	 * action listener for the "Calculate" button to trigger the shipping cost calculation.
	 *
	 * @see Helper#showAlert(Alert.AlertType, String, String)
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

		form.add(new Label("Length (mm):"), 0, 0);
		form.add(lengthTextField, 1, 0);
		form.add(new Label("Width (mm):"), 0, 1);
		form.add(widthTextField, 1, 1);
		form.add(new Label("Height (mm):"), 0, 2);
		form.add(heightTextField, 1, 2);
		form.add(new Label("Weight (g):"), 0, 3);
		form.add(weightTextField, 1, 3);

		// result label and button to calculate
		form.add(new Label("Shipping Costs:"), 0, 4);
		shippingCostLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		shippingCostLabel.setTextFill(Color.GREEN);
		form.add(shippingCostLabel, 1, 4);

		calcButton.setStyle("-fx-background-color: #0078D4; -fx-text-fill: white;");
		calcButton.setOnAction(ae -> calcShippingCosts());
		form.add(calcButton, 0, 5);

		// compose layout
		this.setSpacing(20);
		this.setPadding(new Insets(20));
		this.getChildren().addAll(heading, form);
	}
}
