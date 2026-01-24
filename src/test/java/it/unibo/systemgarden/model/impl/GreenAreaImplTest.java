package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
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
        assertEquals( "Bologna", area.getLocation().getCity() );
        assertTrue( area.getSectors().isEmpty() );
    }

    @Test
    void testAddSector() {
        area.addSector( "Prato Est", "Fiori" );

        assertEquals( 1, area.getSectors().size() );
        assertTrue( area.getSectors().stream().anyMatch(s -> s.getName().equals("Prato Est")) );
    }

    @Test
    void testRemoveSector() {
        area.addSector( "Prato Est", "Fiori" );
        area.removeSector( "Prato Est" );

        assertTrue( area.getSectors().isEmpty() );
    }
}
