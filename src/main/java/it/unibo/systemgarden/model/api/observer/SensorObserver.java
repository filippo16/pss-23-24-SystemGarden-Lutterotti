package it.unibo.systemgarden.model.api.observer;

import it.unibo.systemgarden.model.utils.SensorType;

/**
 * Observer Interface for sensors.
 * It is used to update sensor data in the view.
*/
public interface SensorObserver {
    
    /**
     * Called when a sensor value is updated.
     * @param areaId the ID of the area where the sensor is located
     * @param sensorId the ID of the sensor
     * @param newValue the new value of the sensor
     * @param type the type of the sensor {@link SensorType}
     */
    void onSensorUpdate( String areaId, String sensorId, double newValue, SensorType type );
}
