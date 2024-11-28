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
     * Loads the shipping rules from the JSON file specified by the RULES_FILE constant.
     *
     * @return a list of ShippingRule objects
     * @throws ShippingRuleException if an error occurs while loading the shipping rules
     */
    public static List<ShippingRule> loadShippingRules() throws ShippingRuleException {
        URL url = ShippingRuleLoader.class.getClassLoader().getResource(RULES_FILE);
        if (url == null) {
            throw new ShippingRuleException("Shipping rules file not found: " + RULES_FILE, null);
        }

        try (InputStream inputStream = ShippingRuleLoader.class.getClassLoader().getResourceAsStream(RULES_FILE)) {
            if (inputStream == null) {
                throw new ShippingRuleException("Shipping rules file could not be opened: " + RULES_FILE, null);
            }

            ObjectMapper mapper = new ObjectMapper();

            // Deserialize JSON into a list of ShippingRule objects
            List<ShippingRule> shippingRules = mapper.readValue(inputStream, new TypeReference<List<ShippingRule>>() {});

            if (shippingRules == null || shippingRules.isEmpty()) {
                throw new ShippingRuleException("No shipping rules found in the file: " + RULES_FILE, null);
            }

            // Sort shipping rules by cost
            shippingRules.sort(Comparator.comparingDouble(ShippingRule::getCost));

            return shippingRules;

        } catch (Exception e) {
            throw new ShippingRuleException("Error loading shipping rules from file: " + RULES_FILE, e);
        }
    }
}
