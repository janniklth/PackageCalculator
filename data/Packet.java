package data;

/**
 * The Packet class represents a package with specific dimensions (length and height) and weight
 */
public class Packet {

	/**
	 * The length of the package in millimeters
	 */
	public int length;

	/**
	 * The width of the package in millimeters
	 */
	public int width;

	/**
	 * The height of the package in millimeters
	 */
	public int height;

	/**
	 * The weight of the package in grams
	 */
	public int weight;

	/**
	 * Constructor for the Packet class
	 *
	 * @param length the length of the package in millimeters
	 * @param width the width of the package in millimeters
	 * @param height the height of the package in millimeters
	 * @param weight the weight of the package in grams
	 */
	public Packet(int length, int width, int height, int weight) {
		this.length = length;
		this.width = width;
		this.height = height;
		this.weight = weight;
	}
}