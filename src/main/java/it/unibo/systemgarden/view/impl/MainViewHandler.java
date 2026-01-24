package it.unibo.systemgarden.view.impl;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.view.utils.DialogHelper;
import it.unibo.systemgarden.view.component.AreaCardController;
import it.unibo.systemgarden.view.dialog.AddAreaDialogController;
import it.unibo.systemgarden.view.dto.CardData;
import it.unibo.systemgarden.model.api.SmartAdvisor;


/**
 * Handler for the main view FXML. 
 * Manages the methods called in the view for buttons and other components. 
 */
public class MainViewHandler {

    private static final String FXML_PATH_AREA_DIALOG = "fxml/dialog/AddAreaDialog.fxml";
    private static final String FXML_PATH_AREA_CARD = "fxml/component/AreaCard.fxml";

    private Map<String, AreaCardController> areaControllers;

    @FXML private VBox areasContainer;
    @FXML private Label statusLabel;

    private Controller controller;

    public MainViewHandler() {
        this.areaControllers = new HashMap<>();    
    }

    
    public void setController(final Controller controller) {
        this.controller = controller;
    }

    @FXML
    private void onAddAreaClicked() {
        final String[] result = DialogHelper.<String[], AddAreaDialogController>showDialog(
            FXML_PATH_AREA_DIALOG, "+ Nuova Area Verde", null
        );
        
        if (result != null) {
            controller.createGreenArea( result[0], result[1] );
        }
    }

    /**
    * Creates an area card for the specified green area.
    * @param controller the controller to be used
    * @param area the green area for which to create the card
    * @return the created CardData containing the card and its controller {@link CardData}
    */
    private CardData<AreaCardController> createAreaCard(final Controller controller, final GreenArea area) {
        try {
            final FXMLLoader loader = new FXMLLoader(
                MainViewHandler.class.getClassLoader().getResource(FXML_PATH_AREA_CARD)
            );
            final VBox card = loader.load();
            
            final AreaCardController cardController = loader.getController();
            cardController.initialize( controller, area );
            
            return new CardData<AreaCardController>(card, cardController);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load AreaCard FXML", e);
        }
    }


    /**
    * Adds a new area card to the Areas Container in the main view.
    * @param area the green area to add
    */
    public void addAreaCard( final GreenArea area ) {
        final CardData<AreaCardController> cardData = createAreaCard( controller, area );

        areasContainer.getChildren().add( cardData.card() );
        areaControllers.put( area.getId(), cardData.controller() );
    }

    /**
    * Removes an area card from the Areas Container in the main view.
    * @param areaId the ID of the green area to remove
    */
    public void removeAreaCard( final String areaId ) {
        areasContainer.getChildren().removeIf( node -> 
            node.getId().equals( areaId ) 
        );
        
        areaControllers.remove( areaId );
    }


    /**    
    * Refreshes an existing area card in the Areas Container in the main view.
    * Puts the updated card in place of the old one.
    * @param area the green area to refresh
    */
    public void refreshAreaCard( final GreenArea area ) {
        ObservableList<Node> children = areasContainer.getChildren();
    
        for (int i = 0; i < children.size(); i++) {

            if ( area.getId().equals( children.get(i).getId() ) ) {

                final CardData<AreaCardController> cardData = 
                    createAreaCard( controller, area 
                );
                
                if( cardData != null ) {

                    children.set( i, cardData.card() ); 
                    areaControllers.put( area.getId(), cardData.controller() );
                }
                break;
            }
        }
    }

    /**    
    * Updates the clock display for the specified green area.
    * @param areaId the ID of the green area
    * @param time the new time to display
    */
    public void updateAreaClock( final String areaId, final LocalTime time ) {   
        AreaCardController ctrl = areaControllers.get( areaId );  
        if ( ctrl != null ) {                                         
            ctrl.updateClock( time );                                 
        }                                                           
    }


    /**
    * Adds a sector card to the specified green area.
    * @param areaId the ID of the green area
    * @param sector the sector to add
    */
    public void addSectorCard( final String areaId, final Sector sector ) {
        AreaCardController ctrl = areaControllers.get( areaId );

        if ( ctrl != null ) {
            ctrl.addSectorCard( sector );
        }
    }

    /**
    * Removes a sector card from the specified green area.
    * @param areaId the ID of the green area
    * @param sectorId the ID of the sector to remove
    */
    public void removeSectorCard( final String areaId, final String sectorId  ) {
        AreaCardController ctrl = areaControllers.get( areaId );

        if ( ctrl != null ) {
            ctrl.removeSectorCard( sectorId );
        }
    }

    /**    
    * Refreshes a sector card in the specified green area.
    * @param areaId the ID of the green area
    * @param sector the sector to refresh
    */
    public void refreshSectorCard( final String areaId, final Sector sector ) {
        AreaCardController ctrl = areaControllers.get( areaId );

        if ( ctrl != null ) {
            ctrl.refreshSectorCard( sector );
        }
    }

    /**    
    * Refreshes sensor data in the specified green area.
    * @param areaId the ID of the green area
    * @param sensorId the ID of the sensor
    * @param newValue the new sensor value
    */
    public void refreshSensorData(final String areaId, final String sensorId, final double newValue ) {
        AreaCardController ctrl = areaControllers.get( areaId );

        if ( ctrl != null ) {
            ctrl.refreshSensorData( sensorId, newValue );
        }
    }

    /**    
    * Shows an advice notification ({@link SmartAdvisor}) for the specified green area.
    * @param areaId the ID of the green area
    * @param advice the advice message to display
    */
    public void showAdviceNotification( final String areaId, final String advice ) {
        AreaCardController ctrl = areaControllers.get( areaId );

        if ( ctrl != null ) {
            ctrl.showAdviceNotification( advice );
        }
    }
}