package data;



/*
 * The ErrorDisplayState enum represents the different states in which error messages can be displayed.
 * The available states are:
 * - POPUP_AND_LOG: Display error messages in a popup and log area
 * - LOG_ONLY: Display error messages only in the log area
 */
public enum ErrorDisplayState {
    POPUP_AND_LOG("Popup + log messages"),
    LOG_ONLY("only log messages");

    private final String displayString;

    ErrorDisplayState(String displayString) {
        this.displayString = displayString;
    }

    /**
     * Returns a user-friendly display string for the error display state.
     *
     * @return the display string
     */
    public String convertToDisplayString() {
        return displayString;
    }

    /**
     * Finds an ErrorDisplayState by its display string.
     *
     * @param displayString the display string to search for
     * @return the corresponding ErrorDisplayState
     * @throws IllegalArgumentException if no match is found
     */
    public static ErrorDisplayState fromDisplayString(String displayString) {
        for (ErrorDisplayState state : values()) {
            if (state.displayString.equals(displayString)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Invalid display string: " + displayString);
    }
}