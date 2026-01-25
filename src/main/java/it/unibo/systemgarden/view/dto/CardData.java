package it.unibo.systemgarden.view.dto;

import javafx.scene.layout.VBox;

/**
 * A DTO that encapsulates a card's visual representation and its associated controller.
 * @param <T> the type of the controller associated with the card
 * @param card the VBox representing the card's visual layout
 * @param controller the controller associated with the card
*/
public record CardData<T>(VBox card, T controller) {}
