package it.unibo.systemgarden.view.utils;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CardGenerator {

    private String css;

    public CardGenerator(final String css) {
        this.css = css;
    }


    /**
     * Creates a card that representing a GreenArea.
    */
    public VBox createAreaCard(Controller controller, final GreenArea area) {
        final VBox card = new VBox(10);
        card.setId(area.getId());
        card.getStyleClass().add("area-card");
        card.setPadding(new Insets(15));

        // Structure
        final HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        final Label nameLabel = new Label(area.getName());
        nameLabel.getStyleClass().add("area-name");

        final Label cityLabel = new Label("(" + area.getCity() + ")");
        cityLabel.setStyle("-fx-text-fill: #666666;");

        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        final Button deleteBtn = new Button("X");
        deleteBtn.getStyleClass().add("danger-button");
        deleteBtn.setOnAction(e -> {
            controller.removeGreenArea( area.getId() );
        });

        header.getChildren().addAll(nameLabel, cityLabel, spacer, deleteBtn);

        final VBox sectorsSection = createSectorsSection(controller, area);

        card.getChildren().addAll(header, sectorsSection );
        return card;
    }

    /**
     * Creates the sectors section for a GreenArea card.
    */
    private VBox createSectorsSection(Controller controller, final GreenArea area) {
        final VBox box = new VBox(5);

        final HBox titleRow = new HBox(10);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        final Label title = new Label("Settori");
        title.getStyleClass().add("section-label");

        final Button addBtn = new Button("Aggiungi Settore");
        addBtn.getStyleClass().add("btn-primary");
        addBtn.setOnAction(e -> {
            final String result = DialogHelper.showAddSectorDialog(css);
            if (result != null) {
                controller.addSectorToArea(area.getId(), result);
            } 
        });

        titleRow.getChildren().addAll(title, addBtn);
        box.getChildren().add(titleRow);

        for (final Sector sector : area.getSectors()) {
            final HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            final String status = sector.isIrrigating() ? "[ON]" : "[--]";
            final Label sectorLabel = new Label(status + " " + sector.getName());
            sectorLabel.setPrefWidth(150);

            final Button startBtn = new Button("Avvia");
            startBtn.getStyleClass().add("btn-secondary");
            startBtn.setOnAction(e -> {
                controller.irrigateSector(area.getId(), sector.getId());
            });

            final Button stopBtn = new Button("Ferma");
            stopBtn.getStyleClass().add("btn-secondary");
            stopBtn.setOnAction(e -> {
                controller.stopSector(area.getId(), sector.getId());
            });

            final Button deleteBtn = new Button("X");
            deleteBtn.getStyleClass().add("danger-button");
            deleteBtn.setOnAction(e -> {
                controller.removeSectorFromArea(area.getId(), sector.getId());
            });

            row.getChildren().addAll(sectorLabel, startBtn, stopBtn, deleteBtn);
            box.getChildren().add(row);
        }

        return box;
    }
}
