package it.unibo.systemgarden.model.impl.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Implementation of Sensor. ({@link HumiditySensor}, {@link TemperatureSensor}).
 * Test the generation data methods.
*/
class SensorImplTest {

    private HumiditySensor hSensor;
    private TemperatureSensor tSensor;
    
    @BeforeEach
    void setUp() {
        hSensor = new HumiditySensor("Umidità Test");
        tSensor = new TemperatureSensor("Temperatura Test");    
    }

    @Test
    void testGenerateHumidityData() {
        for (int i = 0; i < 10; i++) {
            double humidity = hSensor.generateNewValue();
            assert(humidity >= 30.0 && humidity <= 90.0);
        }
    }

    @Test
    void testGenerateTemperatureData() {
        for (int i = 0; i < 10; i++) {
            double temperature = tSensor.generateNewValue();
            assert(temperature >= 5.0 && temperature <= 40.0);
        }
    }
    
}
