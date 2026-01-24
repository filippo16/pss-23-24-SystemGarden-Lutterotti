package it.unibo.systemgarden.model.api.strategy;

import it.unibo.systemgarden.model.utils.IrrigationAdvice;


/**
 * Strategy Interface for irrigation advisors.
*/
public interface AdvisorStrategy {
     /**
     * Analyzes the current conditions and returns an irrigation advice.
     * @param humidity    the current humidity percentage (0-100)
     * @param temperature the current temperature in Celsius
     * @return the irrigation advice based on the conditions
    */
    IrrigationAdvice getAdvice(double humidity, double temperature);
}
