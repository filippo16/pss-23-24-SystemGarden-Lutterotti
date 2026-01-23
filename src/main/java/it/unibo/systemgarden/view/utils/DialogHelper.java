package it.unibo.systemgarden.view.utils;

import it.unibo.systemgarden.view.api.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.function.Consumer;

/**
 * Helper class to show FXML dialogs.
 */
public final class DialogHelper {

    /**
     * Shows a custom dialog.
     * @param <R> the result type
     * @param <C> the controller type
     * @param fxmlPath the path to the FXML file for the dialog
     * @param title the title of the dialog window
     * @param css the CSS stylesheet to apply to the dialog
     * @param controllerInitializer a consumer to initialize the controller before showing the dialog with lambda function
     * @return the result from the dialog controller
    */
    public static<R, C extends DialogController<R>> R showDialog( 
            final String fxmlPath, final String title, 
            final Consumer<C> controllerInit
        ) {
            
        try {

            final FXMLLoader loader = new FXMLLoader( ClassLoader.getSystemResource( fxmlPath ) );
            final Parent root = loader.load();

            final Stage dialog = createDialogStage( title, root );
            final C controller = loader.getController();
            controller.setStage( dialog );

            
            if ( controllerInit != null ) {
                controllerInit.accept( controller );
            }

            dialog.showAndWait();

            return controller.getResult();
        } catch (Exception e) {
            throw new RuntimeException( "Error showing dialog: " + e.getMessage(), e );
        }
    }
    
    private static Stage createDialogStage( final String title, final Parent root ) {
        final Stage stage = new Stage();
        stage.setTitle( title );
        stage.initModality( Modality.APPLICATION_MODAL );
        stage.initStyle( StageStyle.UTILITY );
        stage.setResizable(false);

        final Scene dialogScene = new Scene(root);

        stage.setScene(dialogScene);
        return stage;
    }
}
