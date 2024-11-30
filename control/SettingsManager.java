package control;

import data.MeasurementUnit;

import java.util.ArrayList;
import java.util.List;

public class SettingsManager {
    private static List<SettingsListener> listeners = new ArrayList<>();
    private static MeasurementUnit measurementUnit = MeasurementUnit.METRIC;
    private static boolean showErrorPopupsEnabled = true; // Default to detailed errors
    private static String currency = "EUR (€)";

    public static MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public static void setMeasurementUnit(MeasurementUnit unit) {
        measurementUnit = unit;
        notifyListeners();
    }

    public static boolean isShowErrorPopupsEnabled() {
        return showErrorPopupsEnabled;
    }

    public static void setShowErrorPopups(boolean enabled) {
        showErrorPopupsEnabled = enabled;
    }

    public static String getCurrency() {
        return currency;
    }

    public static void setCurrency(String selectedCurrency) {
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