package test;

import static org.junit.jupiter.api.Assertions.*;

import control.Calculator;
import data.Packet;
import exceptions.ShippingRuleException;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Calculator's shipping cost calculation. Based on the default rules json.
 * Verifies correct shipping costs based on the dimensions and weight of different package types.
 * Also tests for invalid inputs to ensure defensive programming is in place.
 */
public class CalculatorTest {
    /**
     * Tests that a small parcel with dimensions up to 30x30x15 cm and up to 1 kg
     * is correctly calculated to cost 3.89 EUR.
     */
    @Test
    void testSmallParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(300, 300, 150, 1000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(3.89, result, "A parcel up to 30x30x15 cm and up to 1 kg should cost 3.89 EUR.");
    }

    /**
     * Tests that a medium parcel with dimensions up to 60x30x15 cm and up to 2 kg
     * is correctly calculated to cost 4.39 EUR.
     */
    @Test
    void testMediumParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(600, 300, 150, 2000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(4.39, result, "A parcel up to 60x30x15 cm and up to 2 kg should cost 4.39 EUR.");
    }

    /**
     * Tests that a large parcel with dimensions up to 120x60x60 cm, a girth of up to 300 cm,
     * and a weight up to 5 kg is correctly calculated to cost 5.89 EUR.
     */
    @Test
    void testLargeParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(1000, 500, 500, 5000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(5.89, result, "A parcel up to 120x60x60 cm, girth <= 300 cm, and up to 5 kg should cost 5.89 EUR.");
    }

    /**
     * Tests that an extra-large parcel with dimensions up to 120x60x60 cm, a girth of up to 300 cm,
     * and a weight up to 10 kg is correctly calculated to cost 7.99 EUR.
     */
    @Test
    void testXLParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(1000, 500, 500, 10000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(7.99, result, "A parcel up to 120x60x60 cm, girth <= 300 cm, and up to 10 kg should cost 7.99 EUR.");
    }

    /**
     * Tests that an extra-extra-large parcel with dimensions up to 120x60x60 cm
     * and a weight up to 31 kg is correctly calculated to cost 14.99 EUR.
     */
    @Test
    void testXXLParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(1200, 600, 600, 31000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "A parcel up to 120x60x60 cm and up to 31 kg should cost 14.99 EUR.");
    }

    /**
     * Test that  small heavy parcel is priced correctly.
     */
    @Test
    void testSmallHeavyParcelRate() throws ShippingRuleException {
        Packet packet = new Packet(300, 300, 150, 29000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "A small parcel up to 1 kg should cost 3.89 EUR.");
    }

    /**
     * Tests that a parcel exceeding the maximum weight of 31 kg throws an IllegalArgumentException.
     */
    @Test
    void testExceedMaxWeight() {
        Packet packet = new Packet(1200, 600, 600, 32000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package dimensions or weight, no suitable shipping rule found.", exception.getMessage());
    }

    /**
     * Tests that a parcel exceeding the maximum dimensions of 120x60x60 cm throws an IllegalArgumentException.
     */
    @Test
    void testExceedMaxDimensions() {
        Packet packet = new Packet(1300, 700, 700, 10000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package dimensions or weight, no suitable shipping rule found.", exception.getMessage());
    }

    /**
     * Tests that a parcel with no dimension throws an IllegalArgumentException.
     */
    @Test
    void testAllZeroDimensions() {
        Packet packet = new Packet(0, 0, 0, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package dimensions or weight. All values must be greater than 0.", exception.getMessage());
    }

    // TODO: Maybe add a test for every dimension being 0?

    /**
     * Tests that a parcel with only one null dimension throws an IllegalArgumentException.
     */
    @Test
    void testOneZeroDimension() {
        Packet packet = new Packet(0, 300, 150, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package dimensions or weight. All values must be greater than 0.", exception.getMessage());
    }

    /**
     * Tests that a parcel with negative dimensions throws an IllegalArgumentException.
     */
    @Test
    void testNegativeDimensions() {
        Packet packet = new Packet(-300, 300, 150, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package dimensions or weight. All values must be greater than 0.", exception.getMessage());
    }

    /**
     * Tests that a Null parcel throws an IllegalArgumentException.
     */
    @Test
    void testNullPacket() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(null);
        });
        assertEquals("Invalid package dimensions or weight. All values must be greater than 0.", exception.getMessage());
    }

    /**
     * Test that a parcel exceeding the girth limit is priced correctly.
     */
    @Test
    void testExceedMaxGirth() throws ShippingRuleException {
        Packet packet = new Packet(1200, 600, 600, 5000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "Parcel exceeding girth limit should cost 14.99 EUR.");
    }

    /**
     * Tests calculation precision for a weight that is very close to 10 kg boundary.
     */
    @Test
    void testFloatingPointPrecisionForWeight() throws ShippingRuleException {
        Packet packet = new Packet(100, 100, 100, 9999); // Very close to 10 kg
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(7.99, result, "Parcel very close to 10 kg boundary should cost 5.89 EUR.");
    }

    /**
     * Tests a parcel with near-limit dimensions and weight to ensure correct rule selection.
     */
    @Test
    void testNearLimitCombination() throws ShippingRuleException {
        Packet packet = new Packet(299, 299, 150, 999); // Just below small parcel limits
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(3.89, result, "Parcel just below the small parcel limit should cost 3.89 EUR.");
    }

    /**
     * Test that a parcel with maximum integer dimensions and weight does not crash the program.
     */
    @Test
    void testMaxIntegerDimensions() {
        Packet packet = new Packet(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package dimensions or weight, no suitable shipping rule found.", exception.getMessage());
    }
}

