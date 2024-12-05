package test;

import data.ErrorDisplayState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides test cases for the {@link ErrorDisplayState} enum.
 *
 * <p>Tests methods for converting states to their display strings and for finding a state by its display string.</p>
 *
 * <p>Contains the following test cases:</p>
 * <ul>
 *     <li>{@link #testConvertToDisplayString()} - Tests the generation of the display string</li>
 *     <li>{@link #testFromDisplayString()} - Tests finding a state by its display string</li>
 *     <li>{@link #testInvalidDisplayString()} - Tests handling of invalid display strings</li>
 * </ul>
 *
 * @see ErrorDisplayState
 */
public class ErrorDisplayStateTest {

    /**
     * Tests the generation of the display string.
     */
    @Test
    public void testConvertToDisplayString() {
        assertEquals("Popup + log messages", ErrorDisplayState.POPUP_AND_LOG.convertToDisplayString(),
                "Display string for POPUP_AND_LOG should be 'Popup + log messages'");
        assertEquals("only log messages", ErrorDisplayState.LOG_ONLY.convertToDisplayString(),
                "Display string for LOG_ONLY should be 'only log messages'");
    }

    /**
     * Tests finding a state by its display string.
     */
    @Test
    public void testFromDisplayString() {
        assertEquals(ErrorDisplayState.POPUP_AND_LOG, ErrorDisplayState.fromDisplayString("Popup + log messages"),
                "Should return POPUP_AND_LOG for 'Popup + log messages'");
        assertEquals(ErrorDisplayState.LOG_ONLY, ErrorDisplayState.fromDisplayString("only log messages"),
                "Should return LOG_ONLY for 'only log messages'");
    }

    /**
     * Tests handling of invalid display strings.
     */
    @Test
    public void testInvalidDisplayString() {
        assertThrows(IllegalArgumentException.class,
                () -> ErrorDisplayState.fromDisplayString("Invalid"),
                "Should throw IllegalArgumentException for an invalid display string");
    }
}
