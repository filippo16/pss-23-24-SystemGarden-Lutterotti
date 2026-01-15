package it.unibo.systemgarden.model.api;

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

    Schedule getSchedule();
}
