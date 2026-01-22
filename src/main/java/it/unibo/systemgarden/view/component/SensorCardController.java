package it.unibo.systemgarden.view.component;

import it.unibo.systemgarden.controller.api.Controller;
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

    private Sensor data;
    private Controller controller;
    private String areaId;

    public void initialize( final Sensor data, final Controller controller, 
        final String areaId 
    ) {
        this.data = data;
        this.controller = controller;
        this.areaId = areaId;


        nameLabel.setText( data.getName() );
        updateValue( data.readData() );
        setTypeValue( data.getType() );
    }

    public void updateValue( double newValue ) {
        valueLabel.setText( String.valueOf( newValue ) );
    }

	private void setTypeValue( SensorType type ) {
		typeValueLabel.setText( type != null ? type.getUnit() : "" );
	}

    @FXML
    private void onDelete() {
        controller.removeSensorFromArea( areaId, data.getId() );
    }
}
