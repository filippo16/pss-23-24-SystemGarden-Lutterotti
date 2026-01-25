package it.unibo.systemgarden.view.dto;

import java.time.LocalTime;
import java.util.List;

/**
 * DTO for schedule to init information.
 * @param startTime the start time of the schedule
 * @param duration the duration of the schedule in minutes
 * @param activeDays the list of active days represented as integers (e.g., 0 for Sunday, 1 for Monday, etc.)
*/
public record ScheduleData( LocalTime startTime, int duration, List<Integer> activeDays ) {}