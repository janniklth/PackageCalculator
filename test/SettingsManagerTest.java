package test;

import control.SettingsManager;
import data.MeasurementSystem;
import data.ErrorDisplayState;
import data.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides test cases for the {@link SettingsManager} class.
 *
 * <p>Tests the methods for getting and setting the current {@link MeasurementSystem}, {@link ErrorDisplayState}, and {@link Currency},
 * as well as the registration of listeners to be notified when settings change to reflect the changes in the UI.</p>
 *
 * <p>Contains the following test cases:</p>
 * <ul>
 *     <li> {@link #testDefaultValues()} - Tests the default values of the settings</li>
 *     <li> {@link #testSetAndGetMeasurementSystem()} - Tests the setting and getting of the measurement system</li>
 *     <li> {@link #testSetAndGetErrorDisplayState()} - Tests the setting and getting of the error display state</li>
 *     <li> {@link #testSetAndGetCurrency()} - Tests the setting and getting of the currency</li>
 *     <li> {@link #testListenerNotification()} - Tests the notification of a listener when settings change</li>
 *     <li> {@link #testMultipleListenersNotification()} - Tests the notification of multiple listeners when settings change</li>
 * </ul>
 *
 * @see SettingsManager
 * @see MeasurementSystem
 * @see ErrorDisplayState
 * @see Currency
 */
public class SettingsManagerTest {

    /**
     * Resets the settings to the default values before each test.
     */
    @BeforeEach
    public void resetSettings() {
        SettingsManager.setMeasurementSystem(MeasurementSystem.METRIC);
        SettingsManager.setShowErrorPopups(ErrorDisplayState.POPUP_AND_LOG);
        SettingsManager.setCurrency(Currency.EURO);
    }

    /**
     * Tests the default values of the settings.
     */
    @Test
    public void testDefaultValues() {
        assertEquals(MeasurementSystem.METRIC, SettingsManager.getMeasurementSystem(), "Default Measurement System should be METRIC");
        assertEquals(ErrorDisplayState.POPUP_AND_LOG, SettingsManager.getErrorDisplayState(), "Default Error Display State should be POPUP_AND_LOG");
        assertEquals(Currency.EURO, SettingsManager.getCurrency(), "Default Currency should be EURO");
    }

    /**
     * Tests the setting and getting of the measurement system.
     */
    @Test
    public void testSetAndGetMeasurementSystem() {
        SettingsManager.setMeasurementSystem(MeasurementSystem.IMPERIAL);
        assertEquals(MeasurementSystem.IMPERIAL, SettingsManager.getMeasurementSystem(), "Measurement System should be set to IMPERIAL");
    }

    /**
     * Tests the setting and getting of the error display state.
     */
    @Test
    public void testSetAndGetErrorDisplayState() {
        SettingsManager.setShowErrorPopups(ErrorDisplayState.LOG_ONLY);
        assertEquals(ErrorDisplayState.LOG_ONLY, SettingsManager.getErrorDisplayState(), "Error Display State should be set to POPUP_ONLY");
    }

    /**
     * Tests the setting and getting of the currency.
     */
    @Test
    public void testSetAndGetCurrency() {
        SettingsManager.setCurrency(Currency.US_DOLLAR);
        assertEquals(Currency.US_DOLLAR, SettingsManager.getCurrency(), "Currency should be set to USD");
    }

    /**
     * Tests the notification of a listener when settings change.
     */
    @Test
    public void testListenerNotification() {
        // Checking a listener is notified when settings change through registering a listener and changing the settings
        AtomicBoolean wasNotified = new AtomicBoolean(false);
        SettingsManager.registerListener(() -> wasNotified.set(true));
        SettingsManager.setMeasurementSystem(MeasurementSystem.IMPERIAL);
        assertTrue(wasNotified.get(), "Listener should be notified when settings change");
    }

    /**
     * Tests the notification of multiple listeners when settings change.
     */
    @Test
    public void testMultipleListenersNotification() {
        // Create two listeners and register them
        AtomicBoolean listener1Notified = new AtomicBoolean(false);
        AtomicBoolean listener2Notified = new AtomicBoolean(false);
        SettingsManager.registerListener(() -> listener1Notified.set(true));
        SettingsManager.registerListener(() -> listener2Notified.set(true));

        // Change the settings
        SettingsManager.setMeasurementSystem(MeasurementSystem.IMPERIAL);

        // Assert that both listeners were notified
        assertTrue(listener1Notified.get(), "Listener 1 should be notified when settings change");
        assertTrue(listener2Notified.get(), "Listener 2 should be notified when settings change");
    }
}