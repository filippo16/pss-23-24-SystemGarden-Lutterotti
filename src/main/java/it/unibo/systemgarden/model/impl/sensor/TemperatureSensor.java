package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.utils.SensorType;

public class TemperatureSensor extends AbstractSensor {

    private static final SensorType TYPE = SensorType.TEMPERATURE;
    private static final double MIN_TEMP = 5.0;
    private static final double MAX_TEMP = 40.0;
    private static final double VARIATION = 2.0;

    public TemperatureSensor(String name) {
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

        
        if (newValue < MIN_TEMP) {
            newValue = MIN_TEMP;
        }
        if (newValue > MAX_TEMP) {
            newValue = MAX_TEMP;
        }

        return Math.round(newValue * 10) / 10.0;
    }
    
}
