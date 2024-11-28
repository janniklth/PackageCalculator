package data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ShippingRule class represents a rule for shipping packages based on their dimensions and weight.
 * Each rule specifies the maximum length, width, height, weight, and optionally girth of a package that can be shipped
 * according to this rule, as well as the cost for shipping such a package. It also provides a method to check if a given
 * {@link Packet} matches the rule.
 * @see Packet
 */
public class ShippingRule {

    @JsonProperty("type")
    private String type;

    @JsonProperty("maxLength")
    private int maxLength;

    @JsonProperty("maxWidth")
    private int maxWidth;

    @JsonProperty("maxHeight")
    private int maxHeight;

    @JsonProperty("maxWeight")
    private int maxWeight;

    @JsonProperty("maxGirth")
    private Integer maxGirth;

    @JsonProperty("cost")
    private double cost;


    /**
     * Retrieves the type (name) of the package according to this shipping rule, e.g., "Small" or "Large".
     * @return the shipping rule type as a String
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Retrieves the cost for shipping a package according to this rule.
     * @return the shipping cost as a double
     */
    public final double getCost() {
        return this.cost;
    }

    /**
     * Checks if the given packet fits within the dimensions and weight limits of this shipping rule.
     * @param pack the packet to check
     * @return true if the packet fits, false otherwise
     */
    private boolean isWithinDimensions(Packet pack) {
        return pack.length <= this.maxLength &&
                pack.width <= this.maxWidth &&
                pack.height <= this.maxHeight &&
                pack.weight <= this.maxWeight;
    }

    /**
     * Checks if the given packet matches this shipping rule (i.e., fits within the dimensions and weight limits).
     * Also respecting the optional girth limit if present.
     * @param pack the packet to check
     * @return true if the packet matches this rule, false otherwise
     */
    public boolean matches(Packet pack) {
        return this.isWithinDimensions(pack) && (this.maxGirth == null || pack.girth <= this.maxGirth);
    }
}
