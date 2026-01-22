package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.utils.SensorType;

public class TemperatureSensor extends AbstractSensor {

    private static final SensorType TYPE = SensorType.TEMPERATURE;

    public TemperatureSensor(String name) {
        super(name);
    }

    @Override
    public SensorType getType() {
        return TYPE;
    }

    // ONLY TEST METHOD
    public void setTemperature( double temperature ) {
        this.currentValue = temperature;
        notifyObservers();
    }
    
}
