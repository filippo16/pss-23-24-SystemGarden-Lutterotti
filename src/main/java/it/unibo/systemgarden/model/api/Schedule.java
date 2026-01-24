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
     * @param timezone the timezone to use for time comparison
     * @return true if irrigation should start now
     */
    boolean shouldStartNow( ZoneId timezone );

    /**
     * Checks if irrigation should stop now based on the given timezone.
     * @param timezone the timezone to use for time comparison
     * @return true if irrigation should stop now
     */
    boolean shouldStopNow( ZoneId timezone );

    /**
     * Updates the schedule with new parameters.
     * @param startTime the new start time
     * @param duration the new duration in minutes
     * @param activeDays the new list of active days
     */
    void update( LocalTime startTime, int duration, List<Integer> activeDays );

    /**
     * Formats the schedule information as a string for view.
     * @return formatted schedule information
    */
    String formatScheduleInfo();
}
