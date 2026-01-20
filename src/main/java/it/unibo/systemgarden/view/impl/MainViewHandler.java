package it.unibo.systemgarden.view.impl;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.view.utils.DialogHelper;
import it.unibo.systemgarden.view.component.AreaCardController;
import it.unibo.systemgarden.view.dialog.AddAreaDialogController;
import it.unibo.systemgarden.view.dto.AreaCardData;
import it.unibo.systemgarden.view.utils.CardGenerator;


/**
 * Handler for the main view FXML. 
 * Manages the methods called in the view for buttons and other components. 
 */
public class MainViewHandler {

    private static final String FXML_PATH_AREA_DIALOG = "fxml/dialog/AddAreaDialog.fxml";

    private Map<String, AreaCardController> areaControllers;

    @FXML private VBox areasContainer;
    @FXML private Label statusLabel;

    private String css;
    private Controller controller;
    private CardGenerator cardGenerator;

    public void setCssStylesheet(final String css) {
        this.css = css;
        this.cardGenerator = new CardGenerator(css);
        this.areaControllers = new HashMap<>();
    }

    public void setController(final Controller controller) {
        this.controller = controller;
    }

    @FXML
    private void onAddAreaClicked() {
        final String[] result = DialogHelper.<String[], AddAreaDialogController>showDialog(
            FXML_PATH_AREA_DIALOG, "+ Nuova Area Verde", css, null
        );
        
        if (result != null) {
            controller.createGreenArea( result[0], result[1] );
        }
    }


    /**
    * Adds a new area card to the Areas Container in the main view.
    */
    public void addAreaCard( final GreenArea area ) {
        final AreaCardData cardData = cardGenerator.createAreaCard( controller, area );

        areasContainer.getChildren().add( cardData.areaCard() );
        areaControllers.put( area.getId(), cardData.controller() );
    }

    /**
    * Removes an area card from the Areas Container in the main view.
    */
    public void removeAreaCard( final GreenArea area ) {
        areasContainer.getChildren().removeIf( node -> 
            node.getId().equals( area.getId() ) );
        areaControllers.remove( area.getId() );
    }


    /**    
    * Refreshes an existing area card in the Areas Container in the main view.
    */
    public void refreshAreaCard( final GreenArea area ) {
        ObservableList<Node> children = areasContainer.getChildren();
    
        for (int i = 0; i < children.size(); i++) {

            if ( area.getId().equals( children.get(i).getId() ) ) {

                final AreaCardData cardData = 
                    cardGenerator.createAreaCard( controller, area 
                );
                
                if( cardData != null ) {

                    children.set( i, cardData.areaCard() ); 
                    areaControllers.put( area.getId(), cardData.controller() );
                }
                break;
            }
        }
    }

    public void updateAreaClock( final String areaId, final LocalTime time ) {   
        AreaCardController ctrl = areaControllers.get( areaId );  
        if ( ctrl != null ) {                                         
            ctrl.updateClock( time );                                 
        }                                                           
    }



    public void addSectorCard( final String areaId, final Sector sector ) {
        AreaCardController ctrl = areaControllers.get( areaId );

        if ( ctrl != null ) {
            ctrl.addSectorCard( sector );
        }
    }

    public void removeSectorCard( final String areaId, final String sectorId  ) {
        AreaCardController ctrl = areaControllers.get( areaId );

        if ( ctrl != null ) {
            ctrl.removeSectorCard( sectorId );
        }
    }

    public void refreshSectorCard( final String areaId, final Sector sector ) {
        AreaCardController ctrl = areaControllers.get( areaId );

        if ( ctrl != null ) {
            ctrl.refreshSectorCard( sector );
        }
    }
}