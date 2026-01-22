package it.unibo.systemgarden.model.impl;

import java.util.Random;

import it.unibo.systemgarden.model.api.Camera;

public class CameraImpl implements Camera {

    private final String id;
    private final String name;

    private static final String VIDEO_PATH = "http://example.com/stream";

    public CameraImpl(final String name) {
        this.id = "CAM-" + new Random().nextInt( 100000 );
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getStreamUrl() {
        return VIDEO_PATH;
    }
    
}
