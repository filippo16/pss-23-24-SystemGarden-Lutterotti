package it.unibo.systemgarden.model.api.observer;

import it.unibo.systemgarden.model.utils.SensorType;

public interface SensorObservable {
    
    void addObserver(SensorObserver observer);

    void removeObserver(SensorObserver observer);

    void notifyObservers( String areaId, String sensorId, double value, SensorType type );
}
