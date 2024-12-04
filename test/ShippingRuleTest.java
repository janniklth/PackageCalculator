package test;

import data.Packet;
import data.ShippingRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides test cases for the {@link ShippingRule} class.
 *
 * <p>Tests the matching of packets to shipping rules based on the dimensions and weight of the packet. The tests
 * ensure that packets that match the rule are correctly identified as a match and packets that do not match the rule
 * are correctly identified as not a match.</p>
 *
 * <p>Contains the following test cases:</p>
 * <ul>
 *     <li> {@link #testMatches_ValidPacket_MatchesRule()} - Tests that a packet with dimensions and weight that are
 *     clearly below the rule is correctly identified as a match. </li>
 *     <li> {@link #testMatches_ValidLimitPacket_MatchesRule()} - Tests that a packet with dimension and weight at the
 *     limit of the rule is correctly identified as a match. </li>
 *     <li> {@link #testMatches_InvalidPacket_DimensionsExceed()} - Tests that a packet with dimensions that exceed the
 *     rule is correctly identified as not a match. </li>
 *     <li> {@link #testMatches_InvalidPacket_WeightExceeds()} - Tests that a packet with weight that exceeds the rule
 *     is correctly identified as not a match. </li>
 *     <li> {@link #testMatches_InvalidPacket_GirthExceeds()} - Tests that a packet with girth that exceeds the rule
 *     (with girth limit) is correctly identified as not a match. </li>
 *     <li> {@link #testMatches_InvalidPacket_NullPacket()} - Tests that a null-packet throws a NullPointerException. </li>
 * </ul>
 *
 * @see ShippingRule
 * @see Packet
 * @see NullPointerException
 */
public class ShippingRuleTest {

    /**
     * Tests that a packet with dimensions and weight that are clearly below the rule is correctly identified as a match.
     */
    @Test
    public void testMatches_ValidPacket_MatchesRule() {
        // create a shipping rule and a packet that matches this rule
        ShippingRule rule = createShippingRule("Small", 100, 100, 100, 100, null, 5.99);
        Packet packet = new Packet(50, 50, 50, 50);

        // assert that the packet matches the rule
        assertTrue(rule.matches(packet), "The packet should match the rule.");
    }

    /**
     * Tests that a packet with dimension and weight at the limit of the rule is correctly identified as a match.
     */
    @Test
    public void testMatches_ValidLimitPacket_MatchesRule() {
        // create a shipping rule and a packet that matches this rules
        ShippingRule rule = createShippingRule("Small", 100, 100, 100, 100, null, 5.99);
        Packet packet = new Packet(100, 100, 100, 100);

        // assert that the packet matches the rule
        assertTrue(rule.matches(packet), "The packet should match the rule.");
    }

    /**
     * Tests that a packet with dimensions that exceed the rule is correctly identified as not a match.
     */
    @Test
    public void testMatches_InvalidPacket_DimensionsExceed() {
        // create a shipping rule and a packet that does not match this rule due to dimensions
        ShippingRule rule = createShippingRule("Small", 100, 100, 100, 100, null, 5.99);
        Packet packet = new Packet(200, 200, 200, 100); // Length exceeds the limit

        // assert assert that the packet does not match the rule
        assertFalse(rule.matches(packet), "The packet should not match the rule as the dimensions exceed the limit.");
    }

    /**
     * Tests that a packet with weight that exceeds the rule is correctly identified as not a match.
     */
    @Test
    public void testMatches_InvalidPacket_WeightExceeds() {
        // create a shipping rule and an according packet that does not match this rule due to weight
        ShippingRule rule = createShippingRule("Small", 100, 100, 100, 100, null, 5.99);
        Packet packet = new Packet(100, 100, 100, 200);

        // assert that the packet does not match the rule
        assertFalse(rule.matches(packet), "The packet should not match the rule as the weight exceeds the limit.");
    }

    /**
     * Tests that a packet with girth that exceeds the rule (with girth limit) is correctly identified as not a match.
     */
    @Test
    public void testMatches_InvalidPacket_GirthExceeds() {
        // create a shipping rule and a packet that does not match this rule due to girth
        ShippingRule rule = createShippingRule("Small", 100, 100, 100, 100, 100, 5.99);
        Packet packet = new Packet(100, 100, 100, 100); // Girth exceeds the limit

        // assert that
        assertFalse(rule.matches(packet), "The packet should not match the rule as the girth exceeds the limit.");
    }

    /**
     * Tests that a null-packet throws a NullPointerException.
     */
    @Test
    public void testMatches_InvalidPacket_NullPacket() {
        // create a shipping rule
        ShippingRule rule = createShippingRule("Small", 100, 100, 100, 100, null, 5.0);

        // assert that a null packet throws a NullPointerException
        assertThrows(NullPointerException.class, () -> rule.matches(null), "A null packet should throw a NullPointerException.");
    }

    /**
     * Helper method to create a shipping rule with the given parameters.
     * @param type
     * @param maxLength
     * @param maxWidth
     * @param maxHeight
     * @param maxWeight
     * @param maxGirth
     * @param cost
     * @return the created shipping rule
     */
    private ShippingRule createShippingRule(String type, int maxLength, int maxWidth, int maxHeight, int maxWeight, Integer maxGirth, double cost) {
        ShippingRule rule = new ShippingRule();
        setField(rule, "type", type);
        setField(rule, "maxLength", maxLength);
        setField(rule, "maxWidth", maxWidth);
        setField(rule, "maxHeight", maxHeight);
        setField(rule, "maxWeight", maxWeight);
        setField(rule, "maxGirth", maxGirth);
        setField(rule, "cost", cost);
        return rule;
    }

    /**
     * Helper method to set a field of an object via reflection.
     * @param object
     * @param fieldName
     * @param value
     */
    private void setField(Object object, String fieldName, Object value) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting field via reflection", e);
        }
    }
}