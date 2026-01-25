package it.unibo.systemgarden.mock;

import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.utils.SensorType;

public class TestSensorObserver implements SensorObserver {

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
