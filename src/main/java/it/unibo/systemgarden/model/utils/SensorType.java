package it.unibo.systemgarden.model.utils;

public enum SensorType {
    TEMPERATURE("°C"),
    HUMIDITY("%");

    private final String unit;

    SensorType(final String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}