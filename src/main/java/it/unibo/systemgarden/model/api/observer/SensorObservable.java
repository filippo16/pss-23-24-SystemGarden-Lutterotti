package it.unibo.systemgarden.model.api.observer;

import it.unibo.systemgarden.model.utils.SensorType;

public interface SensorObservable {
    
    /**
     * Adds an observer to the list of observers to receive updates.
     * @param observer the observer to be added {@link SensorObserver}
     */
    void addObserver(SensorObserver observer);

    /**
     * Removes an observer from the list of observers to stop receiving updates.
     * @param observer the observer to be removed {@link SensorObserver}
     */
    void removeObserver(SensorObserver observer);

    /**
     * Notifies all observers about a sensor value update.
     * @param areaId the ID of the area where the sensor is located
     * @param sensorId the ID of the sensor
     * @param value the new value of the sensor
     * @param type the type of the sensor {@link SensorType}
     */
    void notifyObservers( String areaId, String sensorId, double value, SensorType type );
}
