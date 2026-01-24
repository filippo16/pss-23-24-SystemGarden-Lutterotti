package it.unibo.systemgarden.model.api;

import java.time.LocalTime;
import java.util.List;

/**
 * Interface for a sector (irrigable zone).
 * Each sector has a valve that can be opened/closed.
 */
public interface Sector {

    /**
     * @return the unique identifier of this sector
     */
    String getId();

    /**
     * @return the name of this sector
     */
    String getName();

    /**
     * @return true if the sector is currently irrigating
     */
    boolean isIrrigating();

    /**
     * Starts irrigation.
     */
    void irrigate();

    /**
     * Stops irrigation.
     */
    void stop();

    /**
     * @return the irrigation schedule of this sector
    */
    Schedule getSchedule();

    /**
     * Updates the irrigation schedule of this sector.
     * @param startTime the start time of the irrigation
     * @param duration the duration of the irrigation
     * @param activeDays the days on which the irrigation is active
    */
    void updateSchedule( final LocalTime startTime, final int duration, final List<Integer> activeDays );
}
