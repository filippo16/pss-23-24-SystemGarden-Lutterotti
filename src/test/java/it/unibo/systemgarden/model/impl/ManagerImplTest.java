package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Manager;
import it.unibo.systemgarden.model.api.Sector;

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
    void testCreateGreenArea() {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma" );

        assertNotNull( area );
        assertNotNull( area.getId() );
        assertEquals( "Parco Centrale", area.getName() );
        assertEquals( "Roma", area.getLocation().getCity() );
    }

    @Test
    void testGetGreenArea() {
        final GreenArea created = manager.createGreenArea( "Parco Centrale", "Roma" );
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
    void testGetGreenAreas() {
        manager.createGreenArea( "Parco A", "Milano" );
        manager.createGreenArea( "Parco B", "Torino" );

        final List<GreenArea> areas = manager.getGreenAreas();

        assertEquals( 2, areas.size() );
    }

    @Test
    void testGetGreenAreasEmpty() {
        final List<GreenArea> areas = manager.getGreenAreas();

        assertTrue( areas.isEmpty() );
    }

    @Test
    void testRemoveGreenArea() {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma" );
        final String areaId = area.getId();

        final GreenArea removed = manager.removeGreenArea( areaId );

        assertNotNull( removed );
        assertEquals( areaId, removed.getId() );
        assertNull( manager.getGreenArea( areaId ) );
    }

    @Test
    void testRemoveGreenAreaNotFound() {
        final GreenArea removed = manager.removeGreenArea( "NON-EXISTENT-ID" );

        assertNull( removed );
    }



    
    @Test
    void testAddSectorToArea() {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma" );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        assertNotNull( sector );
        assertEquals( "Prato Nord", sector.getName() );
        assertEquals( 1, area.getSectors().size() );
    }

    @Test
    void testAddSectorToAreaNotFound() {
        final Sector sector = manager.addSectorToArea( "NON-EXISTENT-ID", "Prato Nord" );

        assertNull( sector );
    }

    @Test
    void testRemoveSectorFromArea() {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma" );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        final boolean result = manager.removeSectorFromArea( area.getId(), sector.getId() );

        assertTrue( result );
        assertTrue( area.getSectors().isEmpty() );
    }

    @Test
    void testRemoveSectorFromAreaNotFound() {
        final boolean result = manager.removeSectorFromArea( "NON-EXISTENT-ID", "SECTOR-ID" );

        assertFalse( result );
    }





    @Test
    void testIrrigateSector() {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma" );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        assertFalse( sector.isIrrigating() );

        final Sector irrigated = manager.irrigateSector( area.getId(), sector.getId() );

        assertNotNull( irrigated );
        assertTrue( irrigated.isIrrigating() );
    }

    @Test
    void testIrrigateSectorAreaNotFound() {
        final Sector result = manager.irrigateSector( "NON-EXISTENT-ID", "SECTOR-ID" );

        assertNull( result );
    }

    @Test
    void testStopSector() {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma" );
        final Sector sector = manager.addSectorToArea( area.getId(), "Prato Nord" );

        manager.irrigateSector( area.getId(), sector.getId() );
        assertTrue( sector.isIrrigating() );

        final Sector stopped = manager.stopSector( area.getId(), sector.getId() );

        assertNotNull( stopped );
        assertFalse( stopped.isIrrigating() );
    }

    @Test
    void testStopSectorAreaNotFound() {
        final Sector result = manager.stopSector( "NON-EXISTENT-ID", "SECTOR-ID" );

        assertNull( result );
    }







    @Test
    void testUpdateSectorSchedule() {
        final GreenArea area = manager.createGreenArea( "Parco Centrale", "Roma" );
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
    void testUpdateSectorScheduleAreaNotFound() {
        final Sector result = manager.updateSectorSchedule( 
            "NOT_EXIST", "SECTOR-ID", LocalTime.of( 8, 0 ), 30, List.of( 1, 2 ) 
        );

        assertNull( result );
    }
}
