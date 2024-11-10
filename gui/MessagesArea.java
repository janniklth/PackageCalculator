package gui;

import javafx.scene.control.ListView;

/**
 * The MessagesArea class provides a user interface component for displaying a series of messages or log entries.
 * It extends {@link ListView} to facilitate message management, including adding, viewing, and potentially filtering
 * messages in a list format.
 *
 * @see javafx.scene.control.ListView
 */
public class MessagesArea extends ListView<String> {

    /**
     * Constructs a MessagesArea instance, initializing the list view to display messages.
     */
    public MessagesArea() {
        // TODO: Implement constructor and functionality for adding and managing messages
    }

    /**
     * Adds a new message to the list of messages.
     * @param text the message to be added
     */
    public void setMessage(String text) {
        this.getItems().add(text);
    }

    /**
     * Retrieves the last message from the list.
     * @return the last message in the list
     */
    public String getLastMessage() {
        return this.getItems().getLast();
    }

    /**
     * Clears all messages from the list.
     */
    public void clearMessages() {
        this.getItems().clear();
    }
}
