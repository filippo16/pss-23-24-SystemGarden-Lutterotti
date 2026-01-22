package it.unibo.systemgarden.model.api;

import it.unibo.systemgarden.model.utils.SensorType;

public interface Sensor {


    String getId();

    String getName();

    SensorType getType();

    double readData();
    
}
