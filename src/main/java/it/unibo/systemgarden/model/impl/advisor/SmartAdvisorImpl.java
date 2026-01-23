package it.unibo.systemgarden.model.impl.advisor;

import java.util.Set;

import it.unibo.systemgarden.model.api.SmartAdvisor;
import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;
import it.unibo.systemgarden.model.utils.SensorType;

public class SmartAdvisorImpl implements SmartAdvisor {
    
    private AdvisorStrategy strategy;

    private double currentTemperature = 0; 
    private double currentHumidity = 0; 
    private boolean active = false;

    public SmartAdvisorImpl() {
        this.strategy = new SensorAdvisor();
    }

    @Override
    public void setStrategy(AdvisorStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public IrrigationAdvice getAdvice(Set<SensorType> sensorTypes, double newValue, SensorType type) {
        final IrrigationAdvice newAdvice;

        if (strategy == null && active == false) {
            return null;
        }

        final boolean hasTemp = sensorTypes.contains(SensorType.TEMPERATURE);
        final boolean hasHum  = sensorTypes.contains(SensorType.HUMIDITY);

        if (!hasTemp || !hasHum) {
            newAdvice = IrrigationAdvice.INSUFFICIENT_DATA;
        } else {

            if (type == SensorType.TEMPERATURE) {
                currentTemperature = newValue;
            } else if (type == SensorType.HUMIDITY) {
                currentHumidity = newValue;
            }

            newAdvice = strategy.getAdvice(currentHumidity, currentTemperature);
        }

        return newAdvice;
    }

    public void activeAdvisor(double temperature, double humidity) {
        this.currentTemperature = temperature;
        this.currentHumidity = humidity;
        this.active = true;
    }


}
