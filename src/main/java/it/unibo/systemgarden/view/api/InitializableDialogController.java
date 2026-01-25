package it.unibo.systemgarden.view.api;

/**
 * Interface representing a dialog controller that can be initialized with data.
 * @param <R> the type of the result produced by the dialog
 * @param <D> the type of the data used for initialization
 */
public interface InitializableDialogController<R, D> extends DialogController<R> {
    /**
     * Initializes the dialog controller with the provided data.
     * @param data the data used for initialization (type D)
    */
    void initData(D data);
}