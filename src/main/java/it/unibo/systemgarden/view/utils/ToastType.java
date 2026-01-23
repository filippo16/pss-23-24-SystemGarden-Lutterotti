package it.unibo.systemgarden.view.utils;

public enum ToastType {
    SUCCESS("toast-success"),
    ERROR("toast-error"),
    WARNING("toast-warning"),
    INFO("toast-info");

    private final String styleClass;

    ToastType(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}