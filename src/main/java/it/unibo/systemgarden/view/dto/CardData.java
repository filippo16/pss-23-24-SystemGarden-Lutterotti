package it.unibo.systemgarden.view.dto;

import javafx.scene.layout.VBox;

public record CardData<T>(VBox card, T controller) {}
