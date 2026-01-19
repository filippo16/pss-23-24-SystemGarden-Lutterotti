package it.unibo.systemgarden.view.component;

import it.unibo.systemgarden.view.api.CardController;
import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.view.dialog.AddSectorDialogController;
import it.unibo.systemgarden.view.utils.DialogHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class AreaCardController implements CardController {

    private static final String FXML_PATH_SECTOR_DIALOG = "fxml/dialog/AddSectorDialog.fxml";
    private static final DateTimeFormatter CLOCK_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

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

    /**
     * Initializes the card with data.
     */
    public void initialize(final Controller controller, final GreenArea area, final String css) {
        this.controller = controller;
        this.area = area;
        this.css = css;

        card.setId(area.getId());

        nameLabel.setText(area.getName());
        cityLabel.setText("(" + area.getLocation().getCity() + ")");
        clockLabel.setText(area.getLocation().getLocalTime().format(CLOCK_FORMATTER));

        // Populate sectors
        addSectors();
    }

    private void addSectors() {
        // function to populate sectors
    }

    @FXML
    private void onDeleteArea() {
        controller.removeGreenArea(area.getId());
    }

    @FXML
    private void onAddSector() {
        final String result = DialogHelper.<String, AddSectorDialogController>showDialog(
            FXML_PATH_SECTOR_DIALOG, "Nuovo Settore", css, null);

        if (result != null) {
            controller.addSectorToArea(area.getId(), result);
        }
    }


    public VBox getCard() {
        return card;
    }
}