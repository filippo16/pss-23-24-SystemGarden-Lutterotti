package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for GreenAreaImpl.
 */
class GreenAreaImplTest {
    private GreenArea area;

    @BeforeEach
    void setUp() {
        area = new GreenAreaImpl("Giardino Nord", "Bologna");
    }

    @Test
    void testGreenAreaCreation() {
        assertNotNull(area.getId());
        assertTrue(area.getId().startsWith("AREA-"));
        assertEquals("Giardino Nord", area.getName());
        assertEquals("Bologna", area.getCity());
    }
}
