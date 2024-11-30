package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
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
		Button loadCustomJsonButton = new Button("Load custom shipping rules");
		Button settingsButton = new Button("Settings");
		Button infoButton = new Button("Info");

		// Set action listeners for buttons
		loadCustomJsonButton.setOnAction(e -> System.out.println("Load custom shipping rules clicked."));
		settingsButton.setOnAction(e -> SettingsDialog.show());
		infoButton.setOnAction(e -> InfoDialog.show());

		// Add buttons to the toolbar
		this.getItems().add(loadCustomJsonButton);
		this.getItems().add(new Separator());
		this.getItems().add(settingsButton);
		this.getItems().add(infoButton);
	}
}
