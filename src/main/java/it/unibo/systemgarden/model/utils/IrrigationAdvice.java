package it.unibo.systemgarden.model.utils;

/**
 * Enum representing different irrigation advice types.
 * Each type has an associated message for display in view.
 */
public enum IrrigationAdvice {

    IRRIGATE_NOW("Irrigazione Consigliata"),
    NO_IRRIGATION_NEEDED("Nessuna Irrigazione Necessaria"),
    OPTIMAL_CONDITIONS("Condizioni Ottimali"),
    INSUFFICIENT_DATA("Sensori Insufficienti");


    private final String title;


    IrrigationAdvice(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
