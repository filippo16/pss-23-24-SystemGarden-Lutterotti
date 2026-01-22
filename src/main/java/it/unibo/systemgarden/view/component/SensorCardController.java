package it.unibo.systemgarden.view.component;

import it.unibo.systemgarden.model.api.Sensor;
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
        //valueLabel.setText( data.getValue() );
        setTypeValue(data.getType());
    }

	public void setTypeValue(String type) {
		if( type.equals("Temperature") ) {

            typeValueLabel.setText("°C");
            
        } else if( type.equals("Humidity") ) {

            typeValueLabel.setText("%");

        } else {

            typeValueLabel.setText("");
        }
	}
}
