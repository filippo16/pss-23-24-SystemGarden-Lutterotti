package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.Sector;

import java.util.Random;

/**
 * Implementation of Sector interface.
 */
public class SectorImpl implements Sector {

    private final String id;
    private final String name;
    private boolean irrigating;

    /**
     * Creates a new sector.
     * 
     * @param name the sector name
     */
    public SectorImpl( final String name ) {
        this.id = "SECT-" + new Random().nextInt( 100000 );
        this.name = name;
        this.irrigating = false;
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
    public boolean isIrrigating() {
        return this.irrigating;
    }

    @Override
    public void irrigate() {
        this.irrigating = true;
        System.out.println("[Sector] " + name + " - Irrigation started");
    }

    @Override
    public void stop() {
        this.irrigating = false;
        System.out.println("[Sector] " + name + " - Irrigation stopped");
    }
}
