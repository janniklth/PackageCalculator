package control;

import data.Packet;

/**
 * The Calculator class provides a method to calculate shipping costs based on the dimensions and weight of a packet
 */
public class Calculator {

    /**
     * Calculates the shipping costs based on the dimensions and weight of the given packet
     *
     * @param pack the packet for which the shipping costs are to be calculated
     * @return the calculated shipping costs
     */
    public static double calcShippingCosts(Packet pack) {
        // Initialize default cost for invalid inputs
        double shippingCosts = -1.0;

        // Defensive checks for invalid or nonsensical inputs
        if (pack == null || pack.length <= 0 || pack.width <= 0 || pack.height <= 0 || pack.weight <= 0) {
            throw new IllegalArgumentException("Invalid package dimensions or weight.");
        }

        // Calculate girth (L + 2W + 2H)
        int girth = pack.length + (2 * pack.width) + (2 * pack.height);

        // Define shipping costs based on rules
        if (pack.length <= 300 && pack.width <= 300 && pack.height <= 150 && pack.weight <= 1000) {
            shippingCosts = 3.89;
        } else if (pack.length <= 600 && pack.width <= 300 && pack.height <= 150 && pack.weight <= 2000) {
            shippingCosts = 4.39;
        } else if (pack.length <= 1200 && pack.width <= 600 && pack.height <= 600 && girth <= 3000) {
            if (pack.weight <= 5000) {
                shippingCosts = 5.89;
            } else if (pack.weight <= 10000) {
                shippingCosts = 7.99;
            } else if (pack.weight <= 31000) {
                shippingCosts = 14.99;
            } else {
                throw new IllegalArgumentException("Package weight exceeds maximum limit of 31 kg.");
            }
        } else if (pack.length <= 1200 && pack.width <= 600 && pack.height <= 600 && pack.weight <= 31000) {
            shippingCosts = 14.99;
        } else if (pack.weight > 31000 && pack.length > 1200 && pack.width > 600 && pack.height > 600) {
            throw new IllegalArgumentException("Package weight exceeds maximum limit of 31 kg and dimensions exceed maximum limits of 120x60x60 cm.");
        } else if (pack.weight > 31000) {
            throw new IllegalArgumentException("Package weight exceeds maximum limit of 31 kg.");
        }
        else {
            throw new IllegalArgumentException("Package dimensions exceed maximum allowed limits.");
        }
        return shippingCosts;
    }
}