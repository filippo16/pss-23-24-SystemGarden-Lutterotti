package it.unibo.systemgarden.model.api;

import java.time.LocalTime;
import java.util.List;

public interface Schedule {

    LocalTime getStartTime();

    int getDuration();

    List<Integer> getActiveDays();

    void getNextRunTime();   
}
