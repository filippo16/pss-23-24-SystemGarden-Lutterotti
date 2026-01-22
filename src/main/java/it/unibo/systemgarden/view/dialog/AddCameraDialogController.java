package it.unibo.systemgarden.view.dialog;

import it.unibo.systemgarden.view.api.DialogController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCameraDialogController implements DialogController<String> {

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

    private void closeDialog() {
        stage.close();
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void setStage(javafx.stage.Stage stage) {
        this.stage = stage;
    }
    
}
