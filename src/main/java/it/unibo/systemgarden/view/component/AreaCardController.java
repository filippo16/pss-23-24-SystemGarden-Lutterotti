package it.unibo.systemgarden.view.component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.view.dialog.AddSectorDialogController;
import it.unibo.systemgarden.view.dialog.AddSensorDialogController;
import it.unibo.systemgarden.view.dto.CardData;
import it.unibo.systemgarden.view.dto.SensorData;
import it.unibo.systemgarden.view.utils.DialogHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AreaCardController {

    private static final String FXML_PATH_SECTOR_DIALOG = "fxml/dialog/AddSectorDialog.fxml";
    private static final String FXML_PATH_SECTOR_CARD = "fxml/component/SectorCard.fxml";
    private static final String FXML_PATH_SENSOR_DIALOG = "fxml/dialog/AddSensorDialog.fxml";

    @FXML 
    private VBox card;

    @FXML 
    private Label nameLabel;

    @FXML 
    private Label cityLabel;

    @FXML 
    private Label clockLabel;

    @FXML 
    private VBox sectorsContainer;

    @FXML
    private HBox sensorsContainer;

    private Controller controller;
    private GreenArea area;

    private final Map<String, SectorCardController> sectorControllers = new HashMap<>();
    private final Map<String, SensorCardController> sensorControllers = new HashMap<>();

    public void initialize( final Controller controller, final GreenArea area) {
        this.controller = controller;
        this.area = area;

        card.setId( area.getId() );
        nameLabel.setText( area.getName() );
        cityLabel.setText( "(" + area.getLocation().getCity() + ")" );
        updateClock( area.getLocation().getLocalTime() );

        // Populate sectors
        for (final Sector sector : area.getSectors()) {
            addSectorCard( sector );
        }

        for (final Sensor sensor : area.getSensors()) {
            addSensorCard( sensor );
        }
    }

    public void updateClock( LocalTime time ) {
        clockLabel.setText( time.format( DateTimeFormatter.ofPattern( "HH:mm" ) ) );
    }

    /**
     * Creates a sector card
     * @param sector sector to create
     * @return containing the sector card and its controller
     */
    private CardData<SectorCardController> createSectorCard(final Sector sector) {
        try {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource( FXML_PATH_SECTOR_CARD ));
            
            final VBox sectorCard = loader.load();
            sectorCard.setId( sector.getId() );
            
            final SectorCardController ctrl = loader.getController();
            ctrl.initialize( controller, area.getId(), sector );
            
            return new CardData<SectorCardController>(sectorCard, ctrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error creating sector card : " + e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new sector card.
     * @param sector sector to add
     */
    public void addSectorCard(final Sector sector) {
        final CardData<SectorCardController> sectorCardData = createSectorCard( sector );
        
        if (sectorCardData != null) {
            sectorsContainer.getChildren().add( sectorCardData.card() );
            sectorControllers.put( sector.getId(), sectorCardData.controller() );
        }
    }

    /**
     * Removes a sector card.
     * @param sectorId sector to remove
     */
    public void removeSectorCard( final String sectorId ) {
        sectorsContainer.getChildren().removeIf( node -> 
            sectorId.equals( node.getId() ) );
        sectorControllers.remove( sectorId );
    }

    /**
     * Refreshes a specific sector card.
     * @param sector sector to refresh
     */
    public void refreshSectorCard( final Sector sector ) {
        ObservableList<Node> children = sectorsContainer.getChildren();

        for (int i = 0; i < children.size(); i++) {

            if ( sector.getId().equals( children.get(i).getId() ) ) {

                // Crea la card SENZA aggiungerla
                final CardData<SectorCardController> sectorCardData = 
                    createSectorCard( sector );

                if (sectorCardData != null) {
                    // Sostituisci la vecchia card con la nuova
                    children.set( i, sectorCardData.card() );
                    sectorControllers.put( sector.getId(), 
                        sectorCardData.controller() 
                    );
                }
                
                break;
            }
        }
    }

    @FXML
    private void onDeleteArea() {
        controller.removeGreenArea( area.getId() );
    }

    @FXML
    private void onAddSector() {
        final String result = DialogHelper.<String, AddSectorDialogController>showDialog(
            FXML_PATH_SECTOR_DIALOG, "Nuovo Settore", null);

        if (result != null) {
            controller.addSectorToArea( area.getId(), result );
        }
    }

    @FXML
    private void onAddSensor() {
        
        final SensorData result = DialogHelper.<SensorData, AddSensorDialogController>showDialog(
            FXML_PATH_SENSOR_DIALOG, "Nuovo Sensore", null);


        if (result != null) {
            controller.addSensorToArea( area.getId(), result.name(), result.type() );
        }
    }

    private CardData<SensorCardController> createSensorCard( final Sensor sensor ) {
        try {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource( 
                    "fxml/component/SensorCard.fxml" ));
            
            final VBox sensorCard = loader.load();
            sensorCard.setId( sensor.getId() );
            
            final SensorCardController ctrl = loader.getController();
            ctrl.initialize( sensor, controller, area.getId() );

            return new CardData<SensorCardController>(sensorCard, ctrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error creating sensor card : " + e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new sensor card.
     * @param sensor sensor to add
     */
    public void addSensorCard( final Sensor sensor ) {
        final CardData<SensorCardController> sensorCardData = 
            createSensorCard( sensor );
        
        if (sensorCardData != null) {

            sensorsContainer.getChildren().add( sensorCardData.card() );
            sensorControllers.put( sensor.getId(), sensorCardData.controller() );
        }
    }

    public void removeSensorCard( final String sensorId ) {
        sensorsContainer.getChildren().removeIf( node -> 
            sensorId.equals( node.getId() ) );
        sensorControllers.remove( sensorId );
    }

    public void refreshSensorData( final String sensorId, final double newValue ) {
        SensorCardController ctrl = sensorControllers.get( sensorId );

        if ( ctrl != null ) {
            ctrl.updateValue( newValue );
        }
        
    }


    public VBox getCard() {
        return card;
    }
}