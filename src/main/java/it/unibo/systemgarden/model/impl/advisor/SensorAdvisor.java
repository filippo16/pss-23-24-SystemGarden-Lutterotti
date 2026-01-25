package it.unibo.systemgarden.model.impl.advisor;

import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;

public class SensorAdvisor implements AdvisorStrategy {

    private static final double HUMIDITY_LOW = 30.0;
    private static final double HUMIDITY_OPTIMAL = 60.0;
    private static final double TEMP_HIGH = 30.0;
    
    @Override
    public IrrigationAdvice getAdvice(double humidity, double temperature) {
        

        if (humidity < HUMIDITY_LOW || temperature > TEMP_HIGH) {
            return IrrigationAdvice.IRRIGATE_NOW;
        }
        
    
        if (humidity < HUMIDITY_OPTIMAL) {
            return IrrigationAdvice.NO_IRRIGATION_NEEDED;
        }
        

        return IrrigationAdvice.OPTIMAL_CONDITIONS;
    }
    
}
