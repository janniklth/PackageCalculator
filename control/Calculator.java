package control;

import data.Packet;
import data.ShippingRule;
import exceptions.ShippingRuleException;
import java.util.List;

public class Calculator {

    /**
     * Calculates the shipping costs based on the dimensions and weight of the given packet. Uses the shipping rules
     * loaded from an external JSON file.
     *
     * @param pack the packet for which the shipping costs are to be calculated
     * @return the calculated shipping costs
     */
    public static double calcShippingCosts(Packet pack) throws ShippingRuleException {
        // Load shipping rules from the external JSON file
        List<ShippingRule> shippingRules = ShippingRuleLoader.loadShippingRules();

        // Defensive checks for invalid or nonsensical inputs
        if (pack == null || pack.length <= 0 || pack.width <= 0 || pack.height <= 0 || pack.weight <= 0) {
            throw new IllegalArgumentException("Invalid package dimensions or weight. All values must be greater than 0.");
        }

        // Loop through the loaded shipping rules to find the matching rule
        for (ShippingRule rule : shippingRules) {
            if (rule.matches(pack)) {
                return rule.getCost();
            }
        }

        // If no rule matches, throw an exception (or return a default value if needed)
        throw new IllegalArgumentException("Invalid package dimensions or weight, no suitable shipping rule found.");
    }
}
