package it.unibo.systemgarden.view.api;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;

public interface CardController {

    /**
     * Initializes the card with data.
     */
    void initialize(final Controller controller, final GreenArea area, final String css);
}
