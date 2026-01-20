package it.unibo.systemgarden.model. api;

import java. util.List;

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
     */
    GreenArea createGreenArea(String name, String city);

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
     * @return list of all green areas
    */
    List<GreenArea> getGreenAreas();

    /**
     * Checks all schedules and returns the areas that changed state.
    */
    void checkAllSchedules();

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
}