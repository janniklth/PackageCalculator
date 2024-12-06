package test;

import data.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides test cases for the {@link Currency} enum.
 *
 * <p>Tests methods for converting amounts to and from Euro and for finding a {@link Currency} by its display string
 * .</p>
 *
 * <p>Contains the following test cases:</p>
 * <ul>
 *     <li>{@link #testConvertToDisplayString()} - Tests the generation of the display string</li>
 *     <li>{@link #testFromDisplayString()} - Tests finding a currency by its display string</li>
 *     <li>{@link #testConvertToEuro()} - Tests conversion of amounts to Euro</li>
 *     <li>{@link #testConvertFromEuro()} - Tests conversion of amounts from Euro</li>
 *     <li>{@link #testInvalidDisplayString()} - Tests handling of invalid display strings</li>
 * </ul>
 *
 * @see Currency
 */
public class CurrencyTest {

    /**
     * Tests the generation of the display string.
     */
    @Test
    public void testConvertToDisplayString() {
        assertEquals("Euro (€)", Currency.EURO.convertToDisplayString(), "Euro display string should be 'Euro (€)'");
        assertEquals("US Dollar ($)", Currency.US_DOLLAR.convertToDisplayString(), "US Dollar display string should " +
                "be 'US Dollar ($)'");
        assertEquals("British Pound (£)", Currency.BRITISH_POUND.convertToDisplayString(), "British Pound display " +
                "string should be 'British Pound (£)'");
    }

    /**
     * Tests finding a currency by its display string.
     */
    @Test
    public void testFromDisplayString() {
        assertEquals(Currency.EURO, Currency.fromDisplayString("Euro (€)"), "Should return EURO for 'Euro (€)'");
        assertEquals(Currency.US_DOLLAR, Currency.fromDisplayString("US Dollar ($)"), "Should return US_DOLLAR for " +
                "'US Dollar ($)'");
        assertEquals(Currency.BRITISH_POUND, Currency.fromDisplayString("British Pound (£)"), "Should return " +
                "BRITISH_POUND for 'British Pound (£)'");
    }

    /**
     * Tests the conversion of amounts to Euro.
     */
    @Test
    public void testConvertToEuro() {
        assertEquals(100.0, Currency.EURO.convertToEuro(100), "100 Euro should equal 100 Euro");
        assertEquals(93.46, Currency.US_DOLLAR.convertToEuro(100), 0.01, "100 US Dollar should equal 93.46 Euro");
        assertEquals(119.05, Currency.BRITISH_POUND.convertToEuro(100), 0.01, "100 British Pounds should equal 119.05" +
                " Euro");
    }

    /**
     * Tests the conversion of amounts from Euro.
     */
    @Test
    public void testConvertFromEuro() {
        assertEquals(100.0, Currency.EURO.convertFromEuro(100), "100 Euro should equal 100 Euro");
        assertEquals(107.0, Currency.US_DOLLAR.convertFromEuro(100), 0.01, "100 Euro should equal 107 US Dollar");
        assertEquals(84.0, Currency.BRITISH_POUND.convertFromEuro(100), 0.01, "100 Euro should equal 84 British " +
                "Pounds");
    }

    /**
     * Tests handling of invalid display strings.
     */
    @Test
    public void testInvalidDisplayString() {
        assertThrows(IllegalArgumentException.class, () -> Currency.fromDisplayString("Invalid"), "Should throw " +
                "exception for invalid display string");
    }
}
