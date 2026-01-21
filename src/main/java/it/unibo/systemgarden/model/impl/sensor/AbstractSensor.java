package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.observer.SensorObservable;
import it.unibo.systemgarden.model.api.observer.SensorObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract base class for sensors that implements Observable pattern.
 */
public abstract class AbstractSensor implements Sensor, SensorObservable {

    private final String id;
    private final String name;
    private final List<SensorObserver> observers;
    protected double currentValue;

    protected AbstractSensor(final String name) {
        this.id = "SENS-" + new Random().nextInt( 100000 );
        this.name = name;
        this.observers = new ArrayList<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double readData() {
        return this.currentValue;
    }

    @Override
    public void addObserver(final SensorObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(final SensorObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(  ) {
        observers.forEach( obs -> obs.onSensorUpdate(  ) );
    }
}
