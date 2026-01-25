package it.unibo.systemgarden.view.dto;

import it.unibo.systemgarden.model.utils.SensorType;

/**
 * DTO for sensor to update information.
 * @param name the name of the sensor
 * @param type the type of the sensor
*/
public record SensorData(String name, SensorType type) {}