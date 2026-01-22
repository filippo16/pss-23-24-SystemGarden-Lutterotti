package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.utils.SensorType;

public class HumiditySensor extends AbstractSensor {

    private static final SensorType TYPE = SensorType.HUMIDITY;

    public HumiditySensor(String name) {
        super(name);
    }

    @Override
    public SensorType getType() {
        return TYPE;
    }

    // ONLY TEST METHOD
    public void setHumidity( double humidity ) {
        this.currentValue = humidity;
        notifyObservers();
    }

    @Override
    public void setTemperature(double temperature) {

    }
    
}
