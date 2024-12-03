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

    /**
     * Converts the given length value from the current unit to the target unit.
     *
     * @param length the length value to convert
     * @param toUnit the target measurement unit
     * @return the converted length value
     */
    public double convertLength(double length, MeasurementUnit toUnit) {
        // Check if the unit is already in the target unit
        if (this == toUnit) {
            return length;
        }

        // Perform the conversion
        switch (this) {
            case METRIC:
                if (toUnit == IMPERIAL) {
                    return length / 25.4; // mm to inches
                }
                break;
            case IMPERIAL:
                if (toUnit == METRIC) {
                    return length * 25.4; // inches to mm
                }
                break;
        }

        throw new IllegalArgumentException("Unsupported conversion from " + this + " to " + toUnit);
    }

    /**
     * Converts the given weight value from the current unit to the target unit.
     *
     * @param weight the weight value to convert
     * @param toUnit the target measurement unit
     * @return the converted weight value
     */
    public double convertWeight(double weight, MeasurementUnit toUnit) {
        // Check if the unit is already in the target unit
        if (this == toUnit) {
            return weight;
        }

        // Perform the conversion
        switch (this) {
            case METRIC:
                if (toUnit == IMPERIAL) {
                    return weight / 453.592; // grams to pounds
                }
                break;
            case IMPERIAL:
                if (toUnit == METRIC) {
                    return weight * 453.592; // pounds to grams
                }
                break;
        }

        throw new IllegalArgumentException("Unsupported conversion from " + this + " to " + toUnit);
    }
}
