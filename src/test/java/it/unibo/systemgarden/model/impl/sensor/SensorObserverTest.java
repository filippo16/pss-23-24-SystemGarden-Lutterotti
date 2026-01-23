package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.api.observer.SensorObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Observer pattern in sensors.
 */
class SensorObserverTest {

    private AbstractSensor sensor;
    private TestObserver observer;

    @BeforeEach
    void setUp() {
        sensor = new HumiditySensor("Test Sensor");
        observer = new TestObserver();
    }

    @Test
    void testAddObserver() {
        sensor.addObserver(observer);

        sensor.refresh("AREA-001");

        assertTrue(observer.wasNotified());
    }

    @Test
    void testObserverReceivesCorrectData() {
        sensor.addObserver(observer);

        sensor.refresh("AREA-123");

        assertEquals("AREA-123", observer.getLastAreaId());
        assertEquals(sensor.getId(), observer.getLastSensorId());
        assertEquals(sensor.readData(), observer.getLastValue());
    }

    @Test
    void testRemoveObserver() {
        sensor.addObserver(observer);
        sensor.removeObserver(observer);

        sensor.refresh("AREA-001");

        assertFalse(observer.wasNotified());
    }

    /**
     * Helper class to mock observer notifications.
     */
    private static class TestObserver implements SensorObserver {

        private boolean notified = false;
        private String lastAreaId;
        private String lastSensorId;
        private double lastValue;

        @Override
        public void onSensorUpdate(String areaId, String sensorId, double newValue) {
            this.notified = true;
            this.lastAreaId = areaId;
            this.lastSensorId = sensorId;
            this.lastValue = newValue;
        }

        public boolean wasNotified() {
            return notified;
        }

        public String getLastAreaId() {
            return lastAreaId;
        }

        public String getLastSensorId() {
            return lastSensorId;
        }

        public double getLastValue() {
            return lastValue;
        }
    }

}
