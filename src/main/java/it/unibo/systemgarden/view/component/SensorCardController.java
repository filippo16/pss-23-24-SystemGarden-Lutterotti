package it.unibo.systemgarden.view.component;

import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.utils.SensorType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SensorCardController {

	@FXML
	private Label nameLabel;

	@FXML
	private Label valueLabel;

	@FXML
	private Label typeValueLabel;

    public void initialize( Sensor data ) {
        nameLabel.setText( data.getName() );
        valueLabel.setText( String.valueOf( data.readData() ) );
        setTypeValue(data.getType());
    }

	public void setTypeValue(SensorType type) {
		typeValueLabel.setText(type != null ? type.getUnit() : "");
	}
}
