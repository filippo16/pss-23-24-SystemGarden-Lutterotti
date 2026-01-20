package it.unibo.systemgarden.view.component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.view.dialog.AddSectorDialogController;
import it.unibo.systemgarden.view.dto.SectorCardData;
import it.unibo.systemgarden.view.utils.DialogHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AreaCardController {

    private static final String FXML_PATH_SECTOR_DIALOG = "fxml/dialog/AddSectorDialog.fxml";
    private static final String FXML_PATH_SECTOR_CARD = "fxml/component/SectorCard.fxml";

    @FXML private VBox card;
    @FXML private Label nameLabel;
    @FXML private Label cityLabel;
    @FXML private Label clockLabel;
    @FXML private Button deleteBtn;
    @FXML private Button addSectorBtn;
    @FXML private VBox sectorsContainer;

    private Controller controller;
    private GreenArea area;
    private String css;

    private final Map<String, SectorCardController> sectorControllers = new HashMap<>();

    public void initialize(final Controller controller, final GreenArea area, final String css) {
        this.controller = controller;
        this.area = area;
        this.css = css;

        card.setId(area.getId());
        nameLabel.setText(area.getName());
        cityLabel.setText("(" + area.getLocation().getCity() + ")");
        clockLabel.setText(area.getLocation().getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));

        // Populate sectors
        for (final var sector : area.getSectors()) {
            addSectorCard(sector);
        }
    }

    public void updateClock(LocalTime time) {
        clockLabel.setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    /**
     * Adds a new sector card.
     */
    public SectorCardData addSectorCard(final Sector sector) {
        try {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource( FXML_PATH_SECTOR_CARD ));
            final VBox sectorCard = loader.load();
            sectorCard.setId( sector.getId() );
            
            final SectorCardController ctrl = loader.getController();
            ctrl.initialize( controller, area.getId(), sector, css );
            
            sectorsContainer.getChildren().add( sectorCard );
            sectorControllers.put( sector.getId(), ctrl );
            
            return new SectorCardData(sectorCard, ctrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Removes a sector card.
     */
    public void removeSectorCard( final String sectorId ) {
        sectorsContainer.getChildren().removeIf( node -> 
            sectorId.equals( node.getId() ) );
        sectorControllers.remove( sectorId );
    }

    /**
     * Refreshes a specific sector card.
     */
    public void refreshSectorCard( final Sector sector ) {
        ObservableList<javafx.scene.Node> children = sectorsContainer.getChildren();

        for (int i = 0; i < children.size(); i++) {

            if ( sector.getId().equals( children.get(i).getId() ) ) {

                final SectorCardData sectorCardData = addSectorCard( sector );

                if (sectorCardData != null) {

                    children.set( i, sectorCardData.sectorCard() );
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
            FXML_PATH_SECTOR_DIALOG, "Nuovo Settore", css, null);

        if (result != null) {
            controller.addSectorToArea( area.getId(), result );
        }
    }

    public VBox getCard() {
        return card;
    }
}