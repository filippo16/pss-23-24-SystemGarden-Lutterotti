package it.unibo.systemgarden.model.api;

import java.time.LocalTime;
import java.time.ZoneId;


public interface Location {

    /**
     * @return the city name
     */
    String getCity();

    /**
     * @return the timezone of this location
     */
    ZoneId getTimezone();

    /**
     * @return the current local time at this location
     */
    LocalTime getLocalTime();
}
