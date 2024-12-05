package control;

import data.ErrorDisplayState;
import gui.PackageCalculator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;

/**
 * The MessageHandler class provides utility methods for displaying messages and alerts.
 *
 * <p>Includes methods for logging messages to the MessagesArea and displaying popups.</p>
 * @see Alert
 * @see AlertType
 * @see ErrorDisplayState
 */
public class MessageHandler {
    // List to store pending messages
    private static final List<String> pendingMessages = new ArrayList<>();

    /**
     * Logs a message to the MessagesArea.
     *
     * @param title the title of the message
     * @param message the message content to log
     */
    private static void logMessage(String title, String message) {
        String fullMessage = title + " - " + message;
        PackageCalculator calculator = PackageCalculator.getInstance();

        if (calculator != null && calculator.messagesArea != null) {
            calculator.messagesArea.setMessage(fullMessage);
        } else {
            pendingMessages.add(fullMessage);
        }
    }

    /**
     * Displays an alert dialog (popup) with the specified type, title, and message.
     *
     * @param alertType the type of alert to display (e.g., INFORMATION, ERROR)
     * @param title the title of the alert dialog
     * @param message the message content of the alert
     */
    private static void showPopup(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Flushes all pending messages to the MessagesArea.
     */
    public static void flushPendingMessages() {
        PackageCalculator calculator = PackageCalculator.getInstance();
        if (calculator != null && calculator.messagesArea != null) {
            for (String message : pendingMessages) {
                calculator.messagesArea.setMessage(message);
            }
            MessageHandler.pendingMessages.clear();
        }
    }

    /**
     * Displays an error message according to the current error display state.
     *
     * <p>Depending on the state, the message will be logged, displayed as a popup,
     * or both.</p>
     *
     * @param alertType the type of alert (e.g., ERROR)
     * @param title the title of the message
     * @param message the message content
     */
    public static void handleMessage(AlertType alertType, String title, String message) {
        // Log the message in the MessagesArea
        logMessage(title, message);

        // Display a popup if the state requires it
        ErrorDisplayState errorDisplayState = SettingsManager.getErrorDisplayState();
        if (errorDisplayState == ErrorDisplayState.POPUP_AND_LOG) {
            showPopup(alertType, title, message);
        }
    }
}