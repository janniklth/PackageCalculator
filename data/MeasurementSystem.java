package data;


public enum MeasurementSystem {
    METRIC("Metric", "mm", "g"),
    IMPERIAL("Imperial", "in", "lb");

    private final String name;
    private final String lengthUnitSymbol;
    private final String weightUnitSymbol;
    private final String displayString;

    /**
     * Constructs a new MeasurementUnit with the given name, length unit symbol, and weight unit symbol.
     *
     * @param name the name of the measurement unit
     * @param lengthUnitSymbol the symbol for the length unit
     * @param weightUnitSymbol the symbol for the weight unit
     */
    MeasurementSystem(String name, String lengthUnitSymbol, String weightUnitSymbol) {
        this.name = name;
        this.lengthUnitSymbol = lengthUnitSymbol;
        this.weightUnitSymbol = weightUnitSymbol;
        this.displayString = name + " (" + lengthUnitSymbol + " + " + weightUnitSymbol + ")";
    }

    /**
     * Retrieves the length unit symbol for this measurement unit.
     *
     * @return the length unit symbol
     */
    public String getLengthUnitSymbol() {
        return lengthUnitSymbol;
    }

    /**
     * Retrieves the weight unit symbol for this measurement unit.
     *
     * @return the weight unit symbol
     */
    public String getWeightUnitSymbol() {
        return weightUnitSymbol;
    }

    /**
     * Retrieves a user-friendly display string for the measurement unit.
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
    public static MeasurementSystem fromDisplayString(String displayString) {
        for (MeasurementSystem unit : values()) {
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
     * @param exact whether to round the result to two decimal places
     * @return the converted length value
     */
    public double convertLength(double length, MeasurementSystem toUnit, boolean exact) {
        double convertedLength = 0.0;

        // Check if the unit is already in the target unit
        if (this == toUnit) {
            convertedLength = length;
        }

        // Perform the conversion
        switch (this) {
            default:
                throw new IllegalArgumentException("Unsupported conversion from " + this + " to " + toUnit);
            case METRIC:
                if (toUnit == IMPERIAL) {
                    convertedLength = length / 25.4; // mm to inches
                }
                break;
            case IMPERIAL:
                if (toUnit == METRIC) {
                    convertedLength = length * 25.4; // inches to mm
                }
                break;
        }

        // Round the result to two decimal places if needed
        if (exact) {
            return convertedLength;
        } else {
            return Math.round(convertedLength * 100.0) / 100.0;
        }
    }

    /**
     * Converts the given weight value from the current unit to the target unit.
     *
     * @param weight the weight value to convert
     * @param toUnit the target measurement unit
     * @param exact whether to return the exact conversion or round to two decimal places
     * @return the converted weight value
     */
    public double convertWeight(double weight, MeasurementSystem toUnit, boolean exact) {
        double convertedWeight = 0.0;

        // Check if the unit is already in the target unit
        if (this == toUnit) {
            convertedWeight = weight;
        }

        // Perform the conversion
        switch (this) {
            default:
                throw new IllegalArgumentException("Unsupported conversion from " + this + " to " + toUnit);
            case METRIC:
                if (toUnit == IMPERIAL) {
                    convertedWeight = weight / 453.592; // grams to pounds
                }
                break;
            case IMPERIAL:
                if (toUnit == METRIC) {
                    convertedWeight = weight * 453.592; // pounds to grams
                }
                break;
        }

        // Round the result to two decimal places if needed
        if (exact) {
            return convertedWeight;
        } else {
            return Math.round(convertedWeight * 100.0) / 100.0;
        }
    }
}