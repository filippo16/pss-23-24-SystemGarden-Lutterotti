package it.unibo.systemgarden.model.api;


public interface Sensor {


    String getId();

    String getName();

    String getType();

    double readData();

    void setTemperature(double temperature);
    
}
