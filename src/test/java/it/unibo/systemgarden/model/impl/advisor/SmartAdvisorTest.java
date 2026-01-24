package it.unibo.systemgarden.model.impl.advisor;

import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SensorAdvisor.
 * Tests the strategy implementation with various humidity and temperature
 * combinations.
 */
class SensorAdvisorTest {

    private AdvisorStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new SensorAdvisor();
    }

    @Test
    void testStrategyCreation() {
        assertNotNull(strategy);
    }

    @Test
    void testIrrigateNow() {

        IrrigationAdvice advice = strategy.getAdvice(25.0, 30.0);
        assertEquals(IrrigationAdvice.IRRIGATE_NOW, advice);
    }

    @Test
    void testNoIrrigationNeeded() {

        IrrigationAdvice advice = strategy.getAdvice(45.0, 25.0);
        assertEquals(IrrigationAdvice.NO_IRRIGATION_NEEDED, advice);
    }

    @Test
    void testOptimalCondition() {

        IrrigationAdvice advice = strategy.getAdvice(70.0, 25.0);
        assertEquals(IrrigationAdvice.OPTIMAL_CONDITIONS, advice);
    }

    @Test
    void testNegativeTemperature() {

        IrrigationAdvice advice = strategy.getAdvice(50.0, -5.0);
        assertEquals(IrrigationAdvice.NO_IRRIGATION_NEEDED, advice);
    }
}
