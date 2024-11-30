package data;

public enum MeasurementUnit {
    METRIC("Metric (mm + g)"),
    IMPERIAL("Imperial (inches + lb)");

    private final String displayString;

    MeasurementUnit(String displayString) {
        this.displayString = displayString;
    }

    /**
     * Returns a user-friendly display string for the measurement unit.
     *
     * @return the display string
     */
    public String convertToDisplayString() {
        return displayString;
    }

    /**
     * Finds a MeasurementUnit by its display string.
     *
     * @param displayString the display string to search for
     * @return the corresponding MeasurementUnit
     * @throws IllegalArgumentException if no match is found
     */
    public static MeasurementUnit fromDisplayString(String displayString) {
        for (MeasurementUnit unit : values()) {
            if (unit.displayString.equals(displayString)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid display string: " + displayString);
    }
}
