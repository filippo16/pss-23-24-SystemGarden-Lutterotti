package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.Schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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

    public boolean shouldStartNow(ZoneId timezone) {
        
        LocalDateTime now = LocalDateTime.now(timezone);
        int today = now.getDayOfWeek().getValue();  // 1=Mon, 7=Sun
        
        if (!activeDays.contains(today)) return false;
        
        LocalTime nowTime = now.toLocalTime();
        
        return activeDays.contains(today) && nowTime.isAfter(startTime);
    }

    public boolean shouldStopNow(ZoneId timezone) {
        
        LocalTime endTime = startTime.plusMinutes(duration);
        LocalTime nowTime = LocalTime.now(timezone);
        
        return nowTime.isAfter(endTime);
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
