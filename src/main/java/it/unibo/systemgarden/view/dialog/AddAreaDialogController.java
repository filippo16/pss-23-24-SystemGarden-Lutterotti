package it.unibo.systemgarden.view.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for AddAreaDialog.
 * Contains the methods of the dialog (Create GreenArea)
 */
public class AddAreaDialogController {

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
