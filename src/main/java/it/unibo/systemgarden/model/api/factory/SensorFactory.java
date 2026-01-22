package it.unibo.systemgarden.model.api.factory;

import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.utils.SensorType;

/**
 * Factory interface for creating sensors.
 */
public interface SensorFactory {

    /**
     * Creates a sensor of the specified type.
     * 
     * @param name the sensor name
     * @param type the sensor type
     * @return the created sensor
    */
    Sensor createSensor(String name, SensorType type);
}