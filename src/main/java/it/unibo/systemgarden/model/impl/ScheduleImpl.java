package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.Schedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class ScheduleImpl implements Schedule {

    private LocalTime startTime;
    private int duration;
    private List<Integer> activeDays;

    public ScheduleImpl(final LocalTime startTime, final int duration, final List<Integer> activeDays) {
        this.startTime = startTime;
        this.duration = duration;
        this.activeDays = new ArrayList<>(activeDays);
    }

    @Override
    public LocalTime getStartTime() {
        return this.startTime;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public List<Integer> getActiveDays() {
        return new ArrayList<>(this.activeDays);
    }

    @Override
    public void getNextRunTime() {
       
    }

    /**
     * Updates the schedule parameters.
     */
    public void update(final LocalTime startTime, final int duration, final List<Integer> activeDays) {
        this.startTime = startTime;
        this.duration = duration;
        this.activeDays = new ArrayList<>(activeDays);
    }
}
