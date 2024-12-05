package gui;

import control.MessageHandler;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The PackageCalculator class serves as the main entry point for the application. It is responsible for initializing
 * and configuring the layout and functionality of the user interface components, managing the application window, and
 * setting up the primary stage for this JavaFX application.
 *
 * <p>This class utilizes a singleton pattern to ensure only one instance exists and can be accessed globally within
 * the application.</p>
 *
 * @see javafx.application.Application
 */
public class PackageCalculator extends Application {

	/**
	 * The name of the application, displayed as the title of the main window.
	 */
	public final static String APPNAME = "PackageCalculator";

	// Singleton instance of PackageCalculator
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
	 * The MessagesArea class represents a section of the user interface that displays a list of messages or logs.
	 */
	public MessagesArea messagesArea = new MessagesArea();

	/**
	 * The ToolbarArea class represents a section of the user interface that contains various tools and actions that can
	 * be performed.
	 */
	public ToolbarArea toolbarArea = new ToolbarArea();

	/**
	 * The CalculatorArea class represents a section of the user interface where calculations can be performed.
	 */
	public CalculatorArea calculatorArea = new CalculatorArea();

	/**
	 * The InspectorArea class represents a section of the user interface where details or properties of a selected item can
	 * be inspected and modified.
	 */
	public CostsOverviewArea costsOverviewArea = new CostsOverviewArea();


	// Primary stage (window) for the application
	private Stage primaryStage;

	/**
	 * Retrieves the primary stage, which serves as the main window for the application.
	 *
	 * @return The primary {@link Stage} instance.
	 */
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	/**
	 * Sets up the primary layout and initializes the user interface with various sections, including toolbar, explorer,
	 * editor, messages, and status areas.
	 *
	 * @param primaryStage The primary stage provided by the JavaFX runtime.
	 */
	@Override
	public void start(Stage primaryStage) {
		// Initialize singleton instance
		PackageCalculator.instance = this;

		// Store the primary stage reference
		this.primaryStage = primaryStage;

		// Create a SplitPane for calculator and inspector sections
		SplitPane lr2SplitPane = new SplitPane();
		calculatorArea.setMaxWidth(320);
		calculatorArea.setMinWidth(320);
		lr2SplitPane.getItems().addAll(calculatorArea, costsOverviewArea);

		// Create a vertical SplitPane for editor/inspector and messages areas
		SplitPane tdSplitPane = new SplitPane();
		tdSplitPane.setOrientation(Orientation.VERTICAL);
		tdSplitPane.getItems().addAll(lr2SplitPane, messagesArea.getMessagesListView());
		tdSplitPane.setDividerPositions(0.8f, 0.2f);

		// Set up the main layout, including toolbar, main split pane, and status area
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(toolbarArea);
		mainPane.setCenter(tdSplitPane);

		// Configure the scene and set the window size to the screen's visual bounds
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(mainPane, 800, 500, true);
		primaryStage.setTitle(APPNAME);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// Flush pending messages to the MessagesArea
		MessageHandler.flushPendingMessages();
	}

	/**
	 * The main method to launch the JavaFX application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
