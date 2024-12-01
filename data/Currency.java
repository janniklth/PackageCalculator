package data;

public enum Currency {
    EURO("Euro", "€", 1.0),
    US_DOLLAR("US Dollar", "$", 1.18),
    BRITISH_POUND("British Pound", "£", 0.86);

    private final String name;
    private final String symbol;
    private final double exchangeRateToEuro;

    Currency(String name, String symbol, double exchangeRateToEuro) {
        this.name = name;
        this.symbol = symbol;
        this.exchangeRateToEuro = exchangeRateToEuro;
    }

    /**
     * Returns a user-friendly display string for the currency.
     *
     * @return the display string
     */
    public String convertToDisplayString() {
        return name + " (" + symbol + ")";
    }

    /**
     * Finds a Currency by its display string.
     *
     * @param displayString the display string to search for
     * @return the corresponding Currency
     * @throws IllegalArgumentException if no match is found
     */
    public static Currency fromDisplayString(String displayString) {
        for (Currency currency : values()) {
            if (currency.convertToDisplayString().equals(displayString)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Invalid display string: " + displayString);
    }
}
