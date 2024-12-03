package control;

import data.MeasurementSystem;
import data.ErrorDisplayState;
import data.Currency;

import java.util.ArrayList;
import java.util.List;

public class SettingsManager {
    private static List<SettingsListener> listeners = new ArrayList<>();
    private static MeasurementSystem measurementSystem = MeasurementSystem.METRIC;
    private static ErrorDisplayState errorDisplayState = ErrorDisplayState.POPUP_AND_LOG;
    private static Currency currency = Currency.EURO;

    public static MeasurementSystem getMeasurementSystem() {
        return measurementSystem;
    }

    public static void setMeasurementSystem(MeasurementSystem system) {
        measurementSystem = system;
        notifyListeners();
    }

    public static ErrorDisplayState getErrorDisplayState() {
        return errorDisplayState;
    }

    public static void setShowErrorPopups(ErrorDisplayState state) {
        errorDisplayState = state;
    }

    public static Currency getCurrency() {
        return currency;
    }

    public static void setCurrency(Currency selectedCurrency) {
        currency = selectedCurrency;
    }

    public static void registerListener(SettingsListener listener) {
        listeners.add(listener);
    }

    private static void notifyListeners() {
        for (SettingsListener listener : listeners) {
            listener.onSettingsChanged();
        }
    }

    public interface SettingsListener {
        void onSettingsChanged();
    }
}