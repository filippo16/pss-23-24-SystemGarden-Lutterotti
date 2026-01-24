package it.unibo.systemgarden.model.api;

import java.util.List;

import it.unibo.systemgarden.model.api.observer.AdvisorObservable;
import it.unibo.systemgarden.model.api.observer.SensorObserver;

/**
 * Interface for a green area (garden or plant group).
 * A green area represents a single irrigation system with multiple sectors.
 */
public interface GreenArea extends AdvisorObservable {

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
    void addSector( Sector sector );

    /**
     * Removes a sector from this area.
     * 
     * @param sector the sector to remove
     */
    void removeSector( Sector sector );

    /**
     * Checks all sector schedules and starts/stops irrigation as needed.
     */
    boolean checkSchedules();

    List<Sensor> getSensors();

    void addSensor( final Sensor sensor, final SensorObserver observer  );

    boolean removeSensor( final String sensorId, final SensorObserver observer  );

    void toglgleSmartAdvisor( final boolean enabled );
}
