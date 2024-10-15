package gui;

import control.Calculator;
import data.Packet;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * The CalculatorArea class provides a graphical user interface for calculating shipping costs based
 * on the dimensions and weight of a package. It extends the GridPane layout to organize the input fields,
 * labels, and buttons in a grid.
 */
public class CalculatorArea extends GridPane {

	// Input fields for package dimensions and weight
	TextField lengthTextField = new TextField();
	TextField widthTextField  = new TextField();
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
	 */
	private double calcShippingCosts() {
		// Initialize Calculator
		Calculator calc = new Calculator();

		// Get user input values
		int length = Integer.parseInt(lengthTextField.getText());
		int width = Integer.parseInt(widthTextField.getText());
		int height = Integer.parseInt(heightTextField.getText());
		int weight = Integer.parseInt(weightTextField.getText());

		// Perform calculation
		Packet packet = new Packet(length, width, height, weight);
		Double costs = calc.calcShippingCosts(packet);

		// Show result
		shippingCostLabel.setText(costs.toString());

		return costs;
	}

	/**
	 * Constructor for the CalculatorArea class.
	 *
	 * This constructor sets up the layout of the input fields, labels, and button. It also sets the
	 * action listener for the "Calculate" button to trigger the shipping cost calculation.
	 */
	public CalculatorArea() {
		// Set standard distance between elements
		this.setPadding(new Insets(10, 10, 10, 10));

		// Add description labels for input fields
		this.add(new Label("Length: "), 1, 1);
		this.add(new Label("Width:  "), 1, 2);
		this.add(new Label("Height: "), 1, 3);
		this.add(new Label("Weight: "), 1, 4);

		// Add input fields for user input
		this.add(lengthTextField, 2, 1);
		this.add(widthTextField,  2, 2);
		this.add(heightTextField, 2, 3);
		this.add(weightTextField, 2, 4);

		// Add labels for measurement units
		this.add(new Label("mm"), 3, 1);
		this.add(new Label("mm"), 3, 2);
		this.add(new Label("mm"), 3, 3);
		this.add(new Label("g"), 3, 4);

		// Add shipping cost calculation line
		this.add(new Label("Shipping Costs: "), 1, 5);
		this.add(shippingCostLabel, 2, 5);
		this.add(calcButton, 3, 5);

		// Set action listener for the calculate button
		calcButton.setOnAction(ae -> { calcShippingCosts(); });
	}
}
