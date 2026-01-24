package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.observer.SensorObservable;
import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.utils.SensorType;

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
    private final Random random;

    protected AbstractSensor(final String name) {
        this.id = "SENS-" + new Random().nextInt( 100000 );
        this.name = name;
        this.observers = new ArrayList<>();
        this.random = new Random();
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
    public void notifyObservers( final String areaId, final String sensorId, final double value, final SensorType type ) {
        observers.forEach( obs -> obs.onSensorUpdate( areaId, sensorId, value, type ) );
    }

    @Override
    public void refresh( final String areaId, final SensorType type ) {
        this.currentValue = generateNewValue();
        notifyObservers(areaId, this.id, this.currentValue, type);
    }

    /**
     * Generates a new value for the sensor.
     * Is a test method to simulate sensor data. 
     * @return the new sensor value
     */
    protected abstract double generateNewValue();

    protected Random getRandom() {
        return this.random;
    }
}
