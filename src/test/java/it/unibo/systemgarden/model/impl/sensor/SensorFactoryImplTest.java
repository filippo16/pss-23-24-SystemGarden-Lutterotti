package it.unibo.systemgarden.model.impl.sensor;

import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.factory.SensorFactory;
import it.unibo.systemgarden.model.utils.SensorType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SensorFactoryImpl.
*/
class SensorFactoryImplTest {

    private SensorFactory factory;

    @BeforeEach
    void setUp() {
        factory = new SensorFactoryImpl();
    }

    @Test
    void testCreateHumiditySensor() {
        final Sensor sensor = factory.createSensor( "Umidità", SensorType.HUMIDITY );

        assertNotNull( sensor );
        assertEquals( "Umidità", sensor.getName() );
        assertEquals( SensorType.HUMIDITY, sensor.getType() );
    }

    @Test
    void testCreateTemperatureSensor() {
        final Sensor sensor = factory.createSensor( "Temperatura", SensorType.TEMPERATURE );

        assertNotNull( sensor );
        assertEquals( "Temperatura", sensor.getName() );
        assertEquals( SensorType.TEMPERATURE, sensor.getType() );
    }

    @Test
    void testCreatedSensorsAreCorrectType() {
        final Sensor humidity = factory.createSensor( "Umidità", SensorType.HUMIDITY );
        final Sensor temperature = factory.createSensor( "Temperatura", SensorType.TEMPERATURE );

        assertTrue( humidity instanceof HumiditySensor );
        assertTrue( temperature instanceof TemperatureSensor );
    }

}
