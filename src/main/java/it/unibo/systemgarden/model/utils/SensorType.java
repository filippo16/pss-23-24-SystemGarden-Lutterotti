package it.unibo.systemgarden.model.utils;

public enum SensorType {
    TEMPERATURE("temperature"),
    HUMIDITY("humidity");

    private final String label;

    SensorType(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getUnit() {
        switch (this) {
            case TEMPERATURE:
                return "°C";
            case HUMIDITY:
                return "%";
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return label.substring(0, 1).toUpperCase() + label.substring(1);
    }
}