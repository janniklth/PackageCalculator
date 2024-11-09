package control;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Helper {

    public static void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
