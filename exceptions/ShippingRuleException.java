package exceptions;

/**
 * This exception is thrown when the shipping rules file could not be found, opened, or parsed correctly.
 */
public class ShippingRuleException extends Exception {
    public ShippingRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
