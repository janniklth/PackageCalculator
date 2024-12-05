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
	 * The girth of the package in millimeters
	 */
	public int girth;

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
		this.girth = length + 2 * width + 2 * height;
	}

	/**
	 * Retrieves the length of the package
	 * @return the length of the package in millimeters
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Retrieves the width of the package
	 * @return the width of the package in millimeters
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Retrieves the height of the package
	 * @return the height of the package in millimeters
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Retrieves the weight of the package
	 * @return the weight of the package in grams
	 */
	public int getWeight() {
		return weight;
	}
}