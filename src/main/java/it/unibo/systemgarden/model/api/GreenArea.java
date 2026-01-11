package it.unibo.systemgarden.model.api;

/**
 * Interface for a green area (garden or plant group).
 * A green area represents a single irrigation system.
 */
public interface GreenArea {

    /**
     * @return the unique identifier of this area
     */
    String getId();

    /**
     * @return the name of this area
     */
    String getName();

    /**
     * @return the city where this area is located
     */
    String getCity();
}
