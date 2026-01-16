package it.unibo.systemgarden.view.api;

import javafx.stage.Stage;

public interface DialogController<R> {
    
    R getResult();

    void setStage(Stage stage);

}
