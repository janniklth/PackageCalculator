package control;

import gui.MessagesArea;
import gui.PackageCalculator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The Helper class provides utility methods to support various tasks across the application.
 *
 * <p>This class includes methods for creating alerts and can be extended with additional utility
 * methods as needed. It is primarily used to streamline repetitive tasks and centralize utility code.</p>
 *
 * @see javafx.scene.control.Alert
 */
public class Helper {

    /**
     * Displays an alert dialog with the specified alert type, title, and message.
     *
     * This method creates and shows an {@link Alert} with the given parameters, allowing
     * the application to communicate important information or errors to the user.
     *
     * @param alertType the type of alert to display (e.g., INFORMATION, ERROR)
     * @param title the title of the alert dialog
     * @param message the message content of the alert
     */
    public static void showAlert(AlertType alertType, String title, String message) {
        // show message in messages area
        PackageCalculator.getInstance().messagesArea.setMessage("Error: " + title + " - " + message);

        // create and show alert dialog
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
