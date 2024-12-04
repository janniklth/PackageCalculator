package data;

/**
 * The Currency enum represents different currencies and provides methods for converting between them.
 *
 * <p>Includes methods for converting an amount from the currency to Euro and vice versa and a method to find a Currency
 * by its display string.</p>
 */
public enum Currency {
    /**
     * The Euro currency, last updated on 2024-12-01.
     */
    EURO("Euro", "€", 1.0),

    /**
     * The US Dollar currency, last updated on 2024-12-01.
     */
    US_DOLLAR("US Dollar", "$", 1.07),

    /**
     * The British Pound currency, last updated on 2024-12-01.
     */
    BRITISH_POUND("British Pound", "£", 0.84);

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
     * Returns the currency symbol.
     * @return the currency symbol as a string
     */
    public String getSymbol() {
        return symbol;
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

    /**
     * Converts an amount from this currency to Euro, rounding to two decimal places.
     *
     * @param amount the amount to convert
     * @return the amount in Euro
     */
    public double convertToEuro(double amount) {
        return Math.round(amount / exchangeRateToEuro * 100.0) / 100.0;
    }

    /**
     * Converts an amount from Euro to this currency, rounding to two decimal places.
     *
     * @param amount the amount to convert
     * @return the amount in this currency
     */
    public double convertFromEuro(double amount) {
        return Math.round(amount * exchangeRateToEuro * 100.0) / 100.0;
    }
}
