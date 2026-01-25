package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.mock.TestAdvisorObserver;
import it.unibo.systemgarden.mock.TestSensorObserver;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.observer.AdvisorObservable;
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
        area.addSensor( "AREA-1","Temperatura", 
            SensorType.TEMPERATURE, mockSensorObserver );

        assertEquals( 1, area.getSensors().size() );
    }

    @Test
    void testAddSensorMaxLimit() throws ActionMethodException {

        area.addSensor( "AREA-1","Temperatura", 
            SensorType.TEMPERATURE, mockSensorObserver );
        area.addSensor( "AREA-1","Umiditià", 
            SensorType.HUMIDITY, mockSensorObserver );

        assertThrows(ActionMethodException.class, () -> {
            area.addSensor( "AREA-1","Temperatura", 
            SensorType.TEMPERATURE, mockSensorObserver );
        });
    }

    @Test
    void testRemoveSensor() throws ActionMethodException {
        area.addSensor( area.getId(),"Temperatura", 
            SensorType.TEMPERATURE, mockSensorObserver );
        
        Sensor sensor = area.getSensors().get(0);
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
        


        area1.addSensor( area1.getId(), "Temperature", SensorType.TEMPERATURE, mockSensorObserver );
        area1.addSensor( area1.getId(), "Humidity", SensorType.HUMIDITY, mockSensorObserver );
        

        area2.addSensor( area2.getId(), "Temperature", SensorType.TEMPERATURE, mockSensorObserver );
        area2.addSensor( area2.getId(), "Humidity", SensorType.HUMIDITY, mockSensorObserver );

        Sensor temperature1 = area1.getSensors().stream()
            .filter(s -> s.getType() == SensorType.TEMPERATURE)
            .findFirst()
            .orElseThrow();
        
        temperature1.refresh( area1.getId(), temperature1.getType() );
        
        assertTrue(observer1.wasNotified());
        assertFalse(observer2.wasNotified());
    }


}
