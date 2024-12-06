package test;

import control.Calculator;
import data.Packet;
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
                assertThrows(IllegalArgumentException.class, () -> Calculator.calcShippingCosts(packet),
                        String.format("Expected exception for oversized packet: %s", packet));
            } else if (hasZeroDimension(packet)) {
                // Assert that an exception is thrown for invalid packets
                assertThrows(IllegalArgumentException.class, () -> Calculator.calcShippingCosts(packet),
                        String.format("Expected exception for invalid packet: %s", packet));
            } else {
                // Calculate actual and expected costs for valid packets
                double actualCost = assertDoesNotThrow(() -> Calculator.calcShippingCosts(packet));
                double expectedCost = calculateExpectedCost(packet);

                // Assert that the actual cost matches the expected cost
                assertEquals(expectedCost, actualCost, 0.01, String.format("Mismatch for packet, height: %d, width: " +
                                "%d, length: %d, weight: %d", packet.getHeight(), packet.getWidth(), packet.getLength(),
                        packet.getWeight()));

                // Additional checks for the shipping cost
                assertTrue(actualCost >= 0, "Shipping cost must never be negative.");
                assertTrue(actualCost <= 100, "Shipping cost must never exceed 100.");
            }
        }
    }

    /**
     * Generates a random packet with random dimensions and weight.
     * <p>
     * TODO: Generate negative values for dimensions and weight as well to test invalid packets.
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
     * Calculates the cost for a given packet orientation based on predefined shipping rules.
     *
     * @param length the length of the packet
     * @param width  the width of the packet
     * @param height the height of the packet
     * @param weight the weight of the packet
     * @param girth  the girth of the packet (optional, can be null)
     * @return the calculated cost for the given orientation, or Double.MAX_VALUE if no rule matches
     */
    private double calculateCostForOrientation(int length, int width, int height, int weight, Integer girth) {
        if (isSmallPacket(length, width, height, weight)) {
            return 3.89;
        }
        if (isMediumPacket(length, width, height, weight)) {
            return 4.39;
        }
        if (isLargePacket(length, width, height)) {
            return calculateCostForLargePacket(weight, girth);
        }
        return Double.MAX_VALUE; // No valid rule matches
    }

    /**
     * Checks if the packet is a small packet based on the given dimensions and weight.
     *
     * @param length the length of the packet
     * @param width  the width of the packet
     * @param height the height of the packet
     * @param weight the weight of the packet
     * @return true if the packet is a small packet, false otherwise
     */
    private boolean isSmallPacket(int length, int width, int height, int weight) {
        return length <= 300 && width <= 300 && height <= 150 && weight <= 1000;
    }

    /**
     * Checks if the packet is a medium packet based on the given dimensions and weight.
     *
     * @param length the length of the packet
     * @param width  the width of the packet
     * @param height the height of the packet
     * @param weight the weight of the packet
     * @return true if the packet is a medium packet, false otherwise
     */
    private boolean isMediumPacket(int length, int width, int height, int weight) {
        return length <= 600 && width <= 300 && height <= 150 && weight <= 2000;
    }

    /**
     * Checks if the packet is a large packet based on the given dimensions and weight.
     *
     * @param length the length of the packet
     * @param width  the width of the packet
     * @param height the height of the packet
     * @return true if the packet is a large packet, false otherwise
     */
    private boolean isLargePacket(int length, int width, int height) {
        return length <= 1200 && width <= 600 && height <= 600;
    }

    /**
     * Calculates the cost for a large packet based on the given weight.
     *
     * @param weight the weight of the packet
     * @param girth  the girth of the packet (optional, can be null)
     * @return the calculated cost for the large packet
     */
    private double calculateCostForLargePacket(int weight, int girth) {
        if (girth <= 3000) {
            if (weight <= 5000) {
                return 5.89;
            }
            if (weight <= 10000) {
                return 7.99;
            }
            if (weight <= 31000) {
                return 14.99;
            }
        }
        // If girth is too large, use the following rule/fare (independent of weight)
        return 14.99;
    }


    /**
     * Calculates the lowest shipping cost for a given packet by considering all possible orientations.
     *
     * @param packet the packet for which the shipping cost is to be calculated
     * @return the lowest possible shipping cost
     * @throws IllegalArgumentException if no matching rule is found for any orientation
     */
    private double calculateExpectedCost(Packet packet) {
        // All possible orientations of the packet
        int[][] orientations = {{packet.getLength(), packet.getWidth(), packet.getHeight()}, {packet.getLength(),
                packet.getHeight(), packet.getWidth()}, {packet.getWidth(), packet.getLength(), packet.getHeight()},
                {packet.getWidth(), packet.getHeight(), packet.getLength()}, {packet.getHeight(), packet.getLength(),
                packet.getWidth()}, {packet.getHeight(), packet.getWidth(), packet.getLength()}};

        double lowestCost = Double.MAX_VALUE;

        for (int[] orientation : orientations) {
            int length = orientation[0];
            int width = orientation[1];
            int height = orientation[2];

            // Calculate the girth for the current orientation
            int girth = length + 2 * (width + height);

            // Calculate the cost for the current orientation
            double cost = this.calculateCostForOrientation(length, width, height, packet.getWeight(), girth);
            lowestCost = Math.min(lowestCost, cost);
        }

        if (lowestCost == Double.MAX_VALUE) {
            System.out.println("Length: " + packet.getLength() + " Width: " + packet.getWidth() + " Height: " + packet.getHeight() + " Weight: " + packet.getWeight());
            throw new IllegalArgumentException("No matching rule found for the given packet dimensions and weight.");
        }

        return lowestCost;
    }


    /**
     * Helper function that determines whether a packet exceeds the maximum allowed dimensions or weight
     * by checking every possible orientation.
     *
     * @param packet the packet to check
     * @return true if the packet is oversized in all orientations, false otherwise
     */
    private boolean isOversized(Packet packet) {
        // All possible orientations of the packet
        int[][] orientations = {{packet.getLength(), packet.getWidth(), packet.getHeight()}, {packet.getLength(),
                packet.getHeight(), packet.getWidth()}, {packet.getWidth(), packet.getLength(), packet.getHeight()},
                {packet.getWidth(), packet.getHeight(), packet.getLength()}, {packet.getHeight(), packet.getLength(),
                packet.getWidth()}, {packet.getHeight(), packet.getWidth(), packet.getLength()}};

        for (int[] orientation : orientations) {
            int length = orientation[0];
            int width = orientation[1];
            int height = orientation[2];
            int girth = 2 * (width + height);

            // Check if this orientation fits within the allowed dimensions and weight
            if (length <= 1200 && width <= 600 && height <= 600 && girth <= 3000 && packet.getWeight() <= 31000) {
                return false; // The packet fits in this orientation
            }
        }

        return true; // The packet is oversized in all orientations
    }

    /**
     * Checks if the given packet has any dimension set to zero.
     *
     * @param packet the packet to check
     * @return true if any dimension is zero, false otherwise
     */
    private static boolean hasZeroDimension(Packet packet) {
        return packet.getWidth() == 0 || packet.getHeight() == 0 || packet.getLength() == 0 || packet.getWeight() == 0;
    }

}
