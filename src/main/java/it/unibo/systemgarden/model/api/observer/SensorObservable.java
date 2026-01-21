package it.unibo.systemgarden.model.api.observer;

public interface SensorObservable {
    
    void addObserver(SensorObserver observer);

    void removeObserver(SensorObserver observer);

    void notifyObservers( );
}
