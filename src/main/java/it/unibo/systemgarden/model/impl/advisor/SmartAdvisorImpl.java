package it.unibo.systemgarden.model.impl.advisor;

import java.util.List;

import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.SmartAdvisor;
import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;
import it.unibo.systemgarden.model.utils.SensorType;

public class SmartAdvisorImpl implements SmartAdvisor {
    
    private AdvisorStrategy strategy;

    private double currentTemperature = 0; 
    private double currentHumidity = 0; 

    public SmartAdvisorImpl(AdvisorStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void setStrategy(AdvisorStrategy strategy) {
        this.strategy = strategy;
    }


    public IrrigationAdvice getAdvice(List<Sensor> sensors) {
        final IrrigationAdvice newAdvice;

        if (strategy == null) {
            return null;
        }

        final boolean hasTemp = sensors != null && sensors.stream()
            .anyMatch( s -> s.getType() == SensorType.TEMPERATURE );
        final boolean hasHum = sensors != null && sensors.stream()
            .anyMatch( s -> s.getType() == SensorType.HUMIDITY );

        if (!hasTemp || !hasHum) {
            newAdvice = IrrigationAdvice.INSUFFICIENT_DATA;
        } else {

            newAdvice = strategy.getAdvice(currentHumidity, currentTemperature);
        }

        return newAdvice;
    }


}
