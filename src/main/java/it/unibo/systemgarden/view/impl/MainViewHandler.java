package it.unibo.systemgarden.view.impl;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.view.utils.DialogHelper;


/**
 * Handler for the main view FXML. 
 * Manages the methods called in the view for buttons and other components. 
 */
public class MainViewHandler {
    @FXML
    private VBox areasContainer;

    @FXML
    private Label statusLabel;

    private String css;

    private Controller controller;

    public void setCssStylesheet(final String css) {
        this.css = css;
    }

    public void setController(final Controller controller) {
        this.controller = controller;
    }

    @FXML
    private void onAddAreaClicked() {
        final String[] result = DialogHelper.showAddAreaDialog(css);
        
        if(result != null) {
            controller.createGreenArea(result[0], result[1]);
            System.out.println("Creating area: " + result[0] + " in " + result[1]);
        }

    }


    public void addAreaCard(final GreenArea area) {
        final VBox card = createAreaCard(area);
        card.setId(area.getId());
        areasContainer.getChildren().add(card);
    }

    public void removeAreaCard(final GreenArea area) {
        areasContainer.getChildren().removeIf( node -> node.getId().equals( area.getId() ) );
    }

    public void refreshAreaCard(final GreenArea area) {
        ObservableList<Node> children = areasContainer.getChildren();
    
        for (int i = 0; i < children.size(); i++) {
            if (area.getId().equals(children.get(i).getId())) {
                children.set(i, createAreaCard( area ));  // ← Sostituisce direttamente!
                break;
            }
        }
    }

    private VBox createAreaCard(final GreenArea area) {
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
