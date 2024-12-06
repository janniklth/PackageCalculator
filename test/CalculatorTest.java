package test;

import static org.junit.jupiter.api.Assertions.*;

import control.Calculator;
import data.Packet;
import exceptions.ShippingRuleException;
import org.junit.jupiter.api.Test;

/**
 * Provides test cases for the {@link Calculator} class.
 *
 * <p>Tests the shipping cost calculation for parcels based on the defined default shipping rules. Ensures that the
 * correct
 * shipping cost is calculated for parcels of different sizes and weights and that exceptions are thrown for oversized
 * parcels and parcels with invalid dimensions or weight.</p>
 *
 * <p>Contains the following test cases:</p>
 * <ul>
 *     <li> {@link #testSmallParcelRate()} - Tests that a small parcel with dimensions up to 30x30x15 cm and up to 1 kg
 *     is correctly calculated to cost 3.89 EUR. </li>
 *     <li> {@link #testMediumParcelRate()} - Tests that a medium parcel with dimensions up to 60x30x15 cm and up to
 *     2 kg
 *     is correctly calculated to cost 4.39 EUR. </li>
 *     <li> {@link #testLargeParcelRate()} - Tests that a large parcel with dimensions up to 120x60x60 cm, girth up
 *     to 300 cm,
 *     and up to 5 kg is correctly calculated to cost 5.89 EUR. </li>
 *     <li> {@link #testXLParcelRate()} - Tests that an extra-large parcel with dimensions up to 120x60x60 cm, girth
 *     up to 300 cm,
 *     and up to 10 kg is correctly calculated to cost 7.99 EUR. </li>
 *     <li> {@link #testXXLParcelRate()} - Tests that an extra-extra-large parcel with dimensions up to 120x60x60 cm
 *     and a weight up to 31 kg is correctly calculated to cost 14.99 EUR. </li>
 *     <li> {@link #testRotatedParcelRate()} - Tests that a parcel that has to be rotated to fit the dimensions is
 *     priced correctly. </li>
 *     <li> {@link #testSmallHeavyParcelRate()} - Tests that a small parcel with a heavy weight is priced correctly.
 *     </li>
 *     <li> {@link #testExceedMaxWeight()} - Tests that a parcel exceeding the maximum weight of 31 kg throws an
 *     {@link IllegalArgumentException}. </li>
 *     <li> {@link #testExceedMaxDimensions()} - Tests that a parcel exceeding the maximum dimensions of 120x60x60 cm
 *     throws an {@link IllegalArgumentException}. </li>
 *     <li> {@link #testAllZeroDimensions()} - Tests that a parcel with all dimensions set to zero throws an
 *     {@link IllegalArgumentException}. </li>
 *     <li> {@link #testOneZeroDimension()} - Tests that a parcel with only one zero dimension throws an
 *     {@link IllegalArgumentException}. </li>
 *     <li> {@link #testNegativeDimensions()} - Tests that a parcel with negative dimensions throws an
 *     {@link IllegalArgumentException}. </li>
 *     <li> {@link #testNegativeWeight()} - Tests that a parcel with negative weight throws an
 *     {@link IllegalArgumentException}. </li>
 *     <li> {@link #testNullPacket()} - Tests that passing a null packet to the calculator throws an
 *     {@link IllegalArgumentException}. </li>
 *     <li> {@link #testExceedMaxGirth()} - Tests that a parcel exceeding the girth limit is priced correctly. </li>
 *     <li> {@link #testOrientationsWidth()} - Tests that a parcel which would normally not fit due to it's width is
 *     priced correctly (due to trying all orientations). </li>
 *     <li> {@link #testOrientationsHeight()} - Tests that a parcel which would normally not fit due to it's height is
 *     priced correctly (due to trying all orientations). </li>
 *     <li> {@link #testFloatingPointPrecisionForWeight()} - Tests calculation precision for a weight very close to
 *     the 10 kg boundary. </li>
 *     <li> {@link #testNearLimitCombination()} - Tests a parcel with near-limit dimensions and weight to ensure
 *     correct rule selection. </li>
 *     <li> {@link #testMaxIntegerDimensions()} - Tests that a parcel with maximum integer dimensions and weight does
 *     not crash the program and throws an appropriate {@link IllegalArgumentException}. </li>
 * </ul>
 *
 * @see Calculator
 * @see Packet
 * @see ShippingRuleException
 * @see IllegalArgumentException
 */

public class CalculatorTest {
    /**
     * Tests that a small parcel with dimensions up to 30x30x15 cm and up to 1 kg
     * is correctly calculated to cost 3.89 EUR.
     */
    @Test
    public void testSmallParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(300, 300, 150, 1000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(3.89, result, "A parcel up to 30x30x15 cm and up to 1 kg should cost 3.89 EUR.");
    }

    /**
     * Tests that a medium parcel with dimensions up to 60x30x15 cm and up to 2 kg
     * is correctly calculated to cost 4.39 EUR.
     */
    @Test
    public void testMediumParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(600, 300, 150, 2000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(4.39, result, "A parcel up to 60x30x15 cm and up to 2 kg should cost 4.39 EUR.");
    }

    /**
     * Tests that a large parcel with dimensions up to 120x60x60 cm, a girth of up to 300 cm,
     * and a weight up to 5 kg is correctly calculated to cost 5.89 EUR.
     */
    @Test
    public void testLargeParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(1000, 500, 500, 5000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(5.89, result, "A parcel up to 120x60x60 cm, girth <= 300 cm, and up to 5 kg should cost 5.89 EUR" +
                ".");
    }

    /**
     * Tests that an extra-large parcel with dimensions up to 120x60x60 cm, a girth of up to 300 cm,
     * and a weight up to 10 kg is correctly calculated to cost 7.99 EUR.
     */
    @Test
    public void testXLParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(1000, 500, 500, 10000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(7.99, result, "A parcel up to 120x60x60 cm, girth <= 300 cm, and up to 10 kg should cost 7.99 " +
                "EUR.");
    }

    /**
     * Tests that an extra-extra-large parcel with dimensions up to 120x60x60 cm
     * and a weight up to 31 kg is correctly calculated to cost 14.99 EUR.
     */
    @Test
    public void testXXLParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(1200, 600, 600, 31000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "A parcel up to 120x60x60 cm and up to 31 kg should cost 14.99 EUR.");
    }

    /**
     * Tests that a parcel that has to be rotated to fit the dimensions is priced correctly.
     */
    @Test
    public void testRotatedParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(600, 1200, 600, 5000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "Rotated parcel should cost 14.99 EUR.");
    }

    /**
     * Tests that  small heavy parcel is priced correctly.
     */
    @Test
    public void testSmallHeavyParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(300, 300, 150, 29000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "A small parcel up to 1 kg should cost 3.89 EUR.");
    }

    /**
     * Tests that a parcel exceeding the maximum weight of 31 kg throws an IllegalArgumentException.
     */
    @Test
    public void testExceedMaxWeight() {
        Packet packet = new Packet(1200, 600, 600, 32000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("No suitable shipping rule found for the given package dimensions and weight.",
                exception.getMessage());
    }

    /**
     * Tests that a parcel exceeding the maximum dimensions of 120x60x60 cm throws an IllegalArgumentException.
     */
    @Test
    public void testExceedMaxDimensions() {
        Packet packet = new Packet(1300, 700, 700, 10000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("No suitable shipping rule found for the given package dimensions and weight.",
                exception.getMessage());
    }

    /**
     * Tests that a parcel with no dimension throws an IllegalArgumentException.
     */
    @Test
    public void testAllZeroDimensions() {
        Packet packet = new Packet(0, 0, 0, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertTrue(exception.getMessage().contains("Invalid package parameters"),
                "Exception message should indicate invalid package dimensions or weight.");
    }

    // TODO: Maybe add a test for every dimension being 0?

    /**
     * Tests that a parcel with only one null dimension throws an IllegalArgumentException.
     */
    @Test
    public void testOneZeroDimension() {
        Packet packet = new Packet(0, 300, 150, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package parameters: - Length must be greater than 0.", exception.getMessage());
    }

    /**
     * Tests that a parcel with negative dimensions throws an IllegalArgumentException.
     */
    @Test
    public void testNegativeDimensions() {
        Packet packet = new Packet(-300, 300, 150, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package parameters: - Length must be greater than 0.", exception.getMessage());
    }

    /**
     * Tests that a parcel with negative weight throws an IllegalArgumentException.
     */
    @Test
    public void testNegativeWeight() {
        Packet packet = new Packet(300, 300, 150, -1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package parameters: - Weight must be greater than 0.", exception.getMessage());
    }

    /**
     * Tests that a Null parcel throws an IllegalArgumentException.
     */
    @Test
    public void testNullPacket() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(null);
        });
        assertEquals("Package is null. Please provide valid package dimensions and weight.", exception.getMessage());
    }

    /**
     * Tests that a parcel exceeding the girth limit is priced correctly.
     */
    @Test
    public void testExceedMaxGirth() throws ShippingRuleException {
        Packet packet = new Packet(1200, 600, 600, 5000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "Parcel exceeding girth limit should cost 14.99 EUR.");
    }

    /**
     * Tests that a parcel which would normally not fit (width), is priced correctly due to trying all orientations.
     */
    @Test
    void testOrientationsWidth() throws ShippingRuleException {
        Packet packet = new Packet(600, 1200, 600, 5000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "Rotated parcel should cost 14.99 EUR.");
    }

    /**
     * Tests that a parcel which would normally not fit (height), is priced correctly due to trying all orientations.
     */
    @Test
    void testOrientationsHeight() throws ShippingRuleException {
        Packet packet = new Packet(600, 600, 1200, 5000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "Rotated parcel should cost 14.99 EUR.");
    }

    /**
     * Tests calculation precision for a weight that is very close to 10 kg boundary.
     */
    @Test
    public void testFloatingPointPrecisionForWeight() throws ShippingRuleException {
        Packet packet = new Packet(100, 100, 100, 9999); // Very close to 10 kg
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(7.99, result, "Parcel very close to 10 kg boundary should cost 5.89 EUR.");
    }

    /**
     * Tests a parcel with near-limit dimensions and weight to ensure correct rule selection.
     */
    @Test
    public void testNearLimitCombination() throws ShippingRuleException {
        Packet packet = new Packet(299, 299, 150, 999); // Just below small parcel limits
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(3.89, result, "Parcel just below the small parcel limit should cost 3.89 EUR.");
    }

    /**
     * Tests that a parcel with maximum integer dimensions and weight does not crash the program.
     */
    @Test
    public void testMaxIntegerDimensions() {
        Packet packet = new Packet(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("No suitable shipping rule found for the given package dimensions and weight.",
                exception.getMessage());
    }
}

