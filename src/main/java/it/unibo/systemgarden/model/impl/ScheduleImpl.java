package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.Schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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


    /**
     * Formats the schedule information for display.
     */
    @Override
    public String formatScheduleInfo() {
        
        final String time = this.getStartTime().format( DateTimeFormatter.ofPattern( "HH:mm" ) );
        final String duration = this.getDuration() + " min";
        final String days = formatDays( this.getActiveDays() );
        
        return "Orario " + time + " | " + duration + " | " + days;
    }

    /**
     * Formats the list of active days.
     */
    private String formatDays(final List<Integer> days) {
        if (days == null || days.isEmpty()) {
            return "Nessun giorno";
        }
        
        return days.stream()
            .sorted()
            .map(d -> switch (d) {
                case 1 -> "Lun";
                case 2 -> "Mar";
                case 3 -> "Mer";
                case 4 -> "Gio";
                case 5 -> "Ven";
                case 6 -> "Sab";
                case 7 -> "Dom";
                default -> "";
            })
            .collect(Collectors.joining(", "));
    }
}
