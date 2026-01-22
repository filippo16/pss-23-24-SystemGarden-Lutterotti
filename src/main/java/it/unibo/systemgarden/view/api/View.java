package it.unibo.systemgarden.view.api;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;

import java.time.LocalTime;

import it.unibo.systemgarden.controller.api.Controller;

/**
 * View interface for SystemGarden
 */
public interface View {

    /**
     * Shows the main window.
     */
    void show();

    void setController( Controller controller );

    /**
     * Add a card view for the specified green area.
     * @param area the green area to add
     */
    void addAreaCard( GreenArea area );

    /**
     * Remove the card view for the specified green area.
     * @param areaId the ID of the green area to remove
     */
    void removeAreaCard( String areaId );

    void refreshAreaCard( GreenArea area );

    void updateAreaClock( String areaId, LocalTime time );

    void addSectorCard( String areaId, Sector sector );

    void removeSectorCard( String areaId, String sectorId );

    void refreshSectorCard( String areaId, Sector sector );
}
