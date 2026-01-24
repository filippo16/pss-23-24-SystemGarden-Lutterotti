package it.unibo.systemgarden.view.dialog;

import it.unibo.systemgarden.view.api.DialogController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for AddSectorDialog.
 * Shows a dialog to get data name for a new sector.
 */
public class AddSectorDialogController implements DialogController<String> {

    @FXML
    private TextField nameField;

    private String result;
    private Stage stage;
  
    @FXML
    private void onAdd() {
        final String name = nameField.getText().trim();

        if (!name.isEmpty()) {
            result = name;
            closeDialog();
        }
    }

    @FXML
    private void onCancel() {
        result = null;
        closeDialog();
    }

    /**
     * @return the sector name or null if cancelled
     */
    public String getResult() {
        return result;
    }

    private void closeDialog() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
