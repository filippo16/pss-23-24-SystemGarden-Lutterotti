package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.utils.SensorType;

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
        final TestObserver observer2 = new TestObserver();

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

    /**
     * Helper class to mock observer notifications.
     */
    private static class TestObserver implements SensorObserver {

        private boolean notified = false;
        private String lastAreaId;
        private String lastSensorId;
        private double lastValue;
        private int notificationCount = 0;
        private SensorType lastSensorType;

        @Override
        public void onSensorUpdate(String areaId, String sensorId, double newValue, SensorType sensorType) {
            this.notified = true;
            this.lastAreaId = areaId;
            this.lastSensorId = sensorId;
            this.lastValue = newValue;
            this.notificationCount++;
            this.lastSensorType = sensorType;
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

        public int getNotificationCount() {
            return notificationCount;
        }

        public SensorType getLastSensorType() {
            return lastSensorType;
        }
    }

}
