package it.unibo.systemgarden.view.api;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.controller.api.Controller;

import java.util.List;

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
     * @param area the green area to remove
     */
    void removeAreaCard( GreenArea area );

    void refreshAreaCard( GreenArea area );
}
