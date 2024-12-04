/**
 * Provides the classes necessary to create a package and handling data related to the package, measurement units,
 * settings, and shipping rules.
 *
 * <p>This package includes classes for creating a package with dimensions and weight, converting between different
 * measurement systems, managing application settings, and loading shipping rules from a file. It serves as the data
 * layer
 * for the application, providing the necessary functionality to store and manipulate data.</p>
 *
 * <p>Classes in this package:</p>
 * <ul>
 *     <li>{@link data.Currency} - Represents a currency with a name, symbol and exchange rate to Euro. Also provides
 *     methods for converting amounts between currencies.</li>
 *     <li>{@link data.ErrorDisplayState} - Represents the different states for displaying error messages.</li>
 *     <li>{@link data.MeasurementSystem} - Represents a measurement system with a name, length unit symbol, and weight
 *     unit symbol. Also provides methods for converting between different measurement systems.</li>
 *     <li>{@link data.Packet} - Represents a packet with dimensions and weight. </li>
 *     <li>{@link data.ShippingRule} - Represents the rules for the cost of shipping a package based on its weight
 *     and dimensions.</li>
 * </ul>
 */
package data;