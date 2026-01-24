package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.mock.TestSensorObserver;
import it.unibo.systemgarden.model.utils.SensorType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Observer pattern in sensors.
 */
class SensorObserverTest {

    private AbstractSensor sensor;
    private TestSensorObserver observer;

    @BeforeEach
    void setUp() {
        sensor = new HumiditySensor("Test Sensor");
        observer = new TestSensorObserver();
    }

    @Test
    void testAddObserver() {
        sensor.addObserver(observer);

        sensor.refresh("AREA-001", SensorType.HUMIDITY);

        assertTrue(observer.wasNotified());
    }

    @Test
    void testObserverReceivesCorrectData() {
        sensor.addObserver(observer);

        sensor.refresh("AREA-123", SensorType.HUMIDITY);

        assertEquals("AREA-123", observer.getLastAreaId());
        assertEquals(sensor.getId(), observer.getLastSensorId());
        assertEquals(sensor.readData(), observer.getLastValue());
        assertEquals(SensorType.HUMIDITY, observer.getLastSensorType());
    }

    @Test
    void testRemoveObserver() {
        sensor.addObserver(observer);
        sensor.removeObserver(observer);

        sensor.refresh("AREA-001", SensorType.HUMIDITY);

        assertFalse(observer.wasNotified());
    }

    @Test
    void testMultipleObservers() {
        final TestSensorObserver observer2 = new TestSensorObserver();

        sensor.addObserver(observer);
        sensor.addObserver(observer2);

        sensor.refresh("AREA-001", SensorType.TEMPERATURE);

        assertTrue(observer.wasNotified());
        assertTrue(observer2.wasNotified());
        assertEquals(SensorType.TEMPERATURE, observer.getLastSensorType());
        assertEquals(SensorType.TEMPERATURE, observer2.getLastSensorType());
    }

    @Test
    void testObserverNotAddedTwice() {
        sensor.addObserver(observer);
        sensor.addObserver(observer);

        sensor.refresh("AREA-001", SensorType.HUMIDITY);

        assertEquals(1, observer.getNotificationCount());
    }
}
