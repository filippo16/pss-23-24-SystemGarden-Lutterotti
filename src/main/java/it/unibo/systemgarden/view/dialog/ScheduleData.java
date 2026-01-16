package it.unibo.systemgarden.view.dialog;

import java.time.LocalTime;
import java.util.List;

/**
 * Data transfer object for schedule information.
 */
public record ScheduleData(LocalTime startTime, int duration, List<Integer> activeDays) {}