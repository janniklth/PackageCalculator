package test;

import static org.junit.jupiter.api.Assertions.*;

import control.Calculator;
import data.Packet;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Calculator's shipping cost calculation.
 * Verifies correct shipping costs based on the dimensions and weight of different package types.
 * Also tests for invalid inputs to ensure defensive programming is in place.
 */
public class CalculatorTest {
    /**
     * Tests that a small parcel with dimensions up to 30x30x15 cm and up to 1 kg
     * is correctly calculated to cost 3.89 EUR.
     */
    @Test
    void testSmallParcelRate() {
        Packet packet = new Packet(300, 300, 150, 1000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(3.89, result, "A parcel up to 30x30x15 cm and up to 1 kg should cost 3.89 EUR.");
    }

    /**
     * Tests that a medium parcel with dimensions up to 60x30x15 cm and up to 2 kg
     * is correctly calculated to cost 4.39 EUR.
     */
    @Test
    void testMediumParcelRate() {
        Packet packet = new Packet(600, 300, 150, 2000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(4.39, result, "A parcel up to 60x30x15 cm and up to 2 kg should cost 4.39 EUR.");
    }

    /**
     * Tests that a large parcel with dimensions up to 120x60x60 cm, a girth of up to 300 cm,
     * and a weight up to 5 kg is correctly calculated to cost 5.89 EUR.
     */
    @Test
    void testLargeParcelRate() {
        Packet packet = new Packet(1000, 500, 500, 5000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(5.89, result, "A parcel up to 120x60x60 cm, girth <= 300 cm, and up to 5 kg should cost 5.89 EUR.");
    }

    /**
     * Tests that an extra-large parcel with dimensions up to 120x60x60 cm, a girth of up to 300 cm,
     * and a weight up to 10 kg is correctly calculated to cost 7.99 EUR.
     */
    @Test
    void testXLParcelRate() {
        Packet packet = new Packet(1000, 500, 500, 10000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(7.99, result, "A parcel up to 120x60x60 cm, girth <= 300 cm, and up to 10 kg should cost 7.99 EUR.");
    }

    /**
     * Tests that an extra-extra-large parcel with dimensions up to 120x60x60 cm
     * and a weight up to 31 kg is correctly calculated to cost 14.99 EUR.
     */
    @Test
    void testXXLParcelRate() {
        Packet packet = new Packet(1200, 600, 600, 31000);
        double result = Calculator.calcShippingCosts(packet);
        assertEquals(14.99, result, "A parcel up to 120x60x60 cm and up to 31 kg should cost 14.99 EUR.");
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
        assertEquals("Package weight exceeds maximum limit of 31 kg.", exception.getMessage());
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
        assertEquals("Package dimensions exceed maximum allowed limits.", exception.getMessage());
    }

    /**
     * Tests that a parcel with no dimension throws an IllegalArgumentException.
     */
    @Test
    void testNoDimensions() {
        Packet packet = new Packet(0, 0, 0, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.calcShippingCosts(packet);
        });
        assertEquals("Invalid package dimensions or weight.", exception.getMessage());
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
        assertEquals("Invalid package dimensions or weight.", exception.getMessage());
    }
}
