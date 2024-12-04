/**
 * Provides the classes necessary to control the application logic.
 *
 * <p>This package includes utility classes for displaying alerts, handling (log-)messages, and managing
 * the overall logics of the application as well as handling the application settings and loading shipping rules from a file.</p>
 *
 * <p>Classes in this package:</p>
 * <ul>
 *   <li>{@link control.Calculator} - Manages the calculation of package dimensions and weight.</li>
 *   <li>{@link control.MessageHandler} - Handles displaying messages and alerts to the user.</li>
 *   <li>{@link control.SettingsManager} - Manages application settings and notifies listeners of changes.</li>
 *   <li>{@link control.ShippingRuleLoader} - Loads shipping rules from a file and provides sorted access to them and
 *   finding the best rule for a given package.</li>
 * </ul>
 */
package control;