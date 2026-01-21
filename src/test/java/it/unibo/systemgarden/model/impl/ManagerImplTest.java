package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Manager;
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
}
