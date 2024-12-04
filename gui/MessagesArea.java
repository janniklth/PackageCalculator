package gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The MessagesArea class provides a user interface component for displaying a series of messages or log entries.
 * It extends {@link ListView} to facilitate message management, including adding, viewing, and potentially filtering
 * messages in a list format.
 *
 * @see javafx.scene.control.ListView
 * @see javafx.scene.layout.HBox
 * @see javafx.scene.layout.Priority
 * @see javafx.scene.layout.Region
 * @see javafx.scene.control.Label
 * @see javafx.collections.FXCollections
 * @see javafx.application.Platform
 * @see java.time.LocalTime
 * @see java.time.format.DateTimeFormatter
 */
public class MessagesArea {
    private final ListView<HBox> messagesListView = new ListView<>();
    private final ObservableList<HBox> messagesList = FXCollections.observableArrayList();

    /**
     * Constructs a new MessagesArea instance.
     */
    public MessagesArea() {
        messagesListView.setItems(messagesList);
    }

    /**
     * Adds a new message entry to the list of messages and scrolls to the bottom of the list.
     *
     * @param message the message to add to the list
     */
    public void setMessage(String message) {
        HBox newMessage = createMessageEntry(message);
        messagesList.add(newMessage);

        // Scroll to the bottom of the ListView
        Platform.runLater(() -> messagesListView.scrollTo(messagesList.size() - 1));
    }

    /**
     * Returns the ListView containing the messages.
     *
     * @return the ListView containing the messages
     * @see ListView
     * @see HBox
     */
    public ListView<HBox> getMessagesListView() {
        return messagesListView;
    }

    /**
     * Creates a new HBox containing a message entry with the specified message and timestamp that can be added to the
     * list of messages.
     *
     * <p>The message entry is formatted with the message on the left, a spacer in the middle, and the timestamp on the
     * right.</p>
     *
     * @param message the message to display in the entry
     * @return a new HBox containing the message entry
     * @see HBox
     * @see Label
     * @see Region
     * @see Priority
     */
    private HBox createMessageEntry(String message) {
        Label messageLabel = new Label(message);
        Label timestampLabel = new Label(getCurrentTime());
        timestampLabel.setStyle("-fx-text-fill: gray;");

        // Create a spacer Region to push the timestamp to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Assemble the HBox with the message, spacer, and timestamp
        HBox container = new HBox(10, messageLabel, spacer, timestampLabel);
        container.setAlignment(Pos.CENTER_LEFT);

        return container;
    }

    /**
     * Returns the current local time as a formatted string (HH:mm:ss).
     *
     * @return the current local time as a formatted string
     * @see LocalTime
     * @see DateTimeFormatter
     */
    private String getCurrentTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return now.format(formatter);
    }
}