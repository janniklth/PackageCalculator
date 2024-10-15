package gui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The PackageCalculator class is the main entry point for the application. It is responsible for initializing and
 * setting up the user interface components, managing the application layout, and handling the main stage for the JavaFX
 * application.
 */
public class PackageCalculator extends Application {

	/**
	 * The name of the application, used as a title for the window.
	 */
	public final static String APPNAME = "PackageCalculator";

	// Singleton instance of the PackageCalculator class
	private static PackageCalculator instance;

	/**
	 * Returns the singleton instance of the PackageCalculator class.
	 * TODO: Make it a real singleton.
	 *
	 * @return The singleton instance of PackageCalculator.
	 */
	public static PackageCalculator getInstance() {
		return instance;
	}

	// - - - - - - GUI areas for different parts of the application - - - - - -

	/**
	 * The ToolbarArea class represents a section of the user interface that contains various tools and actions that can
	 * be performed.
	 */
	public ToolbarArea toolbarArea = new ToolbarArea();

	/**
	 * The ExplorerArea class represents a section of the user interface that displays a tree view of the project structure.
	 */
	public ExplorerArea explorerArea = new ExplorerArea();

	/**
	 * The CalculatorArea class represents a section of the user interface where calculations can be performed.
	 */
	public CalculatorArea editorArea = new CalculatorArea();

	/**
	 * The InspectorArea class represents a section of the user interface where details or properties of a selected item can
	 * be inspected and modified.
	 */
	public InspectorArea inspectorArea = new InspectorArea();

	/**
	 * The MessagesArea class represents a section of the user interface that displays a list of messages or logs.
	 */
	public MessagesArea messagesArea = new MessagesArea();

	/**
	 * The StatusArea class represents a section of the user interface that displays status information.
	 */
	public StatusArea statusArea = new StatusArea();


	/**
	 * The root path of the current project.
	 */
	public String rootPath;

	// Primary stage (window) for the application
	private Stage primaryStage;

	/**
	 * Gets the primary stage of the application, which is the main window.
	 *
	 * @return The primary stage of the application.
	 */
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	/**
	 * This is the main method of the application that sets up the layout of the various areas such as the toolbar,
	 * explorer, editor, messages, and status areas. It also handles the window and its content initialization.
	 *
	 * @param primaryStage The primary stage provided by the JavaFX runtime.
	 */
	@Override
	public void start(Stage primaryStage) {
		// Set singleton instance
		PackageCalculator.instance = this;

		// Remember the primary stage
		this.primaryStage = primaryStage;

		// SplitPane for editor and inspector
		SplitPane lr2SplitPane = new SplitPane();
		lr2SplitPane.getItems().addAll(editorArea, inspectorArea);
		lr2SplitPane.setDividerPositions(0.8f, 0.2f);

		// SplitPane for editor/inspector and messages
		SplitPane tdSplitPane = new SplitPane();
		tdSplitPane.setOrientation(Orientation.VERTICAL);
		tdSplitPane.getItems().addAll(lr2SplitPane, messagesArea);
		tdSplitPane.setDividerPositions(0.9f, 0.1f);

		// SplitPane for explorer and editor/inspector/messages
		SplitPane lrSplitPane = new SplitPane();
		lrSplitPane.getItems().addAll(explorerArea, tdSplitPane);
		lrSplitPane.setDividerPositions(0.2f, 0.8f);

		// Main layout with toolbar, split areas, and status area
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(toolbarArea);
		mainPane.setCenter(lrSplitPane);
		mainPane.setBottom(statusArea);

		// Setup and show the scene
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(mainPane, screenBounds.getWidth(), screenBounds.getHeight(), true);
		primaryStage.setTitle(APPNAME);
		primaryStage.setScene(scene);
		primaryStage.show();

		// Optionally load a default workspace
		// ProjectHandling.openProject("/Users/...");
	}

	/**
	 * The main method that launches the JavaFX application.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
