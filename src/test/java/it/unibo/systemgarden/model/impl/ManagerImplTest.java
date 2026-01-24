package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Manager;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.utils.SensorType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ManagerImpl.
 */
class ManagerImplTest {

    private Manager manager;

    @BeforeEach
    void setUp() {
        manager = new ManagerImpl();
    }

    @Test
    void testCreateGreenArea() throws ActionMethodException {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma", null );

        assertNotNull( area );
        assertNotNull( area.getId() );
        assertEquals( "Parco Centrale", area.getName() );
        assertEquals( "Roma", area.getLocation().getCity() );
    }

    @Test
    void testGetGreenArea() throws ActionMethodException {
        final GreenArea created = manager.createGreenArea( "Parco Centrale", "Roma", null );
        final GreenArea retrieved = manager.getGreenArea( created.getId() );

        assertNotNull( retrieved );
        assertEquals( created.getId(), retrieved.getId() );
        assertEquals( created.getName(), retrieved.getName() );
    }

    @Test
    void testGetGreenAreaNotFound() {
        final GreenArea area = manager.getGreenArea( "NON-EXISTENT-ID" );

        assertNull( area );
    }

    @Test
    void testGetGreenAreas() throws ActionMethodException {
        manager.createGreenArea( "Parco A", "Milano", null );
        manager.createGreenArea( "Parco B", "Torino", null );

        final List<GreenArea> areas = manager.getGreenAreas();

        assertEquals( 2, areas.size() );
    }

    @Test
    void testGetGreenAreasEmpty() {
        final List<GreenArea> areas = manager.getGreenAreas();

        assertTrue( areas.isEmpty() );
    }

    @Test
    void testRemoveGreenArea()  throws ActionMethodException {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma", null );
        final String areaId = area.getId();

        final boolean removed = manager.removeGreenArea( areaId );

        assertTrue( removed );
        assertNull( manager.getGreenArea( areaId ) );
    }

    @Test
    void testRemoveGreenAreaNotFound() throws ActionMethodException {
        final boolean removed = manager.removeGreenArea( "NON-EXISTENT-ID" );

        assertFalse( removed );
    }



    
    @Test
    void testAddSectorToArea() throws ActionMethodException {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma", null );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        assertNotNull( sector );
        assertEquals( "Prato Nord", sector.getName() );
        assertEquals( 1, area.getSectors().size() );
    }

    @Test
    void testRemoveSectorFromArea() throws ActionMethodException {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma", null );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        final boolean result = manager.removeSectorFromArea( area.getId(), sector.getId() );

        assertTrue( result );
        assertTrue( area.getSectors().isEmpty() );
    }





    @Test
    void testIrrigateSector() throws Exception {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma", null );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        assertFalse( sector.isIrrigating() );

        final Sector irrigated = manager.irrigateSector( area.getId(), sector.getId() );

        assertNotNull( irrigated );
        assertTrue( irrigated.isIrrigating() );
    }

    @Test
    void testStopSector() throws Exception {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma", null );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        manager.irrigateSector( area.getId(), sector.getId() );
        assertTrue( sector.isIrrigating() );

        final Sector stopped = manager.stopSector( area.getId(), sector.getId() );

        assertNotNull( stopped );
        assertFalse( stopped.isIrrigating() );
    }







    @Test
    void testUpdateSectorSchedule() throws ActionMethodException {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma", null );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        final LocalTime startTime = LocalTime.of( 6, 30 );
        final int duration = 45;
        final List<Integer> activeDays = List.of( 1, 3, 5 ); // Lunedì, Mercoledì, Venerdì

        final Sector updated = manager.updateSectorSchedule( 
            area.getId(), sector.getId(), startTime, duration, activeDays 
        );

        assertNotNull( updated );
        assertEquals( startTime, updated.getSchedule().getStartTime() );
        assertEquals( duration, updated.getSchedule().getDuration() );
        assertEquals( activeDays, updated.getSchedule().getActiveDays() );
    }

    @Test
    void testAddSensorToArea() throws ActionMethodException {
        final GreenArea area = manager.createGreenArea("Parco Centrale", "Roma", null);

        final GreenArea updatedArea = manager.addSensorToArea(
                area.getId(), "Sensore Umidità", SensorType.HUMIDITY, null);

        assertNotNull( updatedArea );
        assertEquals( 1, updatedArea.getSensors().size() );
        assertEquals( "Sensore Umidità", updatedArea.getSensors()
            .get( 0 ).getName() );
        assertEquals( SensorType.HUMIDITY, updatedArea.getSensors()
            .get( 0 ).getType() );
    }

    @Test
    void testRemoveSensorFromArea() throws ActionMethodException {
        final GreenArea area = manager.createGreenArea("Parco Centrale", "Roma", null);
        manager.addSensorToArea( area.getId(), "Sensore Test", 
            SensorType.HUMIDITY, null 
        );

        final String sensorId = area.getSensors().get(0).getId();
        final boolean removed = manager.removeSensorFromArea( area.getId(), sensorId, null );

        assertTrue(removed);
        assertTrue(area.getSensors().isEmpty());
    }
}
