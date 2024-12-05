package control;

import data.MeasurementSystem;
import data.Packet;
import data.ShippingRule;
import exceptions.ShippingRuleException;
import java.util.List;

/**
 * The Calculator class provides methods for calculating shipping costs based on packet dimensions and weight.
 *
 * <p>Includes a method for calculating shipping costs using the loaded shipping rules and converting
 * the packet dimensions to metric units if needed using the current setting from {@link SettingsManager} and converting
 * method provided by {@link MeasurementSystem}. Also uses the {@link ShippingRuleLoader} to load the shipping rules
 * from an external JSON file.</p>
 *
 * @see ShippingRuleLoader
 * @see SettingsManager
 * @see MeasurementSystem
 */
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
        if (pack == null) {
            throw new IllegalArgumentException("The package is null. Please provide a valid package.");
        }

        // Validate the packet dimensions and weight
        Calculator.validatePacket(pack);

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
        MeasurementSystem measurementSystem = SettingsManager.getMeasurementSystem();

        // Convert the packet dimensions and weight to metric units if the current unit is imperial
        double lengthInMM = measurementSystem.convertLength(pack.length, MeasurementSystem.METRIC, true);
        double widthInMM = measurementSystem.convertLength(pack.width, MeasurementSystem.METRIC, true);
        double heightInMM = measurementSystem.convertLength(pack.height, MeasurementSystem.METRIC, true);
        double weightInGrams = measurementSystem.convertWeight(pack.weight, MeasurementSystem.METRIC, true);

        // Return a new packet with the converted dimensions and weight (cast to integers)
        return new Packet((int) lengthInMM, (int) widthInMM, (int) heightInMM, (int) weightInGrams);

    }

    /**
     * Validates the dimensions and weight of the given packet and gives a detailed error message if any of them are invalid.
     *
     * @param pack the packet to validate
     * @throws IllegalArgumentException if any of the dimensions or weight are invalid
     */
    private static void validatePacket(Packet pack) {
        StringBuilder errorMessage = new StringBuilder("Invalid package parameters: ");
        boolean hasError = false;

        // Check if the length is greater than 0
        if (pack.length <= 0) {
            errorMessage.append("\n - length must be greater than 0. ");
            hasError = true;
        }

        // Check if the width is greater than 0
        if (pack.width <= 0) {
            errorMessage.append("\n - width must be greater than 0. ");
            hasError = true;
        }

        // Check if the height is greater than 0
        if (pack.height <= 0) {
            errorMessage.append("\n - height must be greater than 0. ");
            hasError = true;
        }

        // Check if the weight is greater than 0
        if (pack.weight <= 0) {
            errorMessage.append("\n - weight must be greater than 0. ");
            hasError = true;
        }

        // Throw an exception with the error message if any of the parameters are invalid
        if (hasError) {
            throw new IllegalArgumentException(errorMessage.toString().trim());
        }
    }
}
