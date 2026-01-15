package it.unibo.systemgarden.view.utils;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CardGenerator {

    public VBox createAreaCard(Controller controller, final GreenArea area) {
        final VBox card = new VBox(10);
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

        card.getChildren().addAll(header);
        return card;
    }
}
