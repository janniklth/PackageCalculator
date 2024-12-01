package control;

import data.MeasurementUnit;
import data.Packet;
import data.ShippingRule;
import exceptions.ShippingRuleException;
import java.util.List;

public class Calculator {

    /**
     * Calculates the shipping costs based on the dimensions and weight of the given packet.
     * Uses the shipping rules loaded from an external JSON file.
     *
     * @param pack the packet for which the shipping costs are to be calculated
     * @return the calculated shipping costs
     * @throws ShippingRuleException if the shipping rules cannot be loaded
     */
    public static double calcShippingCosts(Packet pack) throws ShippingRuleException {
        // Load shipping rules from the external JSON file
        List<ShippingRule> shippingRules = ShippingRuleLoader.loadShippingRules();

        // Defensive checks for invalid or nonsensical inputs
        if (pack == null || pack.length <= 0 || pack.width <= 0 || pack.height <= 0 || pack.weight <= 0) {
            throw new IllegalArgumentException("Invalid package dimensions or weight. All values must be greater than 0.");
        }

        // Convert the packet dimensions to metric units if needed
        Packet convertedPack = convertToMetric(pack);

        // Loop through the loaded shipping rules to find the matching rule and convert the cost to the desired currency
        for (ShippingRule rule : shippingRules) {
            if (rule.matches(convertedPack)) {
                double costInEuro = rule.getCost();
                return SettingsManager.getCurrency().convertFromEuro(costInEuro);
            }
        }

        // If no rule matches, throw an exception (or return a default value if needed)
        throw new IllegalArgumentException("Invalid package dimensions or weight, no suitable shipping rule found.");
    }

    /**
     * Converts the dimensions and weight of the given packet to metric units if the current
     * measurement unit is imperial.
     *
     * @param pack the packet to convert
     * @return a new packet with dimensions and weight in metric units
     */
    private static Packet convertToMetric(Packet pack) {
        MeasurementUnit unit = SettingsManager.getMeasurementUnit();

        if (unit == MeasurementUnit.IMPERIAL) {
            double lengthInMM = pack.length * 25.4; // Inches to millimeters
            double widthInMM = pack.width * 25.4;
            double heightInMM = pack.height * 25.4;
            double weightInGrams = pack.weight * 453.592; // Pounds to grams

            return new Packet((int) lengthInMM, (int) widthInMM, (int) heightInMM, (int) weightInGrams);
        }

        // If already in metric, return the original packet
        return pack;
    }
}
