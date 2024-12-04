package control;

import data.MeasurementSystem;
import data.ErrorDisplayState;
import data.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * The SettingsManager class provides methods for managing user settings and notifying listeners of changes.
 *
 * <p>Includes methods for getting and setting the current {@link MeasurementSystem}, {@link ErrorDisplayState}, and {@link Currency},
 * as well as registering listeners to be notified when settings change to reflect teh changes in the UI.</p>
 *
 * @see MeasurementSystem
 * @see ErrorDisplayState
 * @see Currency
 * @see SettingsListener
 */
public class SettingsManager {
    private static List<SettingsListener> listeners = new ArrayList<>();
    private static MeasurementSystem measurementSystem = MeasurementSystem.METRIC;
    private static ErrorDisplayState errorDisplayState = ErrorDisplayState.POPUP_AND_LOG;
    private static Currency currency = Currency.EURO;

    /**
     * Retrieves the current measurement system.
     *
     * @return the current measurement system
     */
    public static MeasurementSystem getMeasurementSystem() {
        return measurementSystem;
    }

    /**
     * Sets/updates the current measurement system to the given system.
     *
     * @param system the new measurement system
     */
    public static void setMeasurementSystem(MeasurementSystem system) {
        measurementSystem = system;
        notifyListeners();
    }

    /**
     * Retrieves the current error display state.
     *
     * @return the current error display state
     */
    public static ErrorDisplayState getErrorDisplayState() {
        return errorDisplayState;
    }

    /**
     * Sets/updates the current error display state to the given state.
     *
     * @param state the new error display state
     */
    public static void setShowErrorPopups(ErrorDisplayState state) {
        errorDisplayState = state;
    }

    /**
     * Retrieves the current currency.
     *
     * @return the current currency
     */
    public static Currency getCurrency() {
        return currency;
    }

    /**
     * Sets/updates the current currency to the given currency.
     *
     * @param selectedCurrency the new currency
     */
    public static void setCurrency(Currency selectedCurrency) {
        currency = selectedCurrency;
    }

    /**
     * Registers a listener to be notified when settings change.
     *
     * @param listener the listener to register
     */
    public static void registerListener(SettingsListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners that settings have changed.
     */
    private static void notifyListeners() {
        for (SettingsListener listener : listeners) {
            listener.onSettingsChanged();
        }
    }

    /**
     * The SettingsListener interface provides a method for notifying listeners when settings change.
     */
    public interface SettingsListener {
        void onSettingsChanged();
    }
}