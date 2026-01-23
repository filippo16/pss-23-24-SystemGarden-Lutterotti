package it.unibo.systemgarden.model.api;

import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;

public interface SmartAdvisor {

    void setStrategy(AdvisorStrategy strategy);
    
}
