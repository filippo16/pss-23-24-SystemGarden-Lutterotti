package it.unibo.systemgarden.view.api;

public interface InitializableDialogController<R, D> extends DialogController<R> {
    void initData(D data);
}