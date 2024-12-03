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

        // Convert the packet dimensions and weight to metric units if the current unit is imperial
        double lengthInMM = SettingsManager.getMeasurementUnit().convertLength(pack.length, MeasurementUnit.METRIC);
        double widthInMM = SettingsManager.getMeasurementUnit().convertLength(pack.width, MeasurementUnit.METRIC);
        double heightInMM = SettingsManager.getMeasurementUnit().convertLength(pack.height, MeasurementUnit.METRIC);
        double weightInGrams = SettingsManager.getMeasurementUnit().convertWeight(pack.weight, MeasurementUnit.METRIC);

        // Return a new packet with the converted dimensions and weight (cast to integers)
        return new Packet((int) lengthInMM, (int) widthInMM, (int) heightInMM, (int) weightInGrams);
    }
}
