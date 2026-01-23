package it.unibo.systemgarden.model.api.observer;

import it.unibo.systemgarden.model.utils.SensorType;

public interface SensorObserver {
    
    void onSensorUpdate( String areaId, String sensorId, double newValue, SensorType type );
}
