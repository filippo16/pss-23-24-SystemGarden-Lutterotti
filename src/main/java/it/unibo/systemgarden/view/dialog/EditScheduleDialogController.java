package it.unibo.systemgarden.view.dialog;

import it.unibo.systemgarden.model.api.Schedule;
import it.unibo.systemgarden.view.api.InitializableDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for EditScheduleDialog.
 * Allows editing schedule parameters (start time, duration, active days).
 */
public class EditScheduleDialogController implements InitializableDialogController<ScheduleData, Schedule> {

    @FXML
    private Spinner<Integer> hourSpinner;
    
    @FXML
    private Spinner<Integer> minuteSpinner;
    
    @FXML
    private Spinner<Integer> durationSpinner;
    
    @FXML
    private CheckBox mondayCheck;
    
    @FXML
    private CheckBox tuesdayCheck;
    
    @FXML
    private CheckBox wednesdayCheck;
    
    @FXML
    private CheckBox thursdayCheck;
    
    @FXML
    private CheckBox fridayCheck;
    
    @FXML
    private CheckBox saturdayCheck;
    
    @FXML
    private CheckBox sundayCheck;

    private ScheduleData result;
    private Stage stage;

    @FXML
    private void initialize() {
        hourSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 6));
        minuteSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        durationSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 180, 30));
    }

    @FXML
    private void onSave() {
        final LocalTime startTime = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
        final int duration = durationSpinner.getValue();
        final List<Integer> activeDays = new ArrayList<>();
        
        if (mondayCheck.isSelected()) activeDays.add(1);
        if (tuesdayCheck.isSelected()) activeDays.add(2);
        if (wednesdayCheck.isSelected()) activeDays.add(3);
        if (thursdayCheck.isSelected()) activeDays.add(4);
        if (fridayCheck.isSelected()) activeDays.add(5);
        if (saturdayCheck.isSelected()) activeDays.add(6);
        if (sundayCheck.isSelected()) activeDays.add(7);
        
        result = new ScheduleData(startTime, duration, activeDays);
        closeDialog();
    }

    @FXML
    private void onCancel() {
        result = null;
        closeDialog();
    }

    @Override
    public ScheduleData getResult() {
        return result;
    }

    private void closeDialog() {
        stage.close();
    }

    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initData(final Schedule data) {
        if (data != null) {

            hourSpinner.getValueFactory().setValue( data.getStartTime().getHour() );
            minuteSpinner.getValueFactory().setValue( data.getStartTime().getMinute() );
            durationSpinner.getValueFactory().setValue( data.getDuration() );
            
            final List<Integer> activeDays = data.getActiveDays();
            mondayCheck.setSelected( activeDays.contains(1) );
            tuesdayCheck.setSelected( activeDays.contains(2) );
            wednesdayCheck.setSelected( activeDays.contains(3) );
            thursdayCheck.setSelected( activeDays.contains(4) );
            fridayCheck.setSelected( activeDays.contains(5) );
            saturdayCheck.setSelected( activeDays.contains(6) );
            sundayCheck.setSelected( activeDays.contains(7) );
            
        }
    }
}
