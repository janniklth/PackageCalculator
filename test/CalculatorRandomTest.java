package test;

import control.Calculator;
import data.Packet;
import exceptions.ShippingRuleException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides a random based test for the {@link Calculator} class.
 *
 * <p>Tests the shipping cost calculation for 1000 random packets based on the defined shipping rules. Ensures that
 * exceptions are thrown for oversized packets and packets with invalid dimensions or weight.</p>
 *
 * @see Calculator
 * @see Packet
 * @see IllegalArgumentException
 * @see Random
 */
public class CalculatorRandomTest {

    private final Random random = new Random();

    /**
     * Tests that 1000 random packets are correctly calculated based on the shipping rules.
     * Ensures exceptions are thrown for oversized packets.
     */
    @Test
    public void testRandomPackets() {
        for (int i = 0; i < 1000; i++) {
            // Generate random packet
            Packet packet = generateRandomPacket();

            // Check if the packet is oversized or any dimension is zero
            if (isOversized(packet)) {
                // Assert that an exception is thrown for oversized packets
                assertThrows(IllegalArgumentException.class,
                        () -> Calculator.calcShippingCosts(packet),
                        String.format("Expected exception for oversized packet: %s", packet));
            } else if (packet.getWidth() == 0 || packet.getHeight() == 0 || packet.getLength() == 0 || packet.getWeight() == 0) {
                // Assert that an exception is thrown for invalid packets
                assertThrows(IllegalArgumentException.class,
                        () -> Calculator.calcShippingCosts(packet),
                        String.format("Expected exception for invalid packet: %s", packet));
            } else {
                // Calculate actual and expected costs for valid packets
                double actualCost = assertDoesNotThrow(() -> Calculator.calcShippingCosts(packet));
                double expectedCost = calculateExpectedCost(packet);

                // Assert that the actual cost matches the expected cost
                assertEquals(expectedCost, actualCost, 0.01,
                        String.format("Mismatch for packet, height: %d, width: %d, length: %d, weight: %d",
                                packet.getHeight(), packet.getWidth(), packet.getLength(), packet.getWeight()));

                // Additional checks for the shipping cost
                assertTrue(actualCost >= 0, "Shipping cost must never be negative.");
                assertTrue(actualCost <= 100, "Shipping cost must never exceed 100.");
            }
        }
    }

    /**
     * Generates a random packet with random dimensions and weight.
     *
     * @return a random packet
     */
    private Packet generateRandomPacket() {
        int length = random.nextInt(1500); // Generate random length up to 1500 mm
        int width = random.nextInt(800);  // Generate random width up to 800 mm
        int height = random.nextInt(800); // Generate random height up to 800 mm
        int weight = random.nextInt(40000); // Generate random weight up to 40 kg
        return new Packet(length, width, height, weight);
    }

    /**
     * Helper function that calculates the expected cost for a given packet based on the defined rules.
     *
     * @param packet the packet for which the expected cost is to be calculated
     * @return the expected cost
     * TODO: Refactor with "introduce parameter object" pattern
     */
    private double calculateExpectedCost(Packet packet) {
        if (packet.getLength() <= 300 && packet.getWidth() <= 300 && packet.getHeight() <= 150 && packet.getWeight() <= 1000) {
            return 3.89;
        } else if (packet.getLength() <= 600 && packet.getWidth() <= 300 && packet.getHeight() <= 150 && packet.getWeight() <= 2000) {
            return 4.39;
        } else if (packet.getLength() <= 1200 && packet.getWidth() <= 600 && packet.getHeight() <= 600 && packet.getGirth() <= 3000) {
            if (packet.getWeight() <= 5000) {
                return 5.89;
            } else if (packet.getWeight() <= 10000) {
                return 7.99;
            } else if (packet.getWeight() <= 31000) {
                return 14.99;
            }
        } else if (packet.getLength() <= 1200 && packet.getWidth() <= 600 && packet.getHeight() <= 600) {
            return 14.99;
        }
        throw new IllegalArgumentException("No matching rule found for the given packet dimensions and weight.");
    }

    /**
     * Helper function that determines whether a packet exceeds the maximum allowed dimensions or weight.
     *
     * @param packet the packet to check
     * @return true if the packet is oversized, false otherwise
     */
    private boolean isOversized(Packet packet) {
        return packet.getLength() > 1200 || packet.getWidth() > 600 || packet.getHeight() > 600 || packet.getGirth() > 30000 || packet.getWeight() > 31000;
    }
}
