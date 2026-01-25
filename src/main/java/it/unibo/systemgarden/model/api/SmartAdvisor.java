package it.unibo.systemgarden.model.api;

import java.util.Set;

import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;
import it.unibo.systemgarden.model.utils.SensorType;


/**
 * Interface for a smart advisor that provides irrigation advice bassed on strategy.
*/
public interface SmartAdvisor {

    /**
     * Sets the advisor strategy.
     * @param strategy the advisor strategy to set
    */
    void setStrategy( AdvisorStrategy strategy );

    /**
     * Gets irrigation advice based on selected strategy.
     * @param sensorTypes the set of sensor types involved
     * @param newValue the new sensor value
     * @param type the type of the sensor that triggered the advice
     * @return the irrigation advice
    */
    IrrigationAdvice getAdvice( Set<SensorType> sensorTypes, double newValue, SensorType type );
    
}
