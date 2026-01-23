package it.unibo.systemgarden.model.api;

import java.util.List;

import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;

public interface SmartAdvisor {

    void setStrategy(AdvisorStrategy strategy);

    IrrigationAdvice getAdvice(List<Sensor> sensors);
    
}
