package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

/**
 * The ToolbarArea class represents the toolbar of the application.
 */
public class ToolbarArea extends ToolBar {

	/**
	 * Constructor for the ToolbarArea. Initializes the buttons and their associated action listeners.
	 */
	public ToolbarArea() {
		// Initialize buttons
		Button settingsButton = new Button("Settings");
		Button infoButton = new Button("Info");
		Button exitButton = new Button("Exit");

		// Set action listeners for buttons
		settingsButton.setOnAction(e -> SettingsDialog.show());
		infoButton.setOnAction(e -> InfoDialog.show());
		exitButton.setOnAction(e -> System.exit(0));

		// Add buttons to the toolbar
		this.getItems().add(settingsButton);
		this.getItems().add(infoButton);
		this.getItems().add(exitButton);
	}
}
