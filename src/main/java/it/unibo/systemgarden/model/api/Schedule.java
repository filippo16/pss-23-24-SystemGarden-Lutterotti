package it.unibo.systemgarden.model.api;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Represents an irrigation schedule with start time, duration, and active days.
 */
public interface Schedule {

    /**
     * @return the scheduled start time
     */
    LocalTime getStartTime();

    /**
     * @return the duration in minutes
     */
    int getDuration();

    /**
     * @return list of active days (1=Monday, 7=Sunday)
     */
    List<Integer> getActiveDays();

    /**
     * Checks if irrigation should start now based on the given timezone.
     * 
     * @param timezone the timezone to use for time comparison
     * @return true if irrigation should start now
     */
    boolean shouldStartNow( ZoneId timezone );

    /**
     * Checks if irrigation should stop now based on the given timezone.
     * 
     * @param timezone the timezone to use for time comparison
     * @return true if irrigation should stop now
     */
    boolean shouldStopNow( ZoneId timezone );

    void update( LocalTime startTime, int duration, List<Integer> activeDays );

    String formatScheduleInfo();
}
