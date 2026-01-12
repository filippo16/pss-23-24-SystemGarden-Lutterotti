package it.unibo.systemgarden.view.impl;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.view.api.View;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * Basic View for testing (no style).
 */
public class SimpleView implements View {

    private final Stage stage;
    private Controller controller;
    private VBox content;

    public SimpleView( final Stage stage ) {
        this.stage = stage;
    }

    public void setController( final Controller controller ) {
        this.controller = controller;
    }

    @Override
    public void show() {
        VBox root = new VBox();
        
        Button addBtn = new Button("+ Nuova Area");
        addBtn.setOnAction(e -> {
            TextInputDialog d1 = new TextInputDialog();
            d1.setHeaderText("Nome:");

            d1.showAndWait().ifPresent(name -> {
                TextInputDialog d2 = new TextInputDialog();
                d2.setHeaderText("Città:");

                d2.showAndWait().ifPresent( city -> {
                    controller.createGreenArea( name, city );
                });
            });
        });
        
        content = new VBox();
        root.getChildren().addAll( addBtn, content );
        
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Test");
        stage.show();
    }

    @Override
    public void updateGreenAreas( final List<GreenArea> areas ) {}
}
