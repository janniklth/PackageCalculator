package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The InfoDialog class represents a modal dialog for displaying application information.
 */
public class InfoDialog {

    /**
     * Displays the information dialog with application details.
     */
    public static void show() {
        Stage infoDialog = new Stage();
        infoDialog.initModality(Modality.APPLICATION_MODAL);
        infoDialog.setTitle("Application Info");

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(new Text("Package Calculator v0.3\n(c) 2020 I. Bogicevic J. Loth"));

        Scene dialogScene = new Scene(vbox, 400, 200);
        infoDialog.setScene(dialogScene);
        infoDialog.showAndWait();
    }
}
