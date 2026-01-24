package it.unibo.systemgarden.view.api;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.view.utils.ToastType;

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

    /**
     * Sets the controller for the view.
     * @param controller the controller to be set
    */
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

    /**
     * Refresh the card view for the specified green area.
     * @param area the green area to refresh
    */
    void refreshAreaCard( GreenArea area );

    /**
     * Update the clock display for the specified green area.S
     * @param areaId the ID of the green area
     * @param time the new time to display
    */
    void updateAreaClock( String areaId, LocalTime time );

    /**
     * Add a card view for the specified sector within a green area.
     * @param areaId the ID of the green area
     * @param sector the sector to add
    */
    void addSectorCard( String areaId, Sector sector );

    /**
     * Remove the card view for the specified sector within a green area.
     * @param areaId the ID of the green area
     * @param sectorId the ID of the sector to remove
    */
    void removeSectorCard( String areaId, String sectorId );

    /**
     * Refresh the card view for the specified sector within a green area.
     * @param areaId the ID of the green area
     * @param sector the sector to refresh
    */
    void refreshSectorCard( String areaId, Sector sector );

    /**
     * Show a toast notification with the specified message and type.
     * @param message the message to display
     * @param type the type of the toast
    */
    void showToast( String message, ToastType type );
}
