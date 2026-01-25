package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.Sector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

/**
 * Test class for SectorImpl.
 */
class SectorImplTest {

    private Sector sector;

    @BeforeEach
    void setUp() {
        sector = new SectorImpl( "Prato Sud" );
    }

    @Test
    void testSectorCreation() {
        assertNotNull( sector.getId() );
        assertTrue( sector.getId().startsWith( "SECT-" ) );
        assertEquals( "Prato Sud", sector.getName() );
        assertFalse( sector.isIrrigating() );
    }

    @Test
    void testIrrigate() {
        sector.irrigate();

        assertTrue( sector.isIrrigating() );
    }

    @Test
    void testStop() {
        sector.irrigate();
        sector.stop();

        assertFalse( sector.isIrrigating() );
    }


    @Test
    void testUpdateSchedule() {
        sector.updateSchedule( LocalTime.of(7, 0), 30, List.of(1, 3, 5) );

        assertNotNull( sector.getSchedule() );
        assertEquals( LocalTime.of(7, 0), sector.getSchedule().getStartTime() );
        assertEquals (30, sector.getSchedule().getDuration() );
    }
}
