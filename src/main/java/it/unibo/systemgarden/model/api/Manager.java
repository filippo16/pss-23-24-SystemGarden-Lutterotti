package it.unibo.systemgarden.model. api;

import java.time.LocalTime;
import java. util.List;

import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.observer.AdvisorObserver;
import it.unibo.systemgarden.model.utils.SensorType;

/**
 * This is the main entry point for the model layer.
 */
public interface Manager {

    /**
     * Creates a new green area.
     * 
     * @param name the area name
     * @param city the city
     * @return the created green area
     * @throws ActionMethodException 
     */
    GreenArea createGreenArea(String name, String city) throws ActionMethodException;

    /**
     * Removes a green area.
     * 
     * @param areaId the area ID to remove
     */
    boolean removeGreenArea( String areaId ) throws ActionMethodException;

    /**
     * Gets a green area by ID.
     * 
     * @param areaId the area ID
     * @return the green area, or null if not found
    */
    GreenArea getGreenArea( String areaId );

    /**
     * @return list of all green areas
    */
    List<GreenArea> getGreenAreas();

    /**
     * Checks all schedules and returns the areas that changed state.
    */
    List<GreenArea> checkAllSchedules();

    /**
     * Adds a sector to an area.
     * 
     * @param areaId     the area ID
     * @param sectorName the sector name
    */
    Sector addSectorToArea( String areaId, String sectorName ) throws ActionMethodException;

    /**
     * Removes a sector from an area.
     * 
     * @param areaId   the area ID
     * @param sectorId the sector ID
    */
    boolean removeSectorFromArea( String areaId, String sectorId ) throws ActionMethodException;

    /**
     * Starts irrigation for a specific sector.
     *
     * @param areaId the area ID
     * @param sectorId the sector ID
    */
    Sector irrigateSector( String areaId, String sectorId ) throws ActionMethodException;

    /**
     * Stops irrigation for a specific sector.
     *
     * @param areaId the area ID
     * @param sectorId the sector ID
    */
    Sector stopSector( String areaId, String sectorId ) throws ActionMethodException;

    /**
     * Updates the irrigation schedule for a specific sector.
     * @param areaId the area ID of the specific sector
     * @param sectorId the sector ID of the specific schedule
     * @param startTime the start time of the irrigation
     * @param duration the duration of the irrigation
     * @param activeDays the days on which the irrigation is active
     * @return the updated sector
    */
    Sector updateSectorSchedule( String areaId, String sectorId, 
        LocalTime startTime, int duration, List<Integer> activeDays 
    ) throws ActionMethodException;

    void refreshSensorData();

    GreenArea addSensorToArea( String areaId,  String name,  SensorType type ) throws ActionMethodException;

    boolean removeSensorFromArea(  String areaId, String sensorId ) throws ActionMethodException;

    void toggleSmartAdvisor(  String areaId,  boolean enabled, AdvisorObserver observer );
}