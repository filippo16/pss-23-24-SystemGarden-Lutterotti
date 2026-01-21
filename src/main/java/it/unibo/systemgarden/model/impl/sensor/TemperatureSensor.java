package it.unibo.systemgarden.model.impl.sensor;

public class TemperatureSensor extends AbstractSensor {

    private static final String TYPE = "Temperature";

    public TemperatureSensor(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    // ONLY TEST METHOD
    public void setTemperature(double temperature) {
        this.currentValue = temperature;
        notifyObservers();
    }
    
}
