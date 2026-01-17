package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.Location;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LocationImpl.
 */
class LocationImplTest {

    @Test
    void testLocationCreationWithBologna() {
        final Location location = new LocationImpl("Bologna");
        
        assertEquals("Bologna", location.getCity());
        assertEquals(ZoneId.of("Europe/Rome"), location.getTimezone());
        assertNotNull(location.getLocalTime());
    }

    @Test
    void testLocationCreationWithLondra() {
        final Location location = new LocationImpl("Londra");
        
        assertEquals("Londra", location.getCity());
        assertEquals(ZoneId.of("Europe/London"), location.getTimezone());
    }


    @Test
    void testLocationCaseInsensitive() {
        final Location location1 = new LocationImpl("BOLOGNA");
        final Location location2 = new LocationImpl("bologna");
        final Location location3 = new LocationImpl("BoLoGnA");
        
        assertEquals(ZoneId.of("Europe/Rome"), location1.getTimezone());
        assertEquals(ZoneId.of("Europe/Rome"), location2.getTimezone());
        assertEquals(ZoneId.of("Europe/Rome"), location3.getTimezone());
    }

    @Test
    void testGetLocalTimeNotNull() {
        final Location location = new LocationImpl("new york");
        
        assertNotNull(location.getLocalTime());
    }
}
