package it.unibo.systemgarden.controller.api;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.utils.SensorType;

import java.time.LocalTime;
import java.util.List;

/**
 * Controller interface for SystemGarden
 */
public interface Controller {

    /**
     * Starts the application.
     */
    void start();

    /**
     * Stops the application.
     */
    void stop();

    /**
     * Creates a new green area.
     * 
     * @param name the area name
     * @param city the city
     * @return the created green area
     */
    void createGreenArea( String name, String city );
    /**
     * Removes a green area.
     * 
     * @param areaId the area ID to remove
     */
    void removeGreenArea( String areaId );

    /**
     * Gets a green area by ID.
     * 
     * @param areaId the area ID
     * @return the green area, or null if not found
     */
    GreenArea getGreenArea( String areaId );

    /**
     * Adds a sector to an area.
     * 
     * @param areaId     the area ID
     * @param sectorName the sector name
     */
    void addSectorToArea( String areaId, String sectorName );

    /**
     * Removes a sector from an area.
     * 
     * @param areaId   the area ID
     * @param sectorId the sector ID
     */
    void removeSectorFromArea( String areaId, String sectorId );

    /**
     * Starts irrigation for a specific sector.
     *
     * @param areaId the area ID
     * @param sectorId the sector ID
     */
    void irrigateSector( String areaId, String sectorId );

    /**
     * Stops irrigation for a specific sector.
     *
     * @param areaId the area ID
     * @param sectorId the sector ID
     */
    void stopSector( String areaId, String sectorId );

    /**
     * Updates the irrigation schedule for a specific sector.
     * @param areaId the area ID of the specific sector
     * @param sectorId the sector ID of the specific schedule
     * @param startTime the start time of the irrigation
     * @param duration the duration of the irrigation
     * @param activeDays the days on which the irrigation is active
     * @return the updated green area
    */
    void updateSectorSchedule( String areaId, String sectorId, 
        LocalTime startTime, int duration, List<Integer> activeDays );

    void refreshSensorData();

    void addSensorToArea( String areaId, String name, SensorType type );

    void removeSensorFromArea( String areaId, String sensorId );
}
