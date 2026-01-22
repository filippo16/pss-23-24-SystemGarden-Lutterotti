package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.factory.SensorFactory;
import it.unibo.systemgarden.model.utils.SensorType;

public class SensorFactoryImpl implements SensorFactory {
    
    @Override
    public Sensor createSensor(final String name, final SensorType type) {;
        return switch (type) {
            case HUMIDITY -> new TemperatureSensor(name);
            case TEMPERATURE -> new TemperatureSensor(name);
        };
    }

}
