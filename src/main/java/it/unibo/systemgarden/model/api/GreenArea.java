package it.unibo.systemgarden.model.api;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Interface for a green area (garden or plant group).
 * A green area represents a single irrigation system with multiple sectors.
 */
public interface GreenArea {

    /**
     * @return the unique identifier of this area
     */
    String getId();

    /**
     * @return the name of this area
     */
    String getName();

    /**
     * @return the city where this area is located
     */
    String getCity();

    /**
     * @return list of sectors in this area
     */
    List<Sector> getSectors();

    /**
     * Adds a sector to this area.
     * 
     * @param sector the sector to add
     */
    void addSector( Sector sector );

    /**
     * Removes a sector from this area.
     * 
     * @param sector the sector to remove
     */
    void removeSector( Sector sector );

    ZoneId getTimezone();

    LocalTime getLocalTime();

    void checkSchedules();
}
