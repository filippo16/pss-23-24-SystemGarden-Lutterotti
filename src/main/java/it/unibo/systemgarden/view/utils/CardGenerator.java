package it.unibo.systemgarden.view.utils;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.view.component.AreaCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

import java.io.IOException;


public class CardGenerator {

    private static final String FXML_PATH_AREA_CARD = "fxml/component/AreaCard.fxml";
    private String css;

    public CardGenerator(final String css) {
        this.css = css;
    }


    /**
     * Creates a card that representing a GreenArea.
     */
    public VBox createAreaCard(final Controller controller, final GreenArea area) {
        try {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource(FXML_PATH_AREA_CARD)
            );
            final VBox card = loader.load();
            
            final AreaCardController cardController = loader.getController();
            cardController.initialize(controller, area, css);
            
            return card;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load AreaCard FXML", e);
        }
    }
}
