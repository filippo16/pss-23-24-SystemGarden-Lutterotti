package it.unibo.systemgarden.view.utils;

/**
 * Enum representing different types of toasts with associated style classes.
 * Used for displaying feedback messages to the user.
*/
public enum ToastType {
    SUCCESS("toast-success"),
    ERROR("toast-error"),
    WARNING("toast-warning"),
    INFO("toast-info");

    private final String styleClass;

    ToastType(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * Gets the CSS style class associated with the toast type.
     * @return the style class as a String
    */
    public String getStyleClass() {
        return styleClass;
    }
}