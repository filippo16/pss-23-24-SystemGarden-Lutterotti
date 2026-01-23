package it.unibo.systemgarden.model.api;

import java.util.Set;

import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;
import it.unibo.systemgarden.model.utils.SensorType;

public interface SmartAdvisor {

    void setStrategy(AdvisorStrategy strategy);

    IrrigationAdvice getAdvice(Set<SensorType> sensorTypes, double newValue, SensorType type);
    
}
