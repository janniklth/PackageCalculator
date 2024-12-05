package test;

import data.MeasurementSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides test cases for the {@link MeasurementSystem} enum.
 *
 * <p>Tests the methods for retrieving unit symbols, converting between measurement systems, and
 * finding a {@link MeasurementSystem} by its display string.</p>
 *
 * <p>Contains the following test cases:</p>
 * <ul>
 *     <li>{@link #testConvertToDisplayString()} - Tests the generation of the display string</li>
 *     <li>{@link #testFromDisplayString()} - Tests finding a measurement system by its display string</li>
 *     <li>{@link #testConvertLength()} - Tests the conversion of length between systems</li>
 *     <li>{@link #testConvertWeight()} - Tests the conversion of weight between systems</li>
 * </ul>
 *
 * @see MeasurementSystem
 */
public class MeasurementSystemTest {
    /**
     * Tests the generation of the display string.
     */
    @Test
    public void testConvertToDisplayString() {
        assertEquals("Metric (mm + g)", MeasurementSystem.METRIC.convertToDisplayString(), "Metric display string should be 'Metric (mm + g)'");
        assertEquals("Imperial (in + lb)", MeasurementSystem.IMPERIAL.convertToDisplayString(), "Imperial display string should be 'Imperial (in + lb)'");
    }

    /**
     * Tests finding a measurement system by its display string.
     */
    @Test
    public void testFromDisplayString() {
        assertEquals(MeasurementSystem.METRIC, MeasurementSystem.fromDisplayString("Metric (mm + g)"), "Should return METRIC for 'Metric (mm + g)'");
        assertEquals(MeasurementSystem.IMPERIAL, MeasurementSystem.fromDisplayString("Imperial (in + lb)"), "Should return IMPERIAL for 'Imperial (in + lb)'");
        assertThrows(IllegalArgumentException.class, () -> MeasurementSystem.fromDisplayString("Unknown"), "Should throw exception for invalid display string");
    }

    /**
     * Tests the conversion of length between systems.
     */
    @Test
    public void testConvertLength() {
        // Metric to Imperial
        assertEquals(3.94, MeasurementSystem.METRIC.convertLength(100, MeasurementSystem.IMPERIAL, false), 0.01, "100 mm should equal 3.94 inches");
        // Imperial to Metric
        assertEquals(254.0, MeasurementSystem.IMPERIAL.convertLength(10, MeasurementSystem.METRIC, false), 0.01, "10 inches should equal 254 mm");
        // No conversion
        assertEquals(100.0, MeasurementSystem.METRIC.convertLength(100, MeasurementSystem.METRIC, true), "100 mm should equal 100 mm");
    }

    /**
     * Tests the conversion of weight between systems.
     */
    @Test
    public void testConvertWeight() {
        // Metric to Imperial
        assertEquals(2.2, MeasurementSystem.METRIC.convertWeight(1000, MeasurementSystem.IMPERIAL, false), 0.01, "1000 g should equal 2.2 lb");
        // Imperial to Metric
        assertEquals(4535.92, MeasurementSystem.IMPERIAL.convertWeight(10, MeasurementSystem.METRIC, true), 0.01, "10 lb should equal 4535.92 g");
        // No conversion
        assertEquals(100.0, MeasurementSystem.METRIC.convertWeight(100, MeasurementSystem.METRIC, true), "100 g should equal 100 g");
    }
}
