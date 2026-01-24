package it.unibo.systemgarden.view.dialog;

import it.unibo.systemgarden.model.utils.SensorType;
import it.unibo.systemgarden.view.api.DialogController;
import it.unibo.systemgarden.view.dto.SensorData;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the Add Sensor Dialog.
 * Shows a dialog to get data name and type for a new sensor.
*/
public class AddSensorDialogController implements DialogController<SensorData> {

	@FXML
	private ComboBox<SensorType> sensorTypeCombo;

    @FXML
    private TextField nameField;

	private Stage stage;
	private SensorData result;

    @FXML
    private void initialize() {
        sensorTypeCombo.getItems().setAll( SensorType.values() );
    }

	@FXML
	private void onCancel() {
		result = null;
		closeDialog();
	}

	@FXML
	private void onCreate() {
        final String name = nameField.getText().trim();
		final SensorType value = sensorTypeCombo.getValue();

		if (value != null && !name.isEmpty()) {
			result = new SensorData(name, value);
            closeDialog();
		} 
	}

	private void closeDialog() {
		stage.close();
	}

	@Override
	public SensorData getResult() {
		return result;
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
