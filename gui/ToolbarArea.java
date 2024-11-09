package gui;

import control.ProjectHandling;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The ToolbarArea class represents the toolbar of the application, containing various buttons that allow the user to
 * perform key actions such as opening projects, creating new files, saving files, accessing settings, and getting info.
 * It extends the {@link ToolBar} component from JavaFX.
 *
 * @see javafx.scene.control.ToolBar
 */
public class ToolbarArea extends ToolBar {

	/**
	 * Shows an info dialog with information about the application version and authorship. This dialog is displayed in a
	 * modal window.
	 */
	private void showInfoDialog() {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Application Info");

		VBox vbox = new VBox(20);
		Text infoText = new Text("Package Calculator v0.3 \n (c) 2020 I. Bogicevic J. Loth");
		vbox.getChildren().add(infoText);

		Scene dialogScene = new Scene(vbox, 400, 200);
		dialog.setScene(dialogScene);
		dialog.showAndWait();
	}

	/**
	 * Constructor for the ToolbarArea. Initializes the buttons and their associated action listeners. Buttons
	 * include options for opening a project, creating a new file, saving a file, and accessing the info popup.
	 */
	public ToolbarArea() {
		// Initialize buttons
		Button openProjectButton = new Button("Open Project");
		Button newFileButton = new Button("New File");
		Button saveFileButton = new Button("Save File");
		Button saveFileAsButton = new Button("Save File as");
		Button settingsButton = new Button("Settings");
		Button aboutButton = new Button("About");
		Button infoButton = new Button("Info");

		// Set action listeners for buttons
		openProjectButton.setOnAction(e -> ProjectHandling.openProject());
		newFileButton.setOnAction(e -> ProjectHandling.newFile());
		infoButton.setOnAction(e -> showInfoDialog());

		// add all buttons to the toolbar
		this.getItems().add(openProjectButton);
		this.getItems().add(newFileButton);
		this.getItems().add(saveFileButton);
		this.getItems().add(saveFileAsButton);
		this.getItems().add(new Separator());
		this.getItems().add(settingsButton);
		this.getItems().add(aboutButton);
		this.getItems().add(infoButton);
	}
}
