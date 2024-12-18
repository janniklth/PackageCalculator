package test;

import control.ShippingRuleLoader;
import data.ShippingRule;
import exceptions.ShippingRuleException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides test cases for the {@link ShippingRuleLoader} class.
 *
 * <p>Tests the loading of shipping rules from JSON files, including the default rules and custom rules as well as
 * testing
 * error handling when the json file is not found or corrupted.</p>
 *
 * <p>Contains the following test cases:</p>
 * <ul>
 *     <li> {@link #testLoadShippingRulesSuccessfully()} - Tests that shipping rules are loaded correctly from a JSON
 *     file
 *     with the correct parameters. </li>
 *     <li> {@link #testLoadDefaultShippingRules()} - Tests that the default shipping rules are loaded successfully.
 *     </li>
 *     <li> {@link #testLoadShippingRulesWithDimensionsAndWeight()} - Tests that all dimensions and weight limits are
 *     correctly loaded from the JSON file. </li>
 *     <li> {@link #testLoadShippingRulesWithEmptyJson()} - Tests that an empty JSON file throws a
 *     {@link ShippingRuleException}. </li>
 *     <li> {@link #testLoadShippingRulesWithCorruptedJson()} - Tests that a corrupted JSON file throws a
 *     {@link ShippingRuleException}. </li>
 *     <li> {@link #testShippingRulesAreSortedByCost()} - Tests that the rules are sorted by cost after loading. </li>
 *     <li> {@link #testLoadShippingRulesFileNotFound()} - Tests that a {@link ShippingRuleException} is thrown when the
 *     shipping rules file cannot be found. </li>
 * </ul>
 *
 * @see ShippingRuleLoader
 * @see ShippingRule
 * @see ShippingRuleException
 */
public class ShippingRuleLoaderTest {
    /**
     * Tests that shipping rules are loaded correctly from a JSON file.
     */
    @Test
    public void testLoadShippingRulesSuccessfully() throws Exception {
        // load shipping rules from the test JSON file
        List<ShippingRule> shippingRules = ShippingRuleLoader.loadCustomShippingRules("test/ressources" +
                "/test_basic_rules.json");

        // Assert that the shipping rules are not null and that there are 2 rules loaded with the correct types
        assertNotNull(shippingRules, "Shipping rules list should not be null.");
        assertEquals(2, shippingRules.size(), "There should be exactly 2 shipping rules.");
        assertEquals("Small", shippingRules.get(0).getType(), "First rule type from test json should be 'Small'.");
        assertEquals("Large", shippingRules.get(1).getType(), "Second rule type from test json should be 'Large'.");
    }

    /**
     * Tests that the default shipping rules (from hardcoded path) are loaded successfully.
     */
    @Test
    public void testLoadDefaultShippingRules() {
        // Assert that the default shipping rules are loaded successfully
        assertDoesNotThrow(() -> ShippingRuleLoader.loadShippingRules());
    }

    /**
     * Tests that all dimensions and weight limits are correctly loaded from the JSON file.
     *
     * @throws Exception
     */
    @Test
    public void testLoadShippingRulesWithDimensionsAndWeight() throws Exception {
        // load shipping rules from the test JSON file
        List<ShippingRule> shippingRules = ShippingRuleLoader.loadCustomShippingRules("test/ressources" +
                "/test_basic_rules.json");

        // Assert that the first rule has the correct dimensions and weight
        ShippingRule smallRule = shippingRules.get(0);
        assertEquals(400, smallRule.getMaxLength(), "Small rule should have max length of 100.");
        assertEquals(100, smallRule.getMaxWidth(), "Small rule should have max width of 50.");
        assertEquals(100, smallRule.getMaxHeight(), "Small rule should have max height of 50.");
        assertEquals(500, smallRule.getMaxWeight(), "Small rule should have max weight of 5000.");
    }

    /**
     * Tests that an empty JSON file throws an exception.
     */
    @Test
    public void testLoadShippingRulesWithEmptyJson() {
        // Assert that the shippingRuleLoader should throw an exception
        Exception exception = assertThrows(ShippingRuleException.class,
                () -> ShippingRuleLoader.loadCustomShippingRules("test/ressources/test_empty_rules.json"));
        assertTrue(exception.getCause().toString().contains("No shipping rules found in the file"), "Exception " +
                "message should indicate no rules found.");
    }

    /**
     * Tests that a corrupted JSON file throws an exception (e.g. missing closing bracket).
     */
    @Test
    public void testLoadShippingRulesWithCorruptedJson() {
        // Assert that the shippingRuleLoader should throw an exception
        Exception exception = assertThrows(ShippingRuleException.class,
                () -> ShippingRuleLoader.loadCustomShippingRules("test/ressources/test_corrupted_rules.json"));
        assertTrue(exception.getMessage().contains("Error loading shipping rules"), "Exception message should " +
                "indicate error loading rules.");
    }

    /**
     * Tests that the rules are sorted by cost after loading (ascending order).
     */
    @Test
    public void testShippingRulesAreSortedByCost() throws Exception {
        // load shipping rules from the test JSON file
        List<ShippingRule> shippingRules = ShippingRuleLoader.loadCustomShippingRules("test/ressources" +
                "/test_unssorted_rules.json");

        // assert that the shipping rules are not null and that they are sorted by cost
        assertNotNull(shippingRules, "Shipping rules list should not be null.");
        assertTrue(isSortedByCost(shippingRules), "Shipping rules are not sorted by cost.");
    }

    /**
     * Tests that an error is thrown when the shipping rules files cannot be found.
     */
    @Test
    public void testLoadShippingRulesFileNotFound() {
        // Assert that the shippingRuleLoader should throw an exception
        Exception exception = assertThrows(ShippingRuleException.class,
                () -> ShippingRuleLoader.loadCustomShippingRules("test/ressources/non_existent_file.json"));
        assertTrue(exception.getMessage().contains("Shipping rules file not found"), "Exception message should " +
                "indicate file not found.");
    }

    /**
     * Helper function that checks if a list of shipping rules is sorted by cost.
     *
     * @param shippingRules the list of shipping rules to check
     * @return true if the list is sorted by cost, false otherwise
     */
    private boolean isSortedByCost(List<ShippingRule> shippingRules) {
        for (int i = 1; i < shippingRules.size(); i++) {
            if (shippingRules.get(i - 1).getCost() > shippingRules.get(i).getCost()) {
                return false;
            }
        }
        return true;
    }
}