package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals( "Bologna", area.getCity() );
        assertTrue( area.getSectors().isEmpty() );
    }

    @Test
    void testAddSector() {
        final Sector sector = new SectorImpl( "Prato Est" );
        area.addSector( sector );

        assertEquals( 1, area.getSectors().size() );
        assertTrue( area.getSectors().contains( sector ) );
    }

    @Test
    void testRemoveSector() {
        final Sector sector = new SectorImpl( "Prato Est" );
        area.addSector( sector );
        area.removeSector( sector );

        assertTrue( area.getSectors().isEmpty() );
    }

    @Test
    void testIrrigateAll() {
        final Sector sector1 = new SectorImpl( "Settore 1" );
        final Sector sector2 = new SectorImpl( "Settore 2" );

        area.addSector( sector1 );
        area.addSector( sector2 );

        assertTrue( sector1.isIrrigating() );
        assertTrue( sector2.isIrrigating() );
    }

    @Test
    void testStopAll() {
        final Sector sector1 = new SectorImpl( "Settore 1" );
        area.addSector( sector1 );

        sector1.irrigate();

        assertFalse( sector1.isIrrigating() );
    }
}
