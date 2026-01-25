package it.unibo.systemgarden.view.dialog;

import it.unibo.systemgarden.view.api.DialogController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for AddAreaDialog.
 * Shows a dialog to get data name and city for a new area.
 */
public class AddAreaDialogController implements DialogController<String[]> {

    @FXML
    private TextField nameField;

    @FXML
    private TextField cityField;

    private String[] result;
    private Stage stage;

    @FXML
    private void onCreate() {
        final String name = nameField.getText().trim();
        final String city = cityField.getText().trim();

        if (!name.isEmpty() && !city.isEmpty()) {
            result = new String[] { name, city };
            closeDialog();
        }
    }

    @FXML
    private void onCancel() {
        result = null;
        closeDialog();
    }

    /**
     * @return the result array [name, city] or null if cancelled
     */
    public String[] getResult() {
        return result;
    }

    private void closeDialog() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
