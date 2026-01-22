package it.unibo.systemgarden.model.api.observer;

public interface SensorObserver {
    
    void onSensorUpdate( String areaId, String sensorId, double newValue);
}
