package data;


/**
 * The ErrorDisplayState enum represents different states for displaying error messages and provides methods to get the
 * display string and find a state by its display string.
 *
 * <p>The following states are available:</p>
 * <ul>
 *     <li>{@link data.ErrorDisplayState#POPUP_AND_LOG} - Display error messages in a popup and log area</li>
 *     <li>{@link data.ErrorDisplayState#LOG_ONLY} - Display error messages only in the log area</li>
 * </ul>
 */
public enum ErrorDisplayState {
    /**
     * Display error messages in a popup and log area
     */
    POPUP_AND_LOG("Popup + log messages"),

    /**
     * Display error messages only in the log area
     */
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