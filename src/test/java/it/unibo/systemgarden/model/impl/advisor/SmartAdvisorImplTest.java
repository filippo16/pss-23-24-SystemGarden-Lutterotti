package it.unibo.systemgarden.model.impl.advisor;

import it.unibo.systemgarden.model.api.SmartAdvisor;
import it.unibo.systemgarden.model.api.strategy.AdvisorStrategy;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;
import it.unibo.systemgarden.model.utils.SensorType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartAdvisorImpl.
 * Tests the advisor functionality with different sensor combinations and
 * conditions.
 */
class SmartAdvisorImplTest {

    private SmartAdvisor advisor;

    @BeforeEach
    void setUp() {
        advisor = new SmartAdvisorImpl();
    }

    @Test
    void testAdvisorCreation() {
        assertNotNull(advisor);
    }

    @Test
    void testInsufficientData() {
        Set<SensorType> sensors = EnumSet.of( SensorType.TEMPERATURE );

        IrrigationAdvice advice = advisor.getAdvice( sensors, 25.0, 
            SensorType.TEMPERATURE 
        );

        assertEquals( IrrigationAdvice.INSUFFICIENT_DATA, advice );
    }


    /**
     * Test insufficient data if area don't have sensors.
     */
    @Test
    void testInsufficientDataWithNullSensorSet() {
        IrrigationAdvice advice = advisor.getAdvice( null, 25.0, 
            SensorType.TEMPERATURE
        );

        assertEquals( IrrigationAdvice.INSUFFICIENT_DATA, advice );
    }

    @Test
    void testIrrigateNowWithHighTemperature() {
        Set<SensorType> sensors = EnumSet.of( SensorType.TEMPERATURE, SensorType.HUMIDITY );


        advisor.getAdvice( sensors, 50.0, SensorType.HUMIDITY );

        IrrigationAdvice advice = advisor.getAdvice( sensors, 35.0, 
            SensorType.TEMPERATURE 
        );

        assertEquals( IrrigationAdvice.IRRIGATE_NOW, advice );
    }


    /**
     * Verify that setting a custom strategy works as expected.
     */
    @Test
    void testSetCustomStrategy() {
        // Returns always IRRIGATE_NOW 
        AdvisorStrategy customStrategy = (humidity, temperature) -> IrrigationAdvice.IRRIGATE_NOW;

        advisor.setStrategy(customStrategy);

        Set<SensorType> sensors = EnumSet.of( SensorType.TEMPERATURE, SensorType.HUMIDITY );
        advisor.getAdvice( sensors, 20.0, 
            SensorType.TEMPERATURE 
        );
        
        IrrigationAdvice advice = advisor.getAdvice( sensors, 80.0, 
            SensorType.HUMIDITY 
        );

        
        assertEquals(IrrigationAdvice.IRRIGATE_NOW, advice);
    }

    @Test
    void testSetNullStrategy() {
        advisor.setStrategy(null);

        Set<SensorType> sensors = EnumSet.of( SensorType.TEMPERATURE, SensorType.HUMIDITY );
        IrrigationAdvice advice = advisor.getAdvice(sensors, 25.0, SensorType.TEMPERATURE);

        assertNull(advice);
    }

    /**
     * Test advice update after manual multiple sensor readings.
     */
    @Test
    void testAdviceUpdateAfterMultipleSensorReadings() {
        Set<SensorType> sensors = EnumSet.of( SensorType.TEMPERATURE, SensorType.HUMIDITY );

        advisor.getAdvice( sensors, 50.0, SensorType.HUMIDITY );
        advisor.getAdvice( sensors, 25.0, SensorType.TEMPERATURE );

        IrrigationAdvice advice = advisor.getAdvice( sensors, 15.0, SensorType.HUMIDITY) ;

        assertEquals( IrrigationAdvice.IRRIGATE_NOW, advice );

        advice = advisor.getAdvice( sensors, 80.0, SensorType.HUMIDITY );

        assertEquals( IrrigationAdvice.OPTIMAL_CONDITIONS, advice );
    }
}

