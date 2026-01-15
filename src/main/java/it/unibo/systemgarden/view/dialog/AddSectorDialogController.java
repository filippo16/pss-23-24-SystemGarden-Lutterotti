package it.unibo.systemgarden.view.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for AddSectorDialog.
 * Contains the methods of the dialog (Create Sector)
 */
public class AddSectorDialogController {

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
