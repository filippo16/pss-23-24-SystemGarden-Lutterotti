package it.unibo.systemgarden.model.impl.sensor;

public class TemperatureSensor extends AbstractSensor {
    private double temperature;

    private static final String TYPE = "Temperature";

    public TemperatureSensor(String name) {
        super(name);
        this.temperature = 20.0; // Default temperature
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
}
