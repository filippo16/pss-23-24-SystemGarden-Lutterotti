package it.unibo.systemgarden.view.impl;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.view.utils.DialogHelper;
import it.unibo.systemgarden.view.utils.CardGenerator;


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

    private CardGenerator cardGenerator;

    public void setCssStylesheet(final String css) {
        this.css = css;
        this.cardGenerator = new CardGenerator(css);
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
        final VBox card = cardGenerator.createAreaCard( controller, area );
        areasContainer.getChildren().add(card);
    }

    public void removeAreaCard(final GreenArea area) {
        areasContainer.getChildren().removeIf( node -> node.getId().equals( area.getId() ) );
    }

    public void refreshAreaCard(final GreenArea area) {
        ObservableList<Node> children = areasContainer.getChildren();
    
        for (int i = 0; i < children.size(); i++) {
            if (area.getId().equals(children.get(i).getId())) {
                children.set(i, cardGenerator.createAreaCard( controller, area )); 
                break;
            }
        }
    }



}
