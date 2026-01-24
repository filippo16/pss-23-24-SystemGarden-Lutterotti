package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.mock.TestAdvisorObserver;
import it.unibo.systemgarden.mock.TestSensorObserver;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.impl.sensor.HumiditySensor;
import it.unibo.systemgarden.model.impl.sensor.TemperatureSensor;
import it.unibo.systemgarden.model.utils.SensorType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

/**
 * Test class for GreenAreaImpl.
 */
class GreenAreaImplTest {
    
    private GreenArea area;
    private TestSensorObserver mockSensorObserver = new TestSensorObserver();
    private TestAdvisorObserver mockAdvisorObserver = new TestAdvisorObserver();

    @BeforeEach
    void setUp() {
        area = new GreenAreaImpl( "Giardino Nord", "Bologna" );
    }

    @Test
    void testGreenAreaCreation() {
        assertNotNull( area.getId() );
        assertTrue( area.getId().startsWith( "AREA-" ) );
        assertEquals( "Giardino Nord", area.getName() );
        assertEquals( "Bologna", area.getLocation().getCity() );
        assertTrue( area.getSectors().isEmpty() );
    }

    @Test
    void testAddSector() {
        Sector sector = area.addSector( "AREA-1", "Prato Est" );

        assertNotNull( sector );
        assertEquals( 1, area.getSectors().size() );
        assertTrue( area.getSectors().stream().anyMatch( s -> s.getName().equals("Prato Est") ) ); 
    }

    @Test
    void testRemoveSector() {
        Sector sector = area.addSector( "AREA-1", "Prato Est" );
        boolean removed = area.removeSector( sector.getId() );

        assertTrue( removed );
        assertTrue( area.getSectors().isEmpty() );
    }

    @Test
    void testRemoveSectorNotFound() {
        boolean removed = area.removeSector( "NOT_EXIST_ID" );
        assertFalse( removed );
    }

    @Test
    void testIrrigateSector() {
        Sector sector = area.addSector( "AREA-1", "Prato Est" );
        Sector irrigated = area.irrigateSector( sector.getId() );

        assertNotNull( irrigated );
        assertTrue( irrigated.isIrrigating() );
    }

    @Test
    void testStopSector() {
        Sector sector = area.addSector( "AREA-1", "Prato Est" );
        area.irrigateSector( sector.getId() );
        Sector stopped = area.stopSector( sector.getId() );

        assertNotNull( stopped );
        assertFalse( stopped.isIrrigating() );
    }

    @Test
    void testUpdateSectorSchedule() {
        Sector sector = area.addSector( "AREA-1", "Prato Est" );
        List<Integer> days = List.of(1, 3, 5);
        LocalTime startTime = LocalTime.of(8, 0);

        Sector updated = area.updateSectorSchedule( sector.getId(), startTime, 30, days );

        assertNotNull( updated );
        assertNotNull( updated.getSchedule() );
    }

    @Test
    void testAddSensor() throws ActionMethodException {
        Sensor sensor = new HumiditySensor( "Umidità Giardino" );
        area.addSensor( sensor, mockSensorObserver );

        assertEquals( 1, area.getSensors().size() );
    }

    @Test
    void testAddSensorMaxLimit() throws ActionMethodException {
        Sensor sensor1 = new HumiditySensor( "Umidità 1" );
        Sensor sensor2 = new TemperatureSensor( "Temperatura 1" );
        Sensor sensor3 = new HumiditySensor( "Umidità 2" );

        area.addSensor( sensor1, mockSensorObserver );
        area.addSensor( sensor2, mockSensorObserver );

        assertThrows(ActionMethodException.class, () -> {
            area.addSensor( sensor3, mockSensorObserver );
        });
    }

    @Test
    void testRemoveSensor() throws ActionMethodException {
        Sensor sensor = new HumiditySensor( "Umidità" );
        area.addSensor( sensor, mockSensorObserver );

        boolean removed = area.removeSensor( sensor.getId(), mockSensorObserver );

        assertTrue( removed );
        assertTrue( area.getSensors().isEmpty() );
    }

}
