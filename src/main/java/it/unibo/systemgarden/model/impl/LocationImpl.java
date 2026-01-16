package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.Location;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Implementation of Location interface.
 */
public class LocationImpl implements Location {

    private final String city;
    private final ZoneId timezone;

    /**
     * Creates a new location.
     * 
     * @param city the city name
     */
    public LocationImpl(final String city) {
        this.city = city;
        this.timezone = resolveTimezone(city);
    }

    /**
     * Resolves the timezone based on the city name.
     * 
     * @param city the city name
     * @return the corresponding ZoneId
     */
    private ZoneId resolveTimezone(final String city) {
        return switch (city.toLowerCase()) {
            case "roma", "milano", "bologna", "cesena", "arco" -> ZoneId.of("Europe/Rome");
            case "london", "londra" -> ZoneId.of("Europe/London");
            case "new york" -> ZoneId.of("America/New_York");
            default -> ZoneId.of("Europe/Rome");
        };
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public ZoneId getTimezone() {
        return this.timezone;
    }

    @Override
    public LocalTime getLocalTime() {
        return LocalTime.now(timezone);
    }
}
