package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;

import java.util.Random;

/**
 * Implementation of GreenArea interface.
 */
public class GreenAreaImpl implements GreenArea {

    private final String id;
    private final String name;
    private final String city;

    /**
     * Creates a new green area.
     * 
     * @param name the area name
     * @param city the city where the area is located
     */
    public GreenAreaImpl( final String name, final String city ) {
        this.id = "AREA-" + new Random().nextInt( 100000 );
        this.name = name;
        this.city = city;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCity() {
        return this.city;
    }
}
