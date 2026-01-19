package it.unibo.systemgarden.view.api;

import it.unibo.systemgarden.controller.api.Controller;
import javafx.scene.layout.VBox;

public interface CardController<T> {

    /**
     * Initializes the card with data.
     */
    void initialize(Controller controller, T data, String css);

    VBox getCard();
}