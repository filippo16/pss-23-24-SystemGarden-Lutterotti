package it.unibo.systemgarden.model.api;

import it.unibo.systemgarden.model.utils.SensorType;


/**
 * Interface for a sensor.
*/
public interface Sensor {

    /**
     * @return the unique identifier of this sensor
    */
    String getId();

    /**
     * @return the name of this sensor
    */
    String getName();

    /**
     * @return the type of this sensor
    */
    SensorType getType();

    /**
     * @return the latest data read by this sensor
    */
    double readData();

    /**
     * Refreshes the sensor data.
     * It simulates reading new data from the physical sensor.
     * @param areaId the area ID
     * @param type the sensor type
    */
    void refresh(  String areaId, SensorType type );
    
}
