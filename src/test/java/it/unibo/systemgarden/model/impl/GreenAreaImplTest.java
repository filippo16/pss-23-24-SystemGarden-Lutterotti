package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;

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

}
