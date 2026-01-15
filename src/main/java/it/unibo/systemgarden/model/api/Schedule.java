package it.unibo.systemgarden.model.api;

import java.time.LocalTime;

public interface Schedule {

    LocalTime getStartTime();

    int getDuration();
}
