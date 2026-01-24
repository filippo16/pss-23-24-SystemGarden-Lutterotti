package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.Schedule;
import it.unibo.systemgarden.model.api.Sector;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

/**
 * Implementation of Sector interface.
 */
public class SectorImpl implements Sector {

    private final String id;
    private final String name;
    private boolean irrigating;
    private Schedule schedule;

    /**
     * Creates a new sector.
     * 
     * @param name the sector name
     */
    public SectorImpl( final String name ) {
        this.id = "SECT-" + new Random().nextInt( 100000 );
        this.name = name;
        this.schedule = new ScheduleImpl(LocalTime.of(6, 0), 30, List.of(1, 3, 5));
        this.irrigating = false;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isIrrigating() {
        return this.irrigating;
    }

    @Override
    public void irrigate() {
        this.irrigating = true;
    }

    @Override
    public void stop() {
        this.irrigating = false;
    }

    @Override
    public Schedule getSchedule() {
        return this.schedule;
    }

    @Override
    public void updateSchedule( final LocalTime startTime, final int duration, final List<Integer> activeDays ) {
        this.schedule.update(startTime, duration, activeDays);;
    }
}
