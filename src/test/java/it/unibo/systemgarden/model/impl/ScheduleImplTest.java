package it.unibo.systemgarden.model.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ScheduleImpl.
 */
class ScheduleImplTest {

    private ScheduleImpl schedule;

    @BeforeEach
    void setUp() {
        schedule = new ScheduleImpl(
            LocalTime.of(6, 30),
            45,
            List.of(1, 3, 5) // Monday, Wednesday, Friday
        );
    }

    @Test
    void testScheduleCreation() {
        assertEquals(LocalTime.of(6, 30), schedule.getStartTime());
        assertEquals(45, schedule.getDuration());
        assertEquals(3, schedule.getActiveDays().size());
    }

    @Test
    void testActiveDays() {
        final List<Integer> days = schedule.getActiveDays();

        assertTrue(days.contains(1)); // Monday
        assertTrue(days.contains(3)); // Wednesday
        assertTrue(days.contains(5)); // Friday
        assertFalse(days.contains(2)); // Tuesday
    }

    @Test
    void testGetActiveDaysIsDefensiveCopy() {
        final List<Integer> days = schedule.getActiveDays();
        days.add(7);


        assertEquals(3, schedule.getActiveDays().size());
        assertFalse(schedule.getActiveDays().contains(7));
    }

    @Test
    void testUpdate() {
        schedule.update(
            LocalTime.of(18, 0),
            30,
            new ArrayList<>(List.of(2, 4, 6))
        );

        assertEquals(LocalTime.of(18, 0), schedule.getStartTime());
        assertEquals(30, schedule.getDuration());
        assertEquals(List.of(2, 4, 6), schedule.getActiveDays());
    }

    @Test
    void testUpdatePreservesDefensiveCopy() {
        final List<Integer> newDays = new ArrayList<>(List.of(2, 4));
        schedule.update(LocalTime.of(12, 0), 60, newDays);

        newDays.add(6); 


        assertEquals(2, schedule.getActiveDays().size());
        assertFalse(schedule.getActiveDays().contains(6));
    }
}
