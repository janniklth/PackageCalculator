package control;

import data.Packet;

/**
 * The Calculator class provides a method to calculate shipping costs based on the dimensions and weight of a packet.
 */
public class Calculator {

	/**
	 * Calculates the shipping costs based on the dimensions and weight of the given packet.
	 * @param pack the packet for which the shipping costs are to be calculated
	 * @return the calculated shipping costs
	 */
	public double calcShippingCosts(Packet pack) {
		double shippingCosts;
		if ((pack.height <= 300) && (pack.width <= 300) && (pack.height <= 150)) {
			shippingCosts = 3.89;
		}
		if ((pack.height <= 600) && (pack.width <= 300) && (pack.height <= 150)) {
			shippingCosts = 4.39;
		}
		if ((pack.height <= 1200) && (pack.width <= 600) && (pack.height <= 600) && pack.weight <= 5000) {
			shippingCosts = 5.99;
		} else if (pack.weight <= 10000) {
			shippingCosts = 7.99;
		} else {
			shippingCosts = 14.99;
		}
		return shippingCosts;
	}
}
