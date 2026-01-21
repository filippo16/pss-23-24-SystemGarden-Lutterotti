package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Manager;
import it.unibo.systemgarden.model.api.Sector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
