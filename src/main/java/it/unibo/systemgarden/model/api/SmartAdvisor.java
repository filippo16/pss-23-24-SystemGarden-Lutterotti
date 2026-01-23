package it.unibo.systemgarden.model.api;

import java.util.List;

import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;
import it.unibo.systemgarden.model.utils.SensorType;

public interface SmartAdvisor {

    void setStrategy(AdvisorStrategy strategy);

    IrrigationAdvice getAdvice(List<SensorType> sensorTypes, double newValue, SensorType type);
    
}
