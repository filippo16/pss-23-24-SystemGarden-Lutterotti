package it.unibo.systemgarden.view.dto;

import it.unibo.systemgarden.view.component.SectorCardController;
import javafx.scene.layout.VBox;

public record SectorCardData( VBox sectorCard, SectorCardController controller ){};