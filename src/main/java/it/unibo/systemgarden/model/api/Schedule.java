package it.unibo.systemgarden.model.api;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

public interface Schedule {

    LocalTime getStartTime();

    int getDuration();

    List<Integer> getActiveDays();

    boolean shouldStartNow(ZoneId timezone);
    
    boolean shouldStopNow(ZoneId timezone);
}
