package it.unibo.systemgarden.model. api;

import java.time.LocalTime;
import java. util.List;

import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.observer.AdvisorObserver;
import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.utils.SensorType;

/**
 * This is the main entry point for the model layer.
*/
public interface Manager {

    /**
     * Creates a new green area.
     * @param name the area name
     * @param city the city
     * @param observer the advisor observer (view)
     * @return the created green area
     * @throws ActionMethodException 
    */
    GreenArea createGreenArea(String name, String city, AdvisorObserver observer) throws ActionMethodException;

    /**
     * Removes a green area.
     * @param areaId the area ID to remove
     * @param observer the advisor observer (view)
     * @return true if the area was removed, false otherwise
     * @throws ActionMethodException
    */
    boolean removeGreenArea( String areaId, AdvisorObserver observer ) throws ActionMethodException;

    /**
     * Gets a green area by ID.
     * @param areaId the area ID
     * @return the green area, or null if not found
    */
    GreenArea getGreenArea( String areaId );

    /**
     * Gets all green areas.
     * @return list of all green areas
    */
    List<GreenArea> getGreenAreas();

    /**
     * Checks all schedules and returns the areas that changed state.
    */
    List<GreenArea> checkAllSchedules();

    /**
     * Adds a sector to an area.
     * @param areaId the area ID
     * @param sectorName the sector name
     * @return the added sector
     * @throws ActionMethodException
    */
    Sector addSectorToArea( String areaId, String sectorName ) throws ActionMethodException;

    /**
     * Removes a sector from an area.
     * @param areaId   the area ID
     * @param sectorId the sector ID
     * @return true if the sector was removed, false otherwise
     * @throws ActionMethodException
    */
    boolean removeSectorFromArea( String areaId, String sectorId ) throws ActionMethodException;

    /**
     * Starts irrigation for a specific sector.
     * @param areaId the area ID
     * @param sectorId the sector ID
     * @return the irrigated sector
     * @throws ActionMethodException
    */
    Sector irrigateSector( String areaId, String sectorId ) throws ActionMethodException;

    /**
     * Stops irrigation for a specific sector.
     * @param areaId the area ID
     * @param sectorId the sector ID
     * @return the stopped sector
     * @throws ActionMethodException
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
     * @throws ActionMethodExceptions
    */
    Sector updateSectorSchedule( String areaId, String sectorId, 
        LocalTime startTime, int duration, List<Integer> activeDays 
    ) throws ActionMethodException;

    /**
     * Refreshes sensor data for all sensors in all areas.
     * It is a test method in order to simulate sensor data updates.
    */
    void refreshSensorData();

    /**
     * Adds a sensor to an area.
     * @param areaId the area ID
     * @param name the sensor name
     * @param type the sensor type
     * @param observer the sensor observer (view)
     * @return the updated green area
     * @throws ActionMethodException
    */
    GreenArea addSensorToArea( String areaId,  String name,  SensorType type, SensorObserver observer ) throws ActionMethodException;

    /**
     * Removes a sensor from an area.
     * @param areaId the area ID
     * @param sensorId the sensor ID
     * @param observer the sensor observer (view)
     * @return true if the sensor was removed, false otherwise
     * @throws ActionMethodException
    */
    boolean removeSensorFromArea(  String areaId, String sensorId, SensorObserver observer ) throws ActionMethodException;
}