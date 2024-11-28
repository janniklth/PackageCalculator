package control;

import exceptions.ShippingRuleException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import data.ShippingRule;

import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

/**
 * The ShippingRuleLoader class loads shipping rules from a JSON file and provides a method to retrieve them as a list of
 * {@link ShippingRule} objects.
 * @see ShippingRule
 */
public class ShippingRuleLoader {

    private static final String RULES_FILE = "data/shipping_rules.json";

    /**
     * Loads the shipping rules from the JSON file specified by the parameter.
     * @param rules_json_path the path to the JSON file containing the shipping rules
     * @return a list of ShippingRule objects
     * @throws ShippingRuleException if an error occurs while loading the shipping rules
     */
    public static List<ShippingRule> loadCustomShippingRules(String rules_json_path) throws ShippingRuleException {
        URL url = ShippingRuleLoader.class.getClassLoader().getResource(rules_json_path);
        if (url == null) {
            throw new ShippingRuleException("Shipping rules file not found: " + rules_json_path, null);
        }

        try (InputStream inputStream = ShippingRuleLoader.class.getClassLoader().getResourceAsStream(rules_json_path)) {
            if (inputStream == null) {
                throw new ShippingRuleException("Shipping rules file could not be opened: " + rules_json_path, null);
            }

            ObjectMapper mapper = new ObjectMapper();

            // Deserialize JSON into a list of ShippingRule objects
            List<ShippingRule> shippingRules = mapper.readValue(inputStream, new TypeReference<List<ShippingRule>>() {});

            if (shippingRules == null || shippingRules.isEmpty()) {
                throw new ShippingRuleException("No shipping rules found in the file: " + rules_json_path, null);
            }

            // Validate the rules
            for (ShippingRule rule : shippingRules) {
                validateShippingRule(rule, rules_json_path);
            }

            // Sort shipping rules by cost
            shippingRules.sort(Comparator.comparingDouble(ShippingRule::getCost));

            return shippingRules;

        } catch (Exception e) {
            throw new ShippingRuleException("Error loading shipping rules from file: " + rules_json_path, e);
        }
    }

    /**
     * Validates the required fields of a ShippingRule.
     * @param rule the ShippingRule to validate
     * @param rules_json_path the path to the JSON file for error reporting
     * @throws ShippingRuleException if any required field is missing or invalid
     */
    private static void validateShippingRule(ShippingRule rule, String rules_json_path) throws ShippingRuleException {
        if (rule.getType() == null || rule.getType().isEmpty()) {
            throw new ShippingRuleException("Missing 'type' field in shipping rule from file: " + rules_json_path, null);
        }
        if (rule.getMaxLength() <= 0) {
            throw new ShippingRuleException("Invalid 'maxLength' field in shipping rule from file: " + rules_json_path, null);
        }
        if (rule.getMaxWidth() <= 0) {
            throw new ShippingRuleException("Invalid 'maxWidth' field in shipping rule from file: " + rules_json_path, null);
        }
        if (rule.getMaxHeight() <= 0) {
            throw new ShippingRuleException("Invalid 'maxHeight' field in shipping rule from file: " + rules_json_path, null);
        }
        if (rule.getMaxWeight() <= 0) {
            throw new ShippingRuleException("Invalid 'maxWeight' field in shipping rule from file: " + rules_json_path, null);
        }
        if (rule.getCost() <= 0) {
            throw new ShippingRuleException("Invalid 'cost' field in shipping rule from file: " + rules_json_path, null);
        }
        // maxGirth is optional, but if present, it should be a valid positive value
        if (rule.getMaxGirth() != null && rule.getMaxGirth() <= 0) {
            throw new ShippingRuleException("Invalid 'maxGirth' field in shipping rule from file: " + rules_json_path, null);
        }
    }

    /**
     * Loads the shipping rules from the JSON file specified by the RULES_FILE constant.
     *
     * @return a list of ShippingRule objects
     * @throws ShippingRuleException if an error occurs while loading the shipping rules
     */
    public static List<ShippingRule> loadShippingRules() throws ShippingRuleException {
        return loadCustomShippingRules(RULES_FILE);
    }
}
