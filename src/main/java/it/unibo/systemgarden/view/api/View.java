package it.unibo.systemgarden.view.api;

import it.unibo.systemgarden.model.api.GreenArea;

import java.util.List;

/**
 * View interface for SystemGarden
 */
public interface View {

    /**
     * Shows the main window.
     */
    void show();

    /**
     * Updates the display with the current green areas.
     * @param areas list of green areas
     */
    void updateGreenAreas( List<GreenArea> areas );
}
