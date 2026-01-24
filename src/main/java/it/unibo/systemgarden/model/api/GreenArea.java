package it.unibo.systemgarden.model.api;

import java.time.LocalTime;
import java.util.List;

import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.observer.SensorObserver;

/**
 * Interface for a green area (garden or plant group).
 * A green area represents a single irrigation system with multiple sectors, location, sensors and smart advisor.
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
     * Gets the location of this area. 
     * @return the location of this area {@link Location}
    */
    Location getLocation();

    /**
     * Gets the list of sectors in this area.
     * @return list of sectors in this area
    */
    List<Sector> getSectors();

    /**
     * Gets a sector by its unique identifier.
     * @param sectorId the unique identifier of the sector
     * @return the sector with the given id
    */
    Sector getSector( String sectorId );

    /**
     * Adds a sector to this area.
     * @param areaId the id of the area
     * @param sectorName the name of the sector to add
     * @return the added sector
    */
    Sector addSector( final String areaId, final String sectorName );

    /**
     * Removes a sector from this area.
     * @param sectorId the id of the sector to remove
    */
    boolean removeSector( String sectorId );

    /**
     * Checks all sector schedules and starts/stops irrigation as needed.
    */
    boolean checkSchedules();

    /**
     * Irrigates a sector immediately.
     * @param sectorId the id of the sector to irrigate
     * @return the irrigated sector
    */
    Sector irrigateSector( String sectorId );

    /**
     * Stops irrigation of a sector immediately.
     * @param sectorId the id of the sector to stop
     * @return the stopped sector
    */
    Sector stopSector( String sectorId );

    /**
     * Updates the schedule of a sector.
     * @param sectorId the id of the sector to update
     * @param startTime the new start time for irrigation
     * @param duration the duration of irrigation in minutes
     * @param activeDays the days of the week when irrigation is active
     * @return the updated sector
    */
    Sector updateSectorSchedule( final String sectorId, 
        final LocalTime startTime, final int duration, final List<Integer> activeDays 
    );

    /**
     * Gets the list of sensors in this area.
     * @return list of sensors in this area
    */
    List<Sensor> getSensors();

    /**
     * Adds a sensor to this area and registers view observer for it.
     * @param sensor the sensor to add
     * @param observer the observer to register
     * @throws ActionMethodException if an error occurs during the addition
    */
    void addSensor( final Sensor sensor, final SensorObserver observer  ) throws ActionMethodException;

    /**
     * Removes a sensor from this area and unregisters view observer for it.
     * @param sensorId the id of the sensor to remove
     * @param observer the observer to unregister
     * @return true if the sensor was removed, false otherwise
    */
    boolean removeSensor( final String sensorId, final SensorObserver observer  );
}
