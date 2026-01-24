package it.unibo.systemgarden.model.api;

import java.time.LocalTime;
import java.util.List;

import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.observer.SensorObserver;

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
     * @return the location of this area
     */
    Location getLocation();

    /**
     * @return list of sectors in this area
     */
    List<Sector> getSectors();

    Sector getSector( String sectorId );

    /**
     * Adds a sector to this area.
     * 
     * @param sector the sector to add
     */
    Sector addSector( final String areaId, final String sectorName );

    /**
     * Removes a sector from this area.
     * 
     * @param sector the sector to remove
     */
    boolean removeSector( String sectorId );

    /**
     * Checks all sector schedules and starts/stops irrigation as needed.
     */
    boolean checkSchedules();

    Sector irrigateSector( String sectorId );

    Sector stopSector( String sectorId );

    Sector updateSectorSchedule( final String sectorId, 
        final LocalTime startTime, final int duration, final List<Integer> activeDays 
    );

    List<Sensor> getSensors();

    void addSensor( final Sensor sensor, final SensorObserver observer  ) throws ActionMethodException;

    boolean removeSensor( final String sensorId, final SensorObserver observer  );
}
