package control;

import data.MeasurementUnit;
import data.ErrorDisplayState;
import data.Currency;

import java.util.ArrayList;
import java.util.List;

public class SettingsManager {
    private static List<SettingsListener> listeners = new ArrayList<>();
    private static MeasurementUnit measurementUnit = MeasurementUnit.METRIC;
    private static ErrorDisplayState errorDisplayState = ErrorDisplayState.POPUP_AND_LOG;
    private static Currency currency = Currency.EURO;

    public static MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public static void setMeasurementUnit(MeasurementUnit unit) {
        measurementUnit = unit;
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