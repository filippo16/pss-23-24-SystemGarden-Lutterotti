package it.unibo.systemgarden.view.component;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.view.dialog.EditScheduleDialogController;
import it.unibo.systemgarden.view.dto.ScheduleData;
import it.unibo.systemgarden.view.utils.DialogHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SectorCardController {

    private static final String FXML_PATH_SCHEDULE_DIALOG = 
        "fxml/dialog/EditScheduleDialog.fxml";

    @FXML private VBox sectorBox;
    @FXML private Label sectorLabel;
    @FXML private Label scheduleInfoLabel;
    @FXML private Button startBtn;
    @FXML private Button stopBtn;
    @FXML private Button scheduleBtn;
    @FXML private Button deleteBtn;

    private Controller controller;
    private String areaId;
    private Sector sector;
    private String css;

    /**
     * Initializes the sector card with data.
     */
    public void initialize(final Controller controller, final String areaId, 
        final Sector sector, final String css
    ) {
        this.controller = controller;
        this.areaId = areaId;
        this.sector = sector;
        this.css = css;

        updateLabels();
    }

    private void updateLabels() {
        final String status = sector.isIrrigating() ? "[ON]" : "[--]";
        sectorLabel.setText(status + " " + sector.getName());
        scheduleInfoLabel.setText(sector.getSchedule().formatScheduleInfo());
    }

    @FXML
    private void onStart() {
        controller.irrigateSector(areaId, sector.getId());
    }

    @FXML
    private void onStop() {
        controller.stopSector(areaId, sector.getId());
    }

    @FXML
    private void onSchedule() {
        final ScheduleData result = DialogHelper.<ScheduleData, EditScheduleDialogController>showDialog(
            FXML_PATH_SCHEDULE_DIALOG, "Modifica Programmazione", css, controllerInit -> {
                controllerInit.initData(sector.getSchedule());
            }
        );

        if (result != null) {
            controller.updateSectorSchedule(areaId, sector.getId(), 
                result.startTime(), result.duration(), result.activeDays());
        }
    }

    @FXML
    private void onDelete() {
        controller.removeSectorFromArea(areaId, sector.getId());
    }

    /**
     * Returns the root VBox of this sector card.
     */
    public VBox getCard() {
        return sectorBox;
    }
}
