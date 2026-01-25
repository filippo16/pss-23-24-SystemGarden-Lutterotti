package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.utils.SensorType;

public class HumiditySensor extends AbstractSensor {

    private static final SensorType TYPE = SensorType.HUMIDITY;
    private static final double MIN_HUMIDITY = 30.0;
    private static final double MAX_HUMIDITY = 90.0;
    private static final double VARIATION = 5.0;

    public HumiditySensor(String name) {
        super(name);
    }

    @Override
    public SensorType getType() {
        return TYPE;
    }

    @Override
    protected double generateNewValue() {

        double variation = (getRandom().nextDouble() - 0.5) * VARIATION * 2;
        double newValue = this.currentValue + variation;

        if (newValue < MIN_HUMIDITY) {
            newValue = MIN_HUMIDITY;
        }
        if (newValue > MAX_HUMIDITY) {
            newValue = MAX_HUMIDITY;
        }

        return Math.round(newValue * 10) / 10.0;
    }
    
}
