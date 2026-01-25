package it.unibo.systemgarden.view.api;

import javafx.stage.Stage;

/**
 * Interface representing a dialog controller in the SystemGarden application.
 * @param <R> the type of the result produced by the dialog
 */
public interface DialogController<R> {
    
    /**
     * Retrieves the result produced by the dialog.
     * @return the result of type R
     */
    R getResult();

    /**
     * Sets the stage for the dialog.
     * @param stage the Stage to be set
     */
    void setStage( Stage stage );

}
