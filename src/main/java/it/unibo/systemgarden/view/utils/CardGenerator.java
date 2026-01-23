package it.unibo.systemgarden.view.utils;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.view.component.AreaCardController;
import it.unibo.systemgarden.view.dto.CardData;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

import java.io.IOException;


public class CardGenerator {

    private static final String FXML_PATH_AREA_CARD = "fxml/component/AreaCard.fxml";


    /**
     * Creates a card that representing a GreenArea.
     */
    public static CardData<AreaCardController> createAreaCard(final Controller controller, final GreenArea area) {
        try {
            final FXMLLoader loader = new FXMLLoader(
                CardGenerator.class.getClassLoader().getResource(FXML_PATH_AREA_CARD)
            );
            final VBox card = loader.load();
            
            final AreaCardController cardController = loader.getController();
            cardController.initialize( controller, area );
            
            return new CardData<AreaCardController>(card, cardController);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load AreaCard FXML", e);
        }
    }
}
