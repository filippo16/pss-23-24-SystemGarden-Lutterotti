package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.mock.TestAdvisorObserver;
import it.unibo.systemgarden.mock.TestSensorObserver;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.observer.AdvisorObservable;
import it.unibo.systemgarden.model.impl.sensor.HumiditySensor;
import it.unibo.systemgarden.model.impl.sensor.TemperatureSensor;

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
    private TestSensorObserver mockSensorObserver;
    private TestAdvisorObserver mockAdvisorObserver;

    @BeforeEach
    void setUp() {
        area = new GreenAreaImpl( "Giardino Nord", "Bologna" );
        mockSensorObserver = new TestSensorObserver();
        mockAdvisorObserver = new TestAdvisorObserver();
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




    @Test
    void testAddAdvisorObserver() {
        
        ((AdvisorObservable) area).addAdvisorObserver( mockAdvisorObserver );
        ((AdvisorObservable) area).notifyAdvisorObservers( area.getId(), "Test Advice" );

        assertTrue( mockAdvisorObserver.wasNotified() );
        assertEquals( "Test Advice", mockAdvisorObserver.getLastAdviceTitle() );
    }

    @Test
    void testRemoveAdvisorObserver() {

        ((AdvisorObservable) area).addAdvisorObserver( mockAdvisorObserver );
        ((AdvisorObservable) area).removeAdvisorObserver( mockAdvisorObserver );
        ((AdvisorObservable) area).notifyAdvisorObservers( area.getId(), "Test Advice" );

        assertFalse( mockAdvisorObserver.wasNotified() );
    }

    @Test
    void testObserverNotAddedTwice() {

        ((AdvisorObservable) area).addAdvisorObserver( mockAdvisorObserver );
        ((AdvisorObservable) area).addAdvisorObserver( mockAdvisorObserver ); 
        ((AdvisorObservable) area).notifyAdvisorObservers( area.getId(), "Test" );

        assertEquals( 1, mockAdvisorObserver.getNotificationCount() );
    }
    

    /**
     * Test observing multiple green areas with a single observer (like ViewImpl).
     */
    @Test
    void testObserveMultipleAreas() {
        
        GreenArea area1 = new GreenAreaImpl("Giardino Nord", "Bologna");
        GreenArea area2 = new GreenAreaImpl("Giardino Sud", "Roma");
        GreenArea area3 = new GreenAreaImpl("Parco Est", "Milano");
        

        ((AdvisorObservable) area1).addAdvisorObserver( mockAdvisorObserver );
        ((AdvisorObservable) area2).addAdvisorObserver( mockAdvisorObserver );
        ((AdvisorObservable) area3).addAdvisorObserver( mockAdvisorObserver );
        

        ((AdvisorObservable) area1).notifyAdvisorObservers( area1.getId(), 
        "Consiglio Area 1");
        ((AdvisorObservable) area2).notifyAdvisorObservers( area2.getId(), 
        "Consiglio Area 2");
        ((AdvisorObservable) area3).notifyAdvisorObservers( area3.getId(), 
        "Consiglio Area 3");
        

        assertEquals( 3, mockAdvisorObserver.getNotificationCount() );
        

        assertEquals( area3.getId(), mockAdvisorObserver.getLastAreaId() );
        assertEquals( "Consiglio Area 3", mockAdvisorObserver.getLastAdviceTitle() );
    }


    /**
     * Test that a sensor update in one area does not notify observers of other areas.
     * For the verification i used the two observers to check 
     * which onSensorUpdate in the specific area was called
     */
    @Test
    void testSensorUpdateDoesNotAffectOtherAreas() throws ActionMethodException {

        GreenArea area1 = new GreenAreaImpl("Giardino Nord", "Bologna");
        GreenArea area2 = new GreenAreaImpl("Giardino Sud", "Roma");
        

        TestAdvisorObserver observer1 = new TestAdvisorObserver();
        TestAdvisorObserver observer2 = new TestAdvisorObserver();
        
        ((AdvisorObservable) area1).addAdvisorObserver( observer1 );
        ((AdvisorObservable) area2).addAdvisorObserver( observer2 );
        

        Sensor humidity1 = new HumiditySensor("Umidità Area 1");
        Sensor temperature1 = new TemperatureSensor("Temperatura Area 1");
        area1.addSensor( humidity1, mockSensorObserver );
        area1.addSensor( temperature1, mockSensorObserver );
        
        Sensor humidity2 = new HumiditySensor("Umidità Area 2");
        Sensor temperature2 = new TemperatureSensor("Temperatura Area 2");
        area2.addSensor( humidity2, mockSensorObserver );
        area2.addSensor( temperature2, mockSensorObserver );
        
        temperature1.refresh( area1.getId(), temperature1.getType() );
        
        assertTrue(observer1.wasNotified());
        assertFalse(observer2.wasNotified());
    }


}
